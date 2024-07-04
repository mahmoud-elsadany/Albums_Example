package com.mahmoud.albums.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmoud.albums.R
import com.mahmoud.albums.base.BaseFragment
import com.mahmoud.albums.util.general.Utils.ChosenAlbum
import com.mahmoud.presentation.factory.ViewModelFactory
import com.mahmoud.presentation.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomePageFragment : BaseFragment() {

    @Inject
    lateinit var homePageModelFactory: ViewModelFactory
    private lateinit var homePageVM: HomePageViewModel


    private var networkStatus: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {




                val albums = homePageVM.albums.collectAsState(initial = emptyList()).value


                HomePageScreen(albums = albums, onAlbumClick = { album ->
                    ChosenAlbum = album
                    val action =
                        HomePageFragmentDirections.actionHomePageFragmentToAlbumDetailsFragment()
                    findNavController().navigate(action)
                }, onRefresh = {
                    homePageVM.getAllRemoteAlbums()
                })


            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initMainObservations()
    }

    override fun networkStatusChanged(isConnected: Boolean) {
        super.networkStatusChanged(isConnected)
        networkStatus = isConnected
    }

    private fun initMainObservations() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePageVM.exceptionState.collect {
                    println("exceptionState: " + it)
                    showException(
                        context?.getString(R.string.error_title),
                        context?.getString(R.string.error_mssg),
                        false
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePageVM.loadingState.collect {
                    showLoading(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePageVM.apiErrorState.collect {

                    print(it.errorMessage)
                    showException(
                        context?.getString(R.string.error_title),
                        context?.getString(R.string.error_mssg),
                        false
                    )

                }
            }
        }
    }


    private fun initUI() {
        homePageVM =
            ViewModelProvider(this, homePageModelFactory)[HomePageViewModel::class.java]


        homePageVM.hasCachedAlbums()

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                homePageVM._albums.collect {
//                    println("albums need to shown owner: $it")
//
//                }
//            }
//        }
    }


    override fun onBackPressed(): Boolean {
        return true
    }

}