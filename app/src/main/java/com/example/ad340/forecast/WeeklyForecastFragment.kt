package com.example.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*

import com.example.ad340.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_forecast,container,false)
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?:""

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val forecastList: RecyclerView = view.findViewById(R.id.dailyForecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        //val zipcode = arguments!!.getString(KEY_ZIPCODE) ?:""
        // Inflate the layout for this fragment
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            //wherever is "forecastItem" was "it" which represents a value pass to lambda
            //   val msg = getString(R.string.forecast_clicked_format,forecastItem.temp, forecastItem.description)
            //   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            showForecastDetails(forecast)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }


        }
        forecastList.adapter = dailyForecastAdapter


        //Create the observer which updates the UI in response to forecast updates
        val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItems ->
            //update our list adapter
            //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
            dailyForecastAdapter.submitList(forecastItems)

        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

        forecastRepository.loadForecast(zipcode)
        return view
    }
    private fun showLocationEntry(){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }
    private fun showForecastDetails(forecast: DailyForecast ){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.description)
        findNavController().navigate(action)
    }

    companion object{
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }

    }

}
