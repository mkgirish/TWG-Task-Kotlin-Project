package com.example.twgproducttask.views.activites

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.twgproducttask.R
import com.example.twgproducttask.util.PermissionChecker
import com.example.twgproducttask.views.activites.fragments.ProductDetailFragment
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_bar_code_scan.*

class BarCodeScanActivity : AppCompatActivity() {

    internal var permissions = arrayOf(Manifest.permission.CAMERA)

    internal var checker : PermissionChecker? = null

    val REQUEST_PERMISSION_CODE = 0
    private var isOpen = false

    private val captureFragment = CaptureFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_scan)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        checker = PermissionChecker(applicationContext)
        iv_flashlight.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_off_black_24dp))
        iv_flashlight.setOnClickListener { view ->
            toggleFlashlight()
        }

        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_zxing_scan)

        captureFragment.analyzeCallback = analyzeCallback

        supportFragmentManager.beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit()

    }

    override fun onResume() {
        super.onResume()
        if (checker!!.ifLackPermissions(permissions)!!) {
            PermissionActivity.startActivityForResult(this, REQUEST_PERMISSION_CODE, permissions)
        }
    }

    internal fun toggleFlashlight() {
        if (!isOpen) {
            CodeUtils.isLightEnable(true)
            iv_flashlight.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_on_black_24dp))
            isOpen = true
        } else {
            CodeUtils.isLightEnable(false)
            iv_flashlight.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_off_black_24dp))
            isOpen = false
        }
    }

    private val analyzeCallback = object : CodeUtils.AnalyzeCallback {

        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {

            val intent: Intent = Intent(applicationContext, ProductSearchActivity::class.java)
            intent.putExtra(ProductDetailFragment.FLAG_BAR_CODE, result)
            startActivity(intent)
        }

        override fun onAnalyzeFailed() {
            Toast.makeText(applicationContext, "Oops, bar code analysis failed!", Toast.LENGTH_SHORT)
            finish()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Activity.RESULT_OK != resultCode) {
            return
        }

        if (requestCode == REQUEST_PERMISSION_CODE && resultCode == PermissionActivity.PERMISSION_DENIED) {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }
}
