package com.example.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        // referencing elements to variables
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)
        val enterButton: Button = view.findViewById(R.id.enterButton)

        //adding click listener to the button
        enterButton.setOnClickListener {
            //write "this"and context will pop, same with text just write the value "", toast - to check if it works first
            //Toast.makeText(this,"Button Clicked!",Toast.LENGTH_SHORT).show()
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length !=5){
                Toast.makeText(requireContext(),R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            } else {
                //following toast will show what we entered
                //Toast.makeText(requireContext(),"Zipcode Entered", Toast.LENGTH_SHORT).show()
               findNavController().navigateUp()
            }
        }

        return view
    }

}
