package com.example.ad340.forecast


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
import coil.api.load
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.api.CurrentWeather

import com.google.android.material.floatingactionbutton.FloatingActionButton
//import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.text.SimpleDateFormat
//import java.util.*
//import com.example.ad340.api.DailyForecast
//import kotlinx.android.synthetic.main.fragment_current_forecast.*
//import kotlinx.android.synthetic.main.fragment_location_entry.*
//import kotlinx.android.synthetic.main.fragment_weekly_forecast.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    // private val args: CurrentForecastFragment by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast,container,false)
        val locationName =view.findViewById<TextView>(R.id.locationName)
        val tempText = view.findViewById<TextView>(R.id.tempText)
      //  val zipcode = arguments?.getString(KEY_ZIPCODE) ?:""
        val descriptionText = view.findViewById<TextView>(R.id.descriptionText)
        //  val date = view.findViewById<TextView>(R.id.dateText)   its today so no need for it
        val forecastIcon = view.findViewById<ImageView>(R.id.imageView)
        // val icon = view.findViewById<ImageView>(R.id.imageViewCF)


        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
       // showForecastDetails(forecast)


        //Create the observer which updates the UI in response to forecast updates
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            //   val currentWeatherObserver = Observer<CurrentWeather> { currentWeather ->
               locationName.text = weather.name
            //  locationName.text = args.locationName
               tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())

            val iconId = weather.weather[0].icon
            // val iconId = args.icon
            forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")

           //  descriptionText.text = weather.weather[1].description    why not 1?
             descriptionText.text = weather.weather[0].description
//            //description.text = args.description
  //           val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(temp, description, date, icon).
//            findNavController().navigate(action)
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocation ->
            when(savedLocation){
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
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
