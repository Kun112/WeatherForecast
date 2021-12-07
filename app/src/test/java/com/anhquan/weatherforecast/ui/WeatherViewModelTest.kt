package com.anhquan.weatherforecast.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anhquan.weatherforecast.data.ResultWrapper
import com.anhquan.weatherforecast.data.WeatherData
import com.anhquan.weatherforecast.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.utility.RandomString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var resultWrapperObserver : Observer<ResultWrapper<MutableList<WeatherData>>>

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(weatherRepository)
    }

    @After
    fun after(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun GetWeather_SuccessResult() = runBlocking {
        val location = RandomString.make()
        val weather = mock(WeatherData::class.java)
        val listWeather = mutableListOf(weather, weather, weather)
        val resultWrapper = ResultWrapper<MutableList<WeatherData>>(listWeather)
        doReturn(resultWrapper).`when`(weatherRepository).getWeatherForecastByLocation(location)
        viewModel.getWeatherData(location).join()
        viewModel.resultWrapper.observeForever(resultWrapperObserver)
        verify(weatherRepository).getWeatherForecastByLocation(location)
        verify(resultWrapperObserver).onChanged(resultWrapper)
        assertEquals(3, resultWrapper.value?.size)
        assertEquals(viewModel.resultWrapper.value?.value, resultWrapper.value)
        viewModel.resultWrapper.removeObserver(resultWrapperObserver)
    }

    @Test
    fun GetWeather_ErrorResult() = runBlocking {
        val location = RandomString.make()
        val errorMsg = RandomString.make()
        val resultWrapper = ResultWrapper<MutableList<WeatherData>>(errorMsg)
        doReturn(resultWrapper).`when`(weatherRepository).getWeatherForecastByLocation(location)
        viewModel.getWeatherData(location).join()
        viewModel.resultWrapper.observeForever(resultWrapperObserver)
        verify(weatherRepository).getWeatherForecastByLocation(location)
        verify(resultWrapperObserver).onChanged(resultWrapper)
        assertEquals(viewModel.resultWrapper.value, (resultWrapper))
        viewModel.resultWrapper.removeObserver(resultWrapperObserver)
    }


    @Test
    fun GetWeather_InvalidInputLength() = runBlockingTest {
        val location = RandomString.make(2)
        val location2 = RandomString.make(1)
        verify(weatherRepository, never()).getWeatherForecastByLocation(location)
        verify(weatherRepository, never()).getWeatherForecastByLocation(location2)
    }
}