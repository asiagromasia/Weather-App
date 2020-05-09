package com.example.ad340.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ad340.*


class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.tempText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)

        //accessing intent data  getintent instead of intent is ok as well, you can put and get, here get and float cause that's what is here
        //val temp = intent.getFloatExtra("key_temp",0f)

        val temp = intent.getFloatExtra("key_temp",0f)
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = intent.getStringExtra("key_description")
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


}

