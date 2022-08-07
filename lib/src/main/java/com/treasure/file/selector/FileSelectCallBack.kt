package com.treasure.file.selector

import android.net.Uri

/**
 * @date 2022/6/1
 * @author Liangyong Ni
 * description FileSelectCallBack
 */
interface FileSelectCallBack {
    fun onResult(data: Uri)
}