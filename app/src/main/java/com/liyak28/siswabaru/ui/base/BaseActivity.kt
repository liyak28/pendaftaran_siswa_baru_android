package com.liyak28.siswabaru.ui.base

import android.Manifest
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.liyak28.siswabaru.R
import com.liyak28.siswabaru.ui.dialog.createAlertDialog
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    companion object {

        private val TAG = BaseActivity::class.java.simpleName

        const val RP_LOCATION = 124
        const val RP_STORAGE = 126

        private val LOCATIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    fun askLocationPermission() {

        this.createAlertDialog(
            title = "",
            message = getString(R.string.core_rationale_location),
            positiveLabel = "Oke",
            negativeLabel = "Cancel",
            positiveButton = { positiveButtonAskPermissionLocation() }
        ).show()

    }

    fun positiveButtonAskPermissionLocation() {
        ActivityCompat.requestPermissions(
            this,
            LOCATIONS, RP_LOCATION
        )
    }

    fun accessPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RP_STORAGE)
    }

    fun askStoragePhotoPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            RP_STORAGE
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted: " + requestCode + " : " + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied: " + requestCode + " : " + perms.size)
        var title = ""
        var description = ""
        when (requestCode) {
            RP_LOCATION -> toast(R.string.core_permission_deny_location)
            RP_STORAGE -> toast(R.string.core_permission_deny_storage)
        }

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (title.isNotBlank() && description.isNotBlank()) {
                AppSettingsDialog.Builder(this)
                    .setTitle(title)
                    .setRationale(description)
                    .setPositiveButton(getString(R.string.core_permission_open_settings))
                    .setNegativeButton(getString(R.string.core_permission_cancel))
                    .build().show()
            }
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted: $requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied: $requestCode")
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun toast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}