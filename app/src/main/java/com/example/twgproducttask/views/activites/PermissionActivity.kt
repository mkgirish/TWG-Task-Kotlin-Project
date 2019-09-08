package com.example.twgproducttask.views.activites

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AlertDialog
import com.example.twgproducttask.R

class PermissionActivity : AppCompatActivity() {


    var mIsRequiredPermissionCheck = false

    internal var checker : com.example.twgproducttask.util.PermissionChecker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        checker = com.example.twgproducttask.util.PermissionChecker(applicationContext)
        if (intent == null || !intent.hasExtra(PERMISSION_EXTRA_FLAG)) {
            throw RuntimeException("PermissionsActivity needs to be started by static method startActivityForResult!")
        }
        mIsRequiredPermissionCheck = true
    }




    override fun onResume() {
        super.onResume()

        if (mIsRequiredPermissionCheck) {
            if (checker!!.ifLackPermissions(getPermissions())!!) {
                requestPermissions(getPermissions())
            } else {
                allPermissionsGranted()
            }
        } else {
            mIsRequiredPermissionCheck = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (PERMISSION_REQUEST_CODE == requestCode && hasAllPermissionsGranted(grantResults)) {
            mIsRequiredPermissionCheck = true
            allPermissionsGranted()
        } else {
            mIsRequiredPermissionCheck = false
            showMissingPermissionDialog()
        }
    }

    private fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Help")
        builder.setMessage("Current app lacks necessary permissions. \n\nPlease click \"Settings\" - \"Permission\" - to grant permissions. \n\nThen click back button twice to return.")

        builder.setNeutralButton("Quit") { dialog, which ->
            setResult(PERMISSION_DENIED)
            finish()
        }

        builder.setPositiveButton(
            "Settings"
        ) { dialog, which -> startAppSettings() }

        builder.show()
    }

    fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
        startActivity(intent)
    }

    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    private fun allPermissionsGranted() {
        setResult(PERMISSION_GRANTED)
        finish()
    }

    fun requestPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    fun getPermissions(): Array<String> {
        return intent.getStringArrayExtra(PERMISSION_EXTRA_FLAG)
    }

    companion object {
        val PERMISSION_EXTRA_FLAG = "nz.co.warehouseandroidtest.permission.extra_permission"
        var PERMISSION_GRANTED = 0
        var PERMISSION_DENIED = 1

        val PACKAGE_URL_SCHEME = "package:"



        val PERMISSION_REQUEST_CODE = 0

        fun startActivityForResult(activity: Activity, requestCode: Int, permissions: Array<String>) {
            val intent = Intent()
            intent.setClass(activity, PermissionActivity::class.java)
            intent.putExtra(PERMISSION_EXTRA_FLAG, permissions)
            ActivityCompat.startActivityForResult(activity, intent, requestCode, null)
        }
    }
}
