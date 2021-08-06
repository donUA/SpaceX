package com.bossco.spacexclient.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.TtsSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bossco.MyApplication
import com.bossco.spacexclient.R
import com.bossco.spacexclient.adapters.LaunchAdapter
import com.bossco.spacexclient.databinding.ActivityMainBinding
import com.bossco.spacexclient.interfaces.MainInterface
import com.bossco.spacexclient.models.Launch
import com.bossco.spacexclient.viewmodels.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appViewModel: AppViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var launchAdapter: LaunchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getDaggerComponent().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecycler()

        initToolbar()

        initData()

        initLaunchData()

        subscribeData()
    }

    private fun setUpRecycler() {
        launchAdapter = LaunchAdapter(object : MainInterface {
            override fun openArticle(launch: Launch) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.article_link))
                    startActivity(browserIntent)


            }

            override fun openWikipedia(launch: Launch) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.wikipedia))
                    startActivity(browserIntent)



            }

            override fun openVideo(launch: Launch) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.video_link))
                    startActivity(browserIntent)


            }
        })

        binding.recyclerLaunches.adapter = launchAdapter
    }

    private fun initLaunchData() {
        appViewModel.getLaunchData()
    }

    private fun subscribeData() {

        appViewModel.messagesLiveData.observe(this, {value ->
            value?.let {
                Snackbar.make(binding.rootMain, it, Snackbar.LENGTH_SHORT).show()
            }

        })


        appViewModel.progressLiveData.observe(this, { value ->
            value?.let { loading ->
                binding.isLoading = loading
            }
        })
        appViewModel.infoLiveData.observe(this, { value ->
            value?.let { info ->
                lifecycleScope.launch {
                    appViewModel.saveInfo(info)
                }
            }
        })

        appViewModel.launchLiveData.observe(this, { value ->

            value?.let { launchList ->
                Timber.i("launches list size ${value.size}")
                lifecycleScope.launch {
                    appViewModel.saveLaunches(launchList)
                }
            }
        })


        appViewModel.infoMessage.observe(this, { value ->
            value?.let { info ->
                binding.info = info
            }

        })

        appViewModel.launchMessage.observe(this, { value ->
            value?.let { list: List<Launch> ->
                launchAdapter.submitList(list)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.filter -> {
                openFilterOptions()
                true

            }
            else -> {return super.onOptionsItemSelected(item)}
        }

    }

    private fun openFilterOptions() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter, menu)
        return true
    }
}