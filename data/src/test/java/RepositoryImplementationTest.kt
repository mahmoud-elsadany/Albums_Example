//import com.syngenta.data.mapper.ListingsDomainDataMapper
//import com.syngenta.data.repository.ListingsRepositoryImpl
//import com.syngenta.data.repository.LocalDataSource
//import com.syngenta.data.repository.RemoteDataSource
//import com.syngenta.domain.repository.ListingsRepository
//import io.reactivex.Observable
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.Mockito.times
//import org.mockito.MockitoAnnotations
//import utils.TestDataGenerator
//
//@RunWith(JUnit4::class)
//class RepositoryImplementationTest {
//
//    private lateinit var listingsRepository: ListingsRepository
//
//    private val listingsMapper = ListingsDomainDataMapper()
//
//
//    @Mock
//    private lateinit var remoteDataSource: RemoteDataSource
//    @Mock
//    private lateinit var localDataSource: LocalDataSource
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        listingsRepository = ListingsRepositoryImpl(
//            listingsMapper,
//            localDataSource,
//            remoteDataSource
//        )
//    }
//
//    @Test
//    fun test_getListings_local_remote_interactions() {
//        val limit = 20
//        val listingsData = TestDataGenerator.generateListings()
//
//        Mockito.`when`(remoteDataSource.getListings(limit))
//            .thenReturn(Observable.just(listingsData))
//        Mockito.`when`(localDataSource.getListings(limit))
//            .thenReturn(Observable.just(listingsData))
//
//        val testSubscriber = listingsRepository.getListings(limit).test()
//
//        testSubscriber.assertSubscribed()
//            .assertValues(
//                listingsData.map { listingsMapper.from(it) },
//                listingsData.map { listingsMapper.from(it) }
//            )
//            .assertComplete()
//
//        Mockito.verify(localDataSource, times(1))
//            .saveListings( listingsData)
//
//        Mockito.verify(remoteDataSource, times(1))
//            .getListings(limit)
//    }
//
//    @Test
//    fun test_getListings_remote_error() {
//        val limit = 20
//
//        val listingsData = TestDataGenerator.generateListings()
//
//        Mockito.`when`(remoteDataSource.getListings(limit))
//            .thenReturn(Observable.error(Throwable()))
//        Mockito.`when`(localDataSource.getListings(limit))
//            .thenReturn(Observable.just(listingsData))
//
//        val testSubscriber = listingsRepository.getListings(limit).test()
//
//        testSubscriber.assertSubscribed()
//            .assertValue { transactions ->
//                transactions.containsAll(listingsData.map {
//                    listingsMapper.from(it)
//                })
//            }
//            .assertComplete()
//
//        Mockito.verify(localDataSource, times(1))
//            .getListings(limit)
//    }
//}