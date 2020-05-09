package com.example.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

//helper fun
fun formatTempForDisplay(temp:Float, tempDisplaySetting: TempDisplaySetting): String {
    //return String.format("%.2f°", temp)
    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f)*(5f/9f)
            String.format("%.2f C°", temp)
        }
    }
}
fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose display unit")
        .setMessage("Choose which temp unit to use for temp display")
        .setPositiveButton("F°"){_, _->
            //Toast.makeText(this,"show using F", Toast.LENGTH_SHORT).show()
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        //one version and later simplify
        //.setNeutralButton("C°", object : DialogInterface.OnClickListener {
        //   override fun onClick(p0: DialogInterface?, which: Int) {
        .setNeutralButton("C°"){_, _->
            // Toast.makeText(this,"show using C", Toast.LENGTH_SHORT).show()
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener{
            Toast.makeText(context,"Setting will take affect on app restart", Toast.LENGTH_SHORT).show()
        }

    dialogBuilder.show()
}

