import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ryanphillips.rpstocks.MainDispatcherRule
import com.ryanphillips.rpstocks.portfolio.data.FakePortfolioRepository
import com.ryanphillips.rpstocks.portfolio.presentation.PortfolioViewModel
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioAction
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PortfolioViewModelTest {

    // JUnit4 Rule to swap the main dispatcher with a test dispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Rule to execute tasks synchronously (for LiveData, though good practice to keep)
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PortfolioViewModel
    private lateinit var fakeRepository: FakePortfolioRepository

    @Before
    fun setUp() {
        fakeRepository = FakePortfolioRepository()
        viewModel = PortfolioViewModel(fakeRepository)
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = viewModel.uiState.value
        assertThat(initialState).isEqualTo(PortfolioState(isLoading = true))
    }

    @Test
    fun `onAction OnPortfolioClick with success updates state with stocks`() = runTest {
        // GIVEN: The repository will return a successful result with stock data
        // THEN: Test the flow of states
        viewModel.uiState.test {
            // Initial state (isLoading = true)
            assertThat(awaitItem().isLoading).isTrue()
            /**
             * Because the PortfolioViewModel triggers a network call by default, we will always get the proper response first.
             */
            assertThat(awaitItem().isLoading).isFalse()

            // WHEN: The OnPortfolioClick action is triggered
            viewModel.onAction(PortfolioAction.OnPortfolioClick)
            // ViewModel resets loading to true before getting next result.
            assertThat(awaitItem().isLoading).isTrue()

            // The state should update to not loading, not error, and contain the stocks
            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.isError).isFalse()
            assertThat(successState.stocks).hasSize(3)
            assertThat(successState.stocks.first().symbol).isEqualTo("^GSPC")

            // Ensure no other emissions
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAction OnEmptyClick updates state with empty list`() = runTest {
        viewModel.uiState.test {
            // Initial state
            assertThat(awaitItem().isLoading).isTrue()

            // Await Default portfolio result
            assertThat(awaitItem().isLoading).isFalse()

            // WHEN: The OnEmptyClick action is triggered
            viewModel.onAction(PortfolioAction.OnEmptyClick)

            // The state should update to not loading and not error, and contain an empty list
            val emptyState = awaitItem()
            assertThat(emptyState.isLoading).isFalse()
            assertThat(emptyState.isError).isFalse()
            assertThat(emptyState.stocks).isEmpty()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAction OnMalformedClick - state updates correctly`() = runTest {
        // GIVEN: The malformed portfolio endpoint will return an error
        viewModel.uiState.test {
            // Initial state
            awaitItem()
            // Default portfolio result
            awaitItem()

            // WHEN: The OnMalformedClick action is triggered
            viewModel.onAction(PortfolioAction.OnMalformedClick)

            // THEN: The state updates to reflect the error
            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.isError).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
