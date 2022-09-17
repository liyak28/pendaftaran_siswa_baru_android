package com.liyak28.siswabaru.ui.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.liyak28.siswabaru.common.utils.viewBinding
import com.liyak28.siswabaru.data.const.Map
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.data.state.ViewState
import com.liyak28.siswabaru.databinding.ActivityStudentCreateBinding
import com.liyak28.siswabaru.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentCreateActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityStudentCreateBinding::inflate)
    private val viewModel by viewModels<StudentViewModel>()

    private var latitude: Double? = Map.DEFAULT_LAT
    private var longitude: Double? = Map.DEFAULT_LNG

    companion object {
        const val ARG_LATITUDE = "latitude"
        const val ARG_LONGTITUDE = "longitude"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getIntentData()
        initView()
        startObserve()

    }

    private fun getIntentData() {
        latitude = intent?.extras?.getDouble(ARG_LATITUDE, Map.DEFAULT_LAT)
        longitude= intent?.extras?.getDouble(ARG_LONGTITUDE, Map.DEFAULT_LNG)
    }

    private fun initView() {

        binding.tvLat.text = "Latitude : $latitude"
        binding.tvLng.text = "Longtitude : $longitude"

        binding.btnSubmit.setOnClickListener {

            val name = binding.etName.text.toString()
            val kk = binding.etKk.text.toString()
            val nisn = binding.etNisn.text.toString()
            val akreditas = binding.etAkreditasi.text.toString()
            val school = binding.etSchool.text.toString()

            viewModel.insertStudent(
                StudentsEntity(
                    nisn = nisn,
                    name = name,
                    nomorKk = kk,
                    akreditasi = akreditas,
                    asalSekolah = school,
                    lat = latitude,
                    lng = longitude
                )
            )

        }

    }

    private fun startObserve() {

        viewModel.studentCreateState.observe(this) {
            when (it) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ViewState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}