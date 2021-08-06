package com.bossco.spacexclient.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.bossco.MyApplication
import com.bossco.spacexclient.R
import com.bossco.spacexclient.adapters.LaunchAdapter
import com.bossco.spacexclient.databinding.ActivityMainBinding
import com.bossco.spacexclient.databinding.ItemFilterBinding
import com.bossco.spacexclient.interfaces.MainInterface
import com.bossco.spacexclient.models.Launch
import com.bossco.spacexclient.viewmodels.AppViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
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
        appViewModel.getLaunchData(null, null, null)
    }

    private fun subscribeData() {

        appViewModel.messagesLiveData.observe(this, { value ->
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
            binding.isInfoNull = value == null
            value?.let { info ->
                lifecycleScope.launch {
                    appViewModel.saveInfo(info)
                }
            }
        })

        appViewModel.launchLiveData.observe(this, { value ->

            binding.isDataNull = value == null
            Timber.i("data null ${value == null}")

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
        supportActionBar?.title = getString(R.string.app_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                openFilterOptions()
                true

            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun openFilterOptions() {

        val arrayListSuccessStatus = arrayListOf<String>("All", "Success", "Failed")
        val arrayListOrderTypes = arrayListOf<String>("All", "Ascending", "Descending")
        val successStatusAdapter =
            ArrayAdapter(this@MainActivity, R.layout.list_item, arrayListSuccessStatus)
        val orderAdapter = ArrayAdapter(this@MainActivity, R.layout.list_item, arrayListOrderTypes)


        val bottomSheet = BottomSheetDialog(this)
        val layout = ItemFilterBinding.inflate(layoutInflater)
        bottomSheet.setContentView(layout.root)
        bottomSheet.show()

        var timeRange: androidx.core.util.Pair<Long, Long>? = null
        var successStatus: String? = null
        var orderStatus: String? = null

        with(layout) {
            selectDateTxtEdit.setOnClickListener {
                val constraintsBuilder =
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointBackward.now())
                val builder =
                    MaterialDatePicker.Builder.dateRangePicker().setTitleText("Select range")
                        .setCalendarConstraints(constraintsBuilder.build())
                val picker = builder.build()
                picker.show(
                    (this@MainActivity as AppCompatActivity).supportFragmentManager,
                    picker.toString()
                )
                picker.addOnCancelListener {
                    Timber.i("Picker was cancelled")
                }
                picker.addOnNegativeButtonClickListener {
                    Timber.i("Negative button was clicked")
                }
                picker.addOnPositiveButtonClickListener {
                    Timber.i("Date range = ${picker.headerText} from ${it.first} to ${it.second}")
                    selectDateTxtEdit.setText(picker.headerText)
                    timeRange = it
                }
            }

            selectSuccessTxtEdit.setAdapter(successStatusAdapter)
            selectSuccessTxtEdit.setText(arrayListSuccessStatus[0], false)
            selectSuccessTxtEdit.setOnItemClickListener { parent, view, position, id ->
                val selected = parent.getItemAtPosition(position) as String
                if (selected.isNotEmpty()) {
                    if (selected == "ALl") {
                        successStatus = null
                    } else {
                        successStatus = selected
                    }
                    selectSuccessTxtEdit.setText(arrayListSuccessStatus[position], false)
                }

            }

            selectOrderTxtEdit.setAdapter(orderAdapter)
            selectOrderTxtEdit.setText(arrayListOrderTypes[0], false)
            selectOrderTxtEdit.setOnItemClickListener { parent, view, position, id ->
                val selected = parent.getItemAtPosition(position) as String
                if (selected.isNotEmpty()) {
                    if (selected == "All") {
                        orderStatus = null
                    } else {
                        orderStatus = selected
                    }
                    selectOrderTxtEdit.setText(arrayListOrderTypes[position], false)
                }
            }


            finishFilter.setOnClickListener {
                filterLaunches(timeRange, successStatus, orderStatus)
                bottomSheet.dismiss()
            }

            closeFilter.setOnClickListener {
                bottomSheet.dismiss()
            }

        }


    }

    private fun filterLaunches(
        timeRange: Pair<Long, Long>?,
        successStatus: String?,
        orderStatus: String?
    ) {
        val order = when (orderStatus) {
            "Ascending" -> {
                "asc"
            }
            "Descending" -> {
                "des"
            }
            else -> null
        }

        appViewModel.getLaunchData(
            getDateTime(timeRange?.first),
            getDateTime(timeRange?.second), order
        )
        appViewModel.deleteLaunches()

    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Long?): String? {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            if (s == null) {
                return null
            } else {
                val netDate = Date(s)
                return sdf.format(netDate)

            }
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter, menu)
        return true
    }
}