package com.treasure.file.selector

import androidx.annotation.DrawableRes
import androidx.annotation.IntRange

/**
 * @date 2022/5/26
 * @author Liangyong Ni
 * description FileType
 */
enum class FileType(@IntRange(from = 1000000) val value: Int, val desc: String, @DrawableRes val resId: Int) {
    FOLDER(1000000, "文件夹", R.drawable.ic_folder),
    CONFIG(1000001, "配置文件", R.drawable.ic_file_config),
    DATABASE(1000002, "数据库", R.drawable.ic_file_db),
    EXCEL(1000004, "表格", R.drawable.ic_file_excel),
    PROGRAM(1000005, "代码/可执行程序", R.drawable.ic_file_program),
    GIF(1000006, "GIF动图", R.drawable.ic_file_gif),
    IMAGE(1000007, "图片", R.drawable.ic_file_img),
    LINK(1000008, "链接/网页", R.drawable.ic_file_link),
    MUSIC(1000009, "音频", R.drawable.ic_file_music),
    PDF(1000010, "PDF文件", R.drawable.ic_file_pdf),
    PPT(1000011, "幻灯片", R.drawable.ic_file_ppt),
    TEXT(1000012, "纯文本文件", R.drawable.ic_file_txt),
    VIDEO(1000013, "视频", R.drawable.ic_file_video),
    WORD(1000014, "文档", R.drawable.ic_file_word),
    ZIP(1000015, "压缩包/镜像", R.drawable.ic_file_zip),
    FONT(1000016, "字体文件", R.drawable.ic_file_font),
    UNKNOWN(1000032, "未知文件", R.drawable.ic_file_unknown);
}

