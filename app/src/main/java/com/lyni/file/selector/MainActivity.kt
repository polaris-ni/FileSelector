package com.lyni.file.selector

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lyni.permission.QuickPermission

class MainActivity : AppCompatActivity(), FileSelectCallBack {

    companion object {
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvTest).setOnClickListener {
            QuickPermission.with(this).onGranted {
                FileSelectDialog.show(supportFragmentManager, isShowHideDir = true)
            }.onDenied {
                Log.e(TAG, "onCreate: $it")
            }.request()

        }
    }

    override fun onResult(data: Intent) {
        Log.e(TAG, "onResult: ${data.data}")
    }
}