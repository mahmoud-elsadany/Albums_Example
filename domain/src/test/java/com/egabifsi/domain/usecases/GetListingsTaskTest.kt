//package com.syngenta.domain.usecases
//
//import com.syngenta.domain.repository.ListingsRepository
//import com.syngenta.domain.utils.TestDataGenerator
//import io.reactivex.Observable
//import io.reactivex.schedulers.Schedulers
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//
//@RunWith(JUnit4::class)
//class GetListingsTaskTest {
//
//    private lateinit var getUserListingsTask: GetListingsTask
//    @Mock
//    lateinit var listingsRepository: ListingsRepository
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        getUserListingsTask = GetListingsTask(
//            listingsRepository,
//            Schedulers.trampoline(),
//            Schedulers.trampoline()
//        )
//    }
//
//    @Test
//    fun test_getListings_success() {
//
//        val limit = 20
//        val listings = TestDataGenerator.generateListings()
//
//        Mockito.`when`(listingsRepository.getListings(limit))
//            .thenReturn(Observable.just(listings))
//
//        val testObserver = getUserListingsTask.buildUseCase(
//            GetListingsTask.Params(
//                limit
//            )
//        ).test()
//
//        testObserver
//            .assertSubscribed()
//            .assertValue { it.containsAll(listings) }
//    }
//
//    @Test
//    fun test_getListings_error() {
//
//        val limit = 20
//        val errorMsg = "ERROR OCCURRED"
//
//        Mockito.`when`(listingsRepository.getListings(limit))
//            .thenReturn(Observable.error(Throwable(errorMsg)))
//
//        val testObserver = getUserListingsTask.buildUseCase( GetListingsTask.Params(
//            limit
//        )).test()
//
//        testObserver
//            .assertSubscribed()
//            .assertError { it.message?.equals(errorMsg, false) ?: false }
//            .assertNotComplete()
//    }
//
//}