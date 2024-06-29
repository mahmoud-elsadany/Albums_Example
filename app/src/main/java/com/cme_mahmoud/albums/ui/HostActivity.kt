package com.cme_mahmoud.albums.ui

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cme_mahmoud.albums.R
import com.cme_mahmoud.albums.base.BaseActivity
import com.cme_mahmoud.albums.base.BaseFragment
import com.cme_mahmoud.albums.databinding.ActivityHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : BaseActivity() {
    private var handler: Handler? = null
    private var r: Runnable? = null
    private lateinit var navController: NavController
    private lateinit var binding: ActivityHostBinding
    var shutterWasClicked = MutableLiveData<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //disble dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        handler = Handler()
        r = Runnable {
            Toast.makeText(
                this,
                "user is inactive from last 10 minutes",
                Toast.LENGTH_SHORT
            ).show()

            //TODO navigate to login fragment

        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_nav_graph) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {

        val fragment =
            supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.get(
                0
            )
        if (fragment is BaseFragment) {
            if (fragment.onBackPressed())
                super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action: Int = event.action
        return when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    shutterWasClicked.value = true
                }
                true
            }
            // Note: this case is never called
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    shutterWasClicked.value = true
                }
                true
            }
            else -> super.dispatchKeyEvent(event)
        }
    }




    override fun onUserInteraction() {
        super.onUserInteraction()

        stopHandler()
    }

    private fun stopHandler() {
        handler!!.removeCallbacks(r!!)
    }

}