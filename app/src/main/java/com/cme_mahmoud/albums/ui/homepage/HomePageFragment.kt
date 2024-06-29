package com.cme_mahmoud.albums.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cme_mahmoud.albums.R
import com.cme_mahmoud.albums.base.BaseFragment
import com.cme_mahmoud.albums.databinding.FragmentLandingPageBinding
import com.cme_mahmoud.presentation.factory.ViewModelFactory
import com.cme_mahmoud.presentation.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomePageFragment : BaseFragment() {
    private lateinit var binding: FragmentLandingPageBinding

    @Inject
    lateinit var homePageModelFactory: ViewModelFactory
    private lateinit var homePageVM: HomePageViewModel


    private var networkStatus: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentLandingPageBinding.inflate(inflater, container, false)
        }
        return binding.root

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
                    print(it)
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePageVM.cachedAlbums.collect {

                    binding.textView.text = it.toString()
                }
            }
        }


    }


    override fun onBackPressed(): Boolean {
        return false
    }

}