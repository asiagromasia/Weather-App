package com.example.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.ad340.*
import kotlinx.android.synthetic.main.fragment_forecast_details.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.dateTextFD
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.imageViewFD as imageViewFD1

//val dateText = layout.findViewById<TextView>(R.id.dateTextFD)
//val forecastIcon = layout.findViewById<ImageView>(R.id.imageViewFD)
private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class ForecastDetailsFragment : Fragment() {
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
 //   private val dateText = view?.findViewById<TextView>(R.id.dateTextFD)
 //   private val forecastIcon = view.findViewById<ImageView>(R.id.imageViewFD)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details,container,false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val tempText = layout.findViewById<TextView>(R.id.tempText)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)
        val dateText = layout.findViewById<TextView>(R.id.dateTextFD)
        val forecastIcon = layout.findViewById<ImageView>(R.id.imageViewFD)
              //  val dateText = layout.findViewById<TextView>(R.id.dateText)
               // val forecastIcon = layout.findViewById<ImageView>(R.id.forecastIcon)

//        dateText.text = args.date.toString()

        // dateText.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))   from DailyForecastAdapter

                //   val iconId = dailyForecast.weather[0].icon
                //   forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
 //       forecastIcon.image= args.icon
        val iconId = args.icon
        forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
        dateText.text = DATE_FORMAT.format(Date(args.date* 1000))
        //val temp = intent.getFloatExtra("key_temp",0f)
        tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = args.description
        return layout
    }

}

