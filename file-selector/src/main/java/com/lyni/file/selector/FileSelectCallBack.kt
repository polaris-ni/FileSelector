package com.lyni.file.selector

import android.content.Intent

/**
 * @date 2022/6/1
 * @author Liangyong Ni
 * description FileSelectCallBack
 */
interface FileSelectCallBack {
    fun onResult(data: Intent)
}