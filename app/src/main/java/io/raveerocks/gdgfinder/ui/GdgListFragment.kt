package io.raveerocks.gdgfinder.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.location.*
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import io.raveerocks.gdgfinder.R
import io.raveerocks.gdgfinder.adapter.GdgClickListener
import io.raveerocks.gdgfinder.adapter.GdgListAdapter
import io.raveerocks.gdgfinder.databinding.FragmentGdgListBinding
import io.raveerocks.gdgfinder.view.GdgListViewModel



class GdgListFragment : Fragment() {

    companion object{
        private const val LOCATION_PERMISSION = "android.permission.ACCESS_COARSE_LOCATION"
    }

    private val viewModel: GdgListViewModel by lazy {
        ViewModelProvider(this).get(GdgListViewModel::class.java)
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
        if (isGranted) {
            requestLastLocationOrStartLocationUpdates()
        } else {
            Log.e("GdgListFragment", "permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLastLocationOrStartLocationUpdates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val adapter = GdgListAdapter(GdgClickListener { chapter ->
            val destination = Uri.parse(chapter.website)
            startActivity(Intent(Intent.ACTION_VIEW, destination))
        })

        val binding = FragmentGdgListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.gdgChapterList.adapter = adapter

        viewModel.showNeedLocation.observe(viewLifecycleOwner
        ) { show ->
            if (show == true) {
                Snackbar.make(
                    binding.root,
                    "No location. Enable location in settings (hint: test with Maps) then check app permissions!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        viewModel.regionList.observe(viewLifecycleOwner, object : Observer<List<String>> {
            override fun onChanged(data: List<String>?) {
                data ?: return
                val chipGroup = binding.regionList
                val layoutInflater = LayoutInflater.from(chipGroup.context)
                val children = data.map { regionName ->
                    val chip = layoutInflater.inflate(R.layout.region, chipGroup, false) as Chip
                    chip.text = regionName
                    chip.tag = regionName
                    chip.setOnCheckedChangeListener { button, isChecked ->
                        viewModel.onFilterChanged(button.tag as String, isChecked)
                    }
                    chip
                }
                chipGroup.removeAllViews()
                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun requestLastLocationOrStartLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                LOCATION_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location == null) {
                startLocationUpdates(fusedLocationClient)
            } else {
                viewModel.onLocationUpdated(location)
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                LOCATION_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return
        }
        val request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER)
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                viewModel.onLocationUpdated(location)
            }
        }
        fusedLocationClient.requestLocationUpdates(request, callback, Looper.myLooper()!!)
    }
}


