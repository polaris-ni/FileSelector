package com.lyni.file.selector

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lyni.permission.QuickPermission
import com.lyni.permission.core.Permissions

class MainActivity : AppCompatActivity(), FileSelectCallBack {

    companion object {
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvTest).setOnClickListener {
            QuickPermission.with(this).addPermissions(Permissions.READ_EXTERNAL_STORAGE).onGranted {
                FileSelectDialog.show(supportFragmentManager, isShowHideDir = true)
            }.onDenied {
                Log.e(TAG, "onCreate: $it")
            }.request()

        }
    }

    override fun onResult(data: Uri) {
        Log.e(TAG, "onResult: $data")
    }
}