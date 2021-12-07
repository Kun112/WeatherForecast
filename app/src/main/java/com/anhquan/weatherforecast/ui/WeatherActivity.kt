package com.anhquan.weatherforecast.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhquan.weatherforecast.R
import com.anhquan.weatherforecast.databinding.ActivityMainBinding
import com.anhquan.weatherforecast.repository.WeatherRepositoryImpl
import com.anhquan.weatherforecast.utils.ViewModelFactory

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        weatherViewModel = ViewModelProvider(
            this,
            ViewModelFactory(WeatherRepositoryImpl.getInstance(this))
        )[WeatherViewModel::class.java]
        setupRecyclerView()
        addListeners()
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter(this)
        binding.rvWeatherData.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvWeatherData.setHasFixedSize(true)
        binding.rvWeatherData.isNestedScrollingEnabled = false
        binding.rvWeatherData.adapter = weatherAdapter
    }

    private fun addListeners() {
        binding.btnGetWeather.setOnClickListener {
            weatherViewModel.getWeatherData(binding.edtLocation.text.toString())
        }

        weatherViewModel.isLoading.observe(this, { showLoading ->
            if (showLoading) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })

        weatherViewModel.resultWrapper.observe(this, { result ->
            if (result.errorMsg != null) {
                showAlertDialog(result.errorMsg)
            } else {
                weatherAdapter.setData(result.value ?: mutableListOf())
            }
        })
    }

    private fun showAlertDialog(errorMsg: String) {
        val dialogBuilder = AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.dialog_error, errorMsg))
            setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }
        dialogBuilder.show()
    }
}