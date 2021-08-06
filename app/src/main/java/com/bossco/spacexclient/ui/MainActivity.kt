package com.bossco.spacexclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bossco.MyApplication
import com.bossco.spacexclient.R
import com.bossco.spacexclient.databinding.ActivityMainBinding
import com.bossco.spacexclient.viewmodels.AppViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appViewModel: AppViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getDaggerComponent().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        initData()

        subscribeData()
    }

    private fun subscribeData() {
        appViewModel.infoLiveData.observe(this, { value ->
            value?.let { info ->
                lifecycleScope.launch {
                    appViewModel.saveInfo(info)
                }
            }
        })


        appViewModel.infoMessage.observe(this, {value ->
            value?.let { info ->
                binding.info = info
            }

        })
    }

    private fun initData() {
        appViewModel.getInfo()
    }


    private fun initToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.title = getString(R.string.app_name)
    }
}