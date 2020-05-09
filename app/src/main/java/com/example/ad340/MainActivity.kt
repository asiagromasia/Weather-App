package com.example.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.details.ForecastDetailsActivity

class MainActivity : AppCompatActivity() {
    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingManager = TempDisplaySettingManager(this)
        // referencing elements to variables
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        //adding click listener to the button
        enterButton.setOnClickListener {
            //write "this"and context will pop, same with text just write the value "", toast - to check if it works first
            //Toast.makeText(this,"Button Clicked!",Toast.LENGTH_SHORT).show()
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length !=5){
                Toast.makeText(this,R.string.zipcode_entry_error,Toast.LENGTH_SHORT).show()
            } else{
                //following toast will show what we entered
                //Toast.makeText(this,zipcode,Toast.LENGTH_SHORT).show()
                forecastRepository.loadForecast(zipcode)
            }
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            //wherever is "forecastItem" was "it" which represents a value pass to lambda
         //   val msg = getString(R.string.forecast_clicked_format,forecastItem.temp, forecastItem.description)
         //   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            showForecastDetails(forecast)
        }
        forecastList.adapter = dailyForecastAdapter


        //Create the oserver which updates the UI in response to forecast updates
        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            //update our list adapter
            //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
            dailyForecastAdapter.submitList(forecastItems)

        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle item selection in menu
        return when (item.itemId){
            R.id.tempDisplaySetting -> {
                //Toast.makeText(this,"clicked menu item", Toast.LENGTH_SHORT).show()
                showTempDisplaySettingDialog(this,tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun showForecastDetails(forecast: DailyForecast ){
        val forecastDetailsIntent = Intent(this,ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp )
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)

    }
}
