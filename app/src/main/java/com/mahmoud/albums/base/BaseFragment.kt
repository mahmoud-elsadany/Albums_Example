package com.mahmoud.albums.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navOptions
import com.mahmoud.albums.R
import com.mahmoud.albums.util.LoadingScreen
import com.mahmoud.albums.util.network.NetworkStateManager
import com.mahmoud.presentation.base.BaseViewModel
import com.mahmoud.presentation.factory.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
abstract class BaseFragment : Fragment(), CoroutineScope{
    private val TAG = "runWithPermissions"
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var baseVM: BaseViewModel


    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    /**
     * You can hardcode the options like this
     * However for the sample purpose the options is dynamically generated based on the
     * values you have selected from the activity
     */


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerNetworkStatusChangeObserver()

        initUI()
    }

    private fun initUI() {
        baseVM = ViewModelProvider(this, viewModelFactory)[BaseViewModel::class.java]
        // listening for error and exceptions


    }

    fun showException(title: String?, message: String?, cancelable: Boolean) {
        // handle something when permissions are not granted and the request method cannot be called

        Toast.makeText(requireContext(),"$title \n $message",Toast.LENGTH_LONG).show()

    }

    fun showLoading(state: Boolean) {
        launch(Dispatchers.Main) {
            if (state) {

                LoadingScreen.displayLoadingWithText(
                    requireContext(),
                    context?.getString(R.string.please_wait),
                    false
                )
            } else {
                LoadingScreen.hideLoading()
            }
        }

    }



    private fun registerNetworkStatusChangeObserver() {
        NetworkStateManager.getInstance().networkConnectivityStatus
            .observe(requireActivity(), activeNetworkStateObserver)
    }


    private val activeNetworkStateObserver =
        Observer { isConnected: Boolean ->
            networkStatusChanged(isConnected)
        }

    open fun onBackPressed() = true

    open fun networkStatusChanged(isConnected: Boolean) {

    }



}