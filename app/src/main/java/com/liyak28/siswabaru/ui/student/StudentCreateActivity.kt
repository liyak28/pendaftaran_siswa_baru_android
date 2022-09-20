package com.liyak28.siswabaru.ui.student

import android.Manifest
import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.liyak28.siswabaru.R
import com.liyak28.siswabaru.common.utils.viewBinding
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.data.state.ViewState
import com.liyak28.siswabaru.databinding.ActivityStudentCreateBinding
import com.liyak28.siswabaru.ui.base.BaseActivity
import com.liyak28.siswabaru.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class StudentCreateActivity : BaseActivity() {

    private val binding by viewBinding(ActivityStudentCreateBinding::inflate)
    private val viewModel by viewModels<StudentViewModel>()

    companion object {
        private const val LAKI = "L"
        private const val PEREMPUAN = "P"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startObserve()
        initListener()
        askStoragePhotoPermission()

    }

    private fun initListener() {

        binding.btnLocation.setOnClickListener {
            getLocationLatLong()
        }

        binding.btnChoosePhoto.setOnClickListener {
            getPhotoFromStorage()
        }

        binding.rgGender.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(rg: RadioGroup?, id: Int) {
                when (id) {
                    R.id.rbBoy -> {
                        viewModel.gender = LAKI
                    }
                    else -> {
                        viewModel.gender = PEREMPUAN
                    }
                }
            }
        })

        binding.btnSubmit.setOnClickListener {

            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()

            if (viewModel.image == null) {
                toast("Harap pilih photo")
            } else {
                val photoFileName = getAndSavePhoto(viewModel.image)

                viewModel.insertStudent(
                    StudentsEntity(
                        name = name,
                        phone = phone,
                        address = address,
                        gender = viewModel.gender,
                        image = photoFileName ?: "",
                        lat = viewModel.latitude,
                        lng = viewModel.longitude
                    )
                )
            }

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

    private fun getLocationLatLong() {

        val client = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission location
            askLocationPermission()
            return
        }

        client.lastLocation.addOnSuccessListener(object : OnSuccessListener<Location> {

            override fun onSuccess(location: Location?) {
                with(binding) {
                    tvLat.text = "latitude : ${location?.latitude}"
                    tvLng.text = "longitude : ${location?.longitude}"
                }

                viewModel.latitude = location?.latitude
                viewModel.longitude = location?.longitude
            }
        })

    }

    private fun getPhotoFromStorage() {

        if (ActivityCompat.checkSelfPermission(
                this,
                permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            toast("Aplikasi ini perlu izin untuk mengakses gambar")
            askStoragePhotoPermission()
            return
        }

        accessPhotoFromGallery()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RP_STORAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    showImageFromUri(data!!.data!!)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun showImageFromUri(uri: Uri) {
        try {
            binding.ivProfile.setImageURI(uri)
            val stream = contentResolver.openInputStream(uri)
            viewModel.image = BitmapFactory.decodeStream(stream)
        } catch (error: Exception) {
            toast("Gagal menampilkan foto yang dipilih")
        }

    }

    fun getAndSavePhoto(bitmap: Bitmap?): String? {
        val file: File = applicationContext.getDir("data", MODE_PRIVATE)
        val folder = File(file, "image")
        if (!folder.exists()) {
            folder.mkdir()
        }
        val imgName = Math.random().toString() + "image.jpg"
        val imgFile = File(folder, imgName)
        try {
            val imgOut = FileOutputStream(imgFile)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, imgOut)
            imgOut.flush()
            imgOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imgFile.absoluteFile.toString()
    }

}