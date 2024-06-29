//import com.syngenta.data.repository.RemoteDataSource
//import com.syngenta.remote.api.LoginService
//import com.syngenta.remote.mapper.ListingsDataNetworkMapper
//import com.syngenta.remote.model.ResponseWrapper
//import com.syngenta.remote.source.RemoteDataSourceImpl
//import io.reactivex.Observable
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import utils.TestDataGenerator
//
//@RunWith(JUnit4::class)
//class RemoteDataSourceImplTest {
//
//    @Mock
//    private lateinit var listingsService: LoginService
//
//    private val listingsMapper = ListingsDataNetworkMapper()
//
//    private lateinit var remoteDataSource: RemoteDataSource
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        remoteDataSource = RemoteDataSourceImpl(
//            listingsMapper,
//            listingsService
//        )
//    }
//
//
//    @Test
//    fun test_getListings_success() {
//        val listings = TestDataGenerator.generateListings()
//        val limit = 20
//
//        val mockResponse = ResponseWrapper(
//            null,
//            listings
//        )
//
//        Mockito.`when`(listingsService.getListings())
//            .thenReturn(Observable.just(mockResponse))
//
//        remoteDataSource.getListings(limit)
//            .test()
//            .assertSubscribed()
//            .assertValue { transactionsList ->
//                transactionsList.containsAll(
//                    listings.map { listingsMapper.from(it) }
//                )
//            }
//            .assertComplete()
//    }
//
//    @Test
//    fun test_getUserTransactions_error() {
//        val errorMsg = "ERROR"
//        val limit = 20
//
//        Mockito.`when`(listingsService.getListings())
//            .thenReturn(Observable.error(Throwable(errorMsg)))
//
//        remoteDataSource.getListings(limit)
//            .test()
//            .assertSubscribed()
//            .assertError {
//                it.message == errorMsg
//            }
//            .assertNotComplete()
//    }
//
//}