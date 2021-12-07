package com.anhquan.weatherforecast.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anhquan.weatherforecast.R
import com.anhquan.weatherforecast.data.WeatherData
import com.anhquan.weatherforecast.databinding.ItemWeatherDataBinding
import com.anhquan.weatherforecast.utils.DateUtil

class WeatherAdapter(val context: Context) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    private var weatherDataList = mutableListOf<WeatherData>()

    fun setData(newWeatherData: MutableList<WeatherData>) {
        this.weatherDataList.clear()
        this.weatherDataList.addAll(newWeatherData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherDataBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = weatherDataList[position]
        holder.bindItem(data)
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    inner class ViewHolder(private val binding: ItemWeatherDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: WeatherData) {
            binding.tvDate.text = context.getString(R.string.date, DateUtil.formatDateTime(data.dt * 1000))
            val avgTemp = ((data.temp.min + data.temp.max) / 2).toInt()
            binding.tvAvgTemp.text = context.getString(R.string.avg_temp, avgTemp)
            var description = ""
            for (index in data.weather.indices) {
                description += if (index < data.weather.size - 1) {
                    "${data.weather[index].description} ,"
                } else {
                    data.weather[index].description
                }
            }
            binding.tvDescription.text = context.getString(R.string.description, description)
            binding.tvHumidity.text = context.getString(R.string.humidity, data.humidity.toString())
            binding.tvPressure.text = context.getString(R.string.pressure, data.pressure.toString())
        }
    }
}