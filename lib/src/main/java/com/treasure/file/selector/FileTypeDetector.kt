package com.treasure.file.selector


/**
 * @date 2022/5/26
 * @author Liangyong Ni
 * description FileTypeDetector
 */
object FileTypeDetector {

    val CONFIG = setOf("xml", "cfg", "json", "yml", "conf", "ini", "properties")
    val DATABASE = setOf("sql", "db", "mdf", "mdb", "dbf", "wdb", "myd")
    val EXCEL = setOf("xls", "xlsx")
    val PROGRAM = setOf("exe", "apk", "sh", "bat", "cmd", "dll", "js", "kt", "java", "py", "py3", "php", "c", "cpp", "h")
    val GIF = setOf("gif")
    val IMAGE = setOf("jpg", "jpeg", "ico", "svg", "jfif", "webp", "png", "bmp", "heif")
    val LINK = setOf("html", "htm", "mhtml", "asp")
    val MUSIC = setOf("wmv", "wma", "asf", "mp3", "wav", "m4a", "flac", "aac", "mid", "ape")
    val PDF = setOf("pdf")
    val PPT = setOf("ppt", "pptx")
    val TEXT = setOf("txt", "log")
    val VIDEO = setOf("mp4", "m4v", "mov", "qt", "flv", "mpeg", "mpg", "vob", "mkv", "rm", "rmvb", "ts", "dat", "avi")
    val WORD = setOf("doc", "docx")
    val ZIP = setOf("zip", "iso", "rar", "7z", "tar", "gz")
    val FONT = setOf("ttf", "otf")

    val FORMATS = hashMapOf(
        FileType.CONFIG to CONFIG,
        FileType.DATABASE to DATABASE,
        FileType.EXCEL to EXCEL,
        FileType.PROGRAM to PROGRAM,
        FileType.GIF to GIF,
        FileType.IMAGE to IMAGE,
        FileType.LINK to LINK,
        FileType.MUSIC to MUSIC,
        FileType.PDF to PDF,
        FileType.PPT to PPT,
        FileType.TEXT to TEXT,
        FileType.VIDEO to VIDEO,
        FileType.WORD to WORD,
        FileType.ZIP to ZIP,
        FileType.FONT to FONT
    )

    /**
     * 获取文件类型
     * 根据文件拓展名获取，可能不准确
     * @param filename 文件名
     * @return [FileType] 文件类型
     */
    fun getFiletype(filename: String?): FileType {
        if (filename == null) {
            return FileType.UNKNOWN
        }
        val pieces = filename.split(".")
        if (pieces.size > 1) {
            val suffix = pieces.last()
            FORMATS.entries.forEach {
                if (suffix in it.value) {
                    return it.key
                }
            }
        }
        return FileType.UNKNOWN
    }

}