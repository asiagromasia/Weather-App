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
import androidx.navigation.fragment.navArgs
import coil.api.load
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.api.CurrentWeather

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.ad340.api.DailyForecast
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_location_entry.*
import kotlinx.android.synthetic.main.fragment_weekly_forecast.*

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
        val locationName: TextView =view.findViewById(R.id.locationName)  //not in weekly
        val tempText: TextView = view.findViewById(R.id.tempTextCF)        //not in weekly
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?:""
        // I added
          val description = view.findViewById<TextView>(R.id.descriptionTextCF)     //nnot in weekly
        //  val date = view.findViewById<TextView>(R.id.dateText)   its today so no need for it
          val forecastIcon = view.findViewById<ImageView>(R.id.imageViewCF)         //nnot in weekly
        // val icon = view.findViewById<ImageView>(R.id.imageViewCF)


        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
       // showForecastDetails(forecast)

//     NO Need here: since no recycler -dailyForecastList
//        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { DailyForecast ->
//            //wherever is "forecastItem" was "it" which represents a value pass to lambda
//            //   val msg = getString(R.string.forecast_clicked_format,forecastItem.temp, forecastItem.description)
//            //   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//            showForecastDetails(DailyForecast)
//
//        }
//        // dailyForecastList.adapter = dailyForecastAdapter


        //Create the observer which updates the UI in response to forecast updates
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            //   val currentWeatherObserver = Observer<CurrentWeather> { currentWeather ->
               locationName.text = weather.name
            //  locationName.text = args.locationName
               tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
            //dateText.text = weather.forecast.
            //dateText.text = DATE_FORMAT.format(Date(args.date *1000))
            //dateText.text = DATE_FORMAT.format(Date(weather.forecast.date * 1000))
            val iconId = weather.forecast.icon       //AAADDDEEED .icon
            // val iconId = args.icon
            forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
//
//           // val description = forecast.weather[0].description
            description.text = weather.forecast.description
//            //description.text = args.description
//            val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(temp, description, date, icon).
//            findNavController().navigate(action)
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

      //  locationRepository = LocationRepository((requireContext()))     it was without homework
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

    private fun showForecastDetails(forecast: DailyForecast ){
       // locationName.text = weather.name
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val date = forecast.date
        val icon = forecast.weather[0].icon
        val iconId = forecast.weather[0].icon
       forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")

        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(temp, description, date, icon)
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
