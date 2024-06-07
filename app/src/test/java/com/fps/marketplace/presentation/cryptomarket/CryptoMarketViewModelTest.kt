import com.fps.core.domain.connectivity.NetworkConnectivityState
import com.fps.core.domain.marketplace.CryptoPair
import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import com.fps.data.mappers.toCryptoPair
import com.fps.data.models.CryptoPairDto
import com.fps.marketplace.presentation.cryptomarket.CryptoMarketAction
import com.fps.marketplace.presentation.cryptomarket.CryptoMarketViewModel
import com.fps.marketplace.presentation.cryptomarket.fakes.FakeConnectivityManager
import com.fps.marketplace.presentation.cryptomarket.fakes.FakeMarketplaceRepository
import com.fps.marketplace.presentation.cryptomarket.helpers.FakeDataHelper
import com.fps.marketplace.presentation.cryptomarket.mappers.toCryptoPairUi
import com.fps.marketplace.presentation.cryptomarket.models.CryptoPairUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoMarketViewModelTest {

    private lateinit var viewModel: CryptoMarketViewModel
    private lateinit var fakeMarketplaceRepository: FakeMarketplaceRepository
    private lateinit var fakeConnectivityManager: FakeConnectivityManager

    private lateinit var fakeApiResponseData: ArrayList<CryptoPairDto>
    private lateinit var fakeCryptoPair: ArrayList<CryptoPair>
    private lateinit var fakeCryptoPairUi: ArrayList<CryptoPairUi>

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        fakeMarketplaceRepository = FakeMarketplaceRepository()
        fakeConnectivityManager = FakeConnectivityManager()

        fakeApiResponseData = FakeDataHelper.getFakeCryptoPairDto() as ArrayList
        fakeCryptoPair = fakeApiResponseData.map { it.toCryptoPair() } as ArrayList
        fakeCryptoPairUi = fakeCryptoPair.map { it.toCryptoPairUi() } as ArrayList

        viewModel = CryptoMarketViewModel(fakeMarketplaceRepository, fakeConnectivityManager)
    }

    @After
    fun tearDown() {
        fakeApiResponseData.clear()
        fakeCryptoPair.clear()
        fakeCryptoPairUi.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun `onAction OnPullToRefresh calls getTickers`(): Unit = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Success(listOf()))

        viewModel.onAction(CryptoMarketAction.OnPullToRefresh)

        Assert.assertTrue(fakeMarketplaceRepository.getTickersCalled)
    }

    @Test
    fun `onAction OnSearch filters cryptoPairsList`() = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Success(fakeCryptoPair))

        viewModel.onAction(CryptoMarketAction.OnSearch("Btc"))

        val filteredList = viewModel.state.cryptoPairsFilteredList
        Assert.assertTrue(filteredList.all { it.name.contains("Btc", ignoreCase = true) })
    }

    @Test
    fun `onAction OnClearSearch clears search query`() = runTest {
        viewModel.onAction(CryptoMarketAction.OnSearch("Btc"))
        viewModel.onAction(CryptoMarketAction.OnClearSearch)

        Assert.assertEquals("", viewModel.state.searchQuery)
    }

    @Test
    fun `getTickers updates state with success`() = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Success(fakeCryptoPair))

        viewModel.onAction(CryptoMarketAction.OnPullToRefresh)

        Assert.assertEquals(false, viewModel.state.isLoading)
        Assert.assertEquals(fakeCryptoPairUi.size, viewModel.state.cryptoPairsList.size)
        Assert.assertNull(viewModel.state.error)
    }

    @Test
    fun `getTickers updates state with error`() = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Error(DataError.Network.NO_INTERNET))

        viewModel.onAction(CryptoMarketAction.OnPullToRefresh)

        Assert.assertEquals(false, viewModel.state.isLoading)
        Assert.assertNotNull(viewModel.state.error)
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = viewModel.state

        Assert.assertTrue(initialState.cryptoPairsList.isEmpty())
        Assert.assertTrue(initialState.cryptoPairsFilteredList.isEmpty())
        Assert.assertEquals("", initialState.searchQuery)
        Assert.assertNull(initialState.error)
        Assert.assertFalse(initialState.isLoading)
    }

    @Test
    fun `network status updates state correctly`() = runTest {
        fakeConnectivityManager.setNetworkState(NetworkConnectivityState.Unavailable)

        Assert.assertFalse(viewModel.state.isConnectedToNetwork)

        fakeConnectivityManager.setNetworkState(NetworkConnectivityState.Available)

        Assert.assertTrue(viewModel.state.isConnectedToNetwork)
    }

    @Test
    fun `onAction OnRefreshAfterError clears error`() = runTest {
        viewModel.onAction(CryptoMarketAction.OnRefreshAfterError)

        Assert.assertNull(viewModel.state.error)
    }

    @Test
    fun `onAction OnSearch with empty query resets filtered list`() = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Success(fakeCryptoPair))

        viewModel.onAction(CryptoMarketAction.OnSearch("Btc"))
        viewModel.onAction(CryptoMarketAction.OnSearch(""))

        Assert.assertEquals(fakeCryptoPairUi.size, viewModel.state.cryptoPairsFilteredList.size)
    }

    @Test
    fun `onAction OnSearch is case insensitive`() = runTest {
        fakeMarketplaceRepository.setTickersResult(Result.Success(fakeCryptoPair))

        viewModel.onAction(
            CryptoMarketAction.OnSearch(
                fakeCryptoPairUi.firstOrNull()?.name?.lowercase() ?: ""
            )
        )

        Assert.assertTrue(viewModel.state.cryptoPairsFilteredList.isNotEmpty())
    }
}
