package com.lyni.file.selector

/**
 * @date 2022/5/26
 * @author Liangyong Ni
 * description FileItem
 */
data class FileItem(
    var name: String? = null,
    var path: String = "/",
    var size: Long = 0,
    var isDirectory: Boolean = false,
    var type: FileType
) {
    constructor() : this(null, "/", 0, false, FileType.UNKNOWN)
}