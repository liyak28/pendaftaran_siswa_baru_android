package com.liyak28.siswabaru.ui.map

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.liyak28.siswabaru.common.utils.viewBinding
import com.liyak28.siswabaru.data.const.Map
import com.liyak28.siswabaru.databinding.ActivityMapBinding
import com.liyak28.siswabaru.ui.student.StudentCreateActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding by viewBinding(ActivityMapBinding::inflate)

    // map
    private lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null
    private lateinit var mapView: View

    private var latitude = Map.DEFAULT_LAT
    private var longitude = Map.DEFAULT_LNG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        setupMap()
    }

    private fun initView() {

        binding.btnSubmit.setOnClickListener {

            if (latitude == 0.0 || longitude == 0.0) {
                Toast.makeText(this, "Harap pilih lokasi", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, StudentCreateActivity::class.java)
                intent.putExtra(StudentCreateActivity.ARG_LATITUDE, latitude)
                intent.putExtra(StudentCreateActivity.ARG_LONGTITUDE, longitude)
                startActivity(intent)
            }

        }

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.run {
            setupCameraDraggingListener()
        }

        moveMapCamera(latitude, longitude)

    }

    private fun setupMap() {
        mapFragment = supportFragmentManager.findFragmentById(binding.fMap.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = binding.fMap
    }

    private fun setupCameraDraggingListener() {
        googleMap?.run {
            setOnCameraIdleListener {
                val location = cameraPosition.target
                getAddress(location.latitude, location.longitude)
            }
        }
    }

    private fun moveMapCamera(lat: Double?, long: Double?) {
        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(lat ?: latitude, long ?: longitude),
                Map.DEFAULT_ZOOM_LV
            )
        )

        setLog("Lat : $lat")
        setLog("long : $long")

    }

    private fun getAddress(lat: Double?, lng: Double?) {

        try {

            val addressList: List<android.location.Address>
            val geocoder = Geocoder(this, Locale.getDefault())

            addressList = geocoder.getFromLocation(
                lat ?: 0.0,
                lng ?: 0.0,
                1
            )

            if (addressList.isNotEmpty()) {
                val completeAddress = addressList[0].getAddressLine(0).orEmpty()
                val province = addressList[0].adminArea
                val district = addressList[0].subAdminArea
                val subDistrict = addressList[0].subLocality
                val postalCode = addressList[0].postalCode
                val firstLine = completeAddress.split(",").firstOrNull().orEmpty()

                binding.tvAddressLocation.text = "$province, $district, $subDistrict, $firstLine"

                setLog("map lat : $lat")
                setLog("map lng : $lng")

            }

        } catch (error: Exception) {

        }

    }

    private fun setLog(msg: String) {
        Log.e("map", msg)
    }

}