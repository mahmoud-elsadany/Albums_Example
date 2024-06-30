package com.cme_mahmoud.albums.ui.albumDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cme_mahmoud.albums.R
import com.cme_mahmoud.albums.base.BaseFragment
import com.cme_mahmoud.albums.ui.homepage.HomePageScreen
import com.cme_mahmoud.albums.util.general.Utils
import com.cme_mahmoud.presentation.factory.ViewModelFactory
import com.cme_mahmoud.presentation.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumDetailsFragment: BaseFragment() {

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
                Utils.ChosenAlbum?.let {
                    AlbumDetailsScreen(it) {
                        openItunesStore(it.iTunesLink)
                    }
                }
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

    }

    private fun openItunesStore(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


    override fun onBackPressed(): Boolean {
        return true
    }

}