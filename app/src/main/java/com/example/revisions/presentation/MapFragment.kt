package com.example.revisions.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.revisions.R
import com.example.revisions.data.LocationDto
import com.example.revisions.databinding.FragmentMapBinding
import com.example.revisions.resource.Resource

import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mMap: GoogleMap? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private val locationViewModel: LocationViewModel by viewModels()

    override fun start() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getUserLocation()
                } else {
                    // Toast
                }
            }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if (!isLocationEnabled()) {
            promptEnableLocation()
        } else {
            getUserLocation()
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewLifecycleOwner.lifecycleScope.launch {
            locationViewModel.locations.collect { resource ->
                when (resource) {
                    is Resource.Success -> {

                        addMarkersToMap(resource.data)
                    }

                    is Resource.Error -> {
                        val errorMessage = resource.errorMessage
                        //error message
                    }
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap?.isMyLocationEnabled = true
            getUserLocation()
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getUserLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(10000)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        val userLocation = LatLng(location.latitude, location.longitude)
                        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                        mMap?.addMarker(MarkerOptions().position(userLocation).title("My Location"))

                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addMarkersToMap(locations: List<LocationDto>) {
        locations.forEach { location ->
            val latLng = LatLng(location.lat, location.lan)
            val marker = mMap?.addMarker(MarkerOptions().position(latLng).title(location.title))

            marker?.tag = location
        }

        mMap?.setOnMarkerClickListener { marker ->
            val location = marker.tag as? LocationDto
            location?.let {
                binding.locationTitle.text = it.title
                binding.locationAddress.text = it.address
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            true
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun promptEnableLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (isLocationEnabled()) {
            getUserLocation()
        }
    }
}
