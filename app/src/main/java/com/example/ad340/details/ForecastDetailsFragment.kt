package com.example.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.ad340.*
import com.example.ad340.databinding.FragmentForecastDetailsBinding
import kotlinx.android.synthetic.main.fragment_forecast_details.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.dateTextFD
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_forecast_details.view.imageViewFD as imageViewFD1



class ForecastDetailsFragment : Fragment() {
    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory

    //"by" means we will use a delegate, which here it is saving for us a state so it doesn't rebuild when screen rotation happens
    //it hands us back a viewModel which is automatically saved and cashed for us
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory}
    )

    private var _binding: FragmentForecastDetailsBinding? = null
    // this property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    //private val binding get()= _binding!!

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState>{viewState ->
        //update the UI
            binding.tempText.text = formatTempForDisplay(viewState.temp, tempDisplaySettingManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
            binding.dateTextFD.text = viewState.date
            binding.imageViewFD.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)
        //adding following bonding get rids of the single icon screen
        //viewModel.processArgs(args)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

