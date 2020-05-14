package com.example.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*

import com.example.ad340.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragment : Fragment() {
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments!!.getString(KEY_ZIPCODE) ?:""
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast,container,false)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }

        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            //wherever is "forecastItem" was "it" which represents a value pass to lambda
            //   val msg = getString(R.string.forecast_clicked_format,forecastItem.temp, forecastItem.description)
            //   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            showForecastDetails(forecast)
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
    private fun showForecastDetails(forecast: DailyForecast ){
        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp )
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)

    }

    companion object{
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }

    }

}
