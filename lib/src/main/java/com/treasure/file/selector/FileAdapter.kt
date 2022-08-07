package com.treasure.file.selector


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class FileAdapter(private val context: Context, private val callBack: CallBack) :
    RecyclerView.Adapter<FileAdapter.ItemViewHolder>() {
    private var rootPath: String? = null
    private var currentPath: String? = null
    private val data: MutableList<FileItem> = mutableListOf()

    fun loadData(path: String) {
        val data = ArrayList<FileItem>()
        if (rootPath == null) {
            rootPath = path
        }
        currentPath = path
        //添加 返回主目录
        val fileRoot = FileItem(DIR_ROOT, rootPath ?: path, 0, true, FileType.FOLDER)
        data.add(fileRoot)
        if (path != PathAdapter.sdCardDirectory) {
            //添加 返回上一级目录
            val fileParent = FileItem(DIR_PARENT, File(path).parent ?: "", 0, true, FileType.FOLDER)
            data.add(fileParent)
        }
        currentPath?.let { currentPath ->
            val files: Array<File> = FileUtils.listDirsAndFiles(currentPath)
            for (file in files) {
                if (!callBack.isShowHideDir() && file.name.startsWith(".")) {
                    continue
                }
                val fileItem = FileItem()
                val isDirectory = file.isDirectory
                fileItem.isDirectory = isDirectory
                if (isDirectory) {
                    fileItem.size = 0
                    fileItem.type = FileType.FOLDER
                } else {
                    fileItem.size = file.length()
                }
                fileItem.name = file.name
                fileItem.path = file.absolutePath
                if (fileItem.type != FileType.FOLDER) {
                    fileItem.type = FileTypeDetector.getFiletype(fileItem.name)
                }
                data.add(fileItem)
            }
            setItems(data)
        }
    }

    interface CallBack {
        fun onFileClick(position: Int)

        fun isShowHideDir(): Boolean
    }

    companion object {
        const val DIR_ROOT = "主目录"
        const val DIR_PARENT = "返回上一级"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.tvItem.apply {
            text = item.name
            setCompoundDrawables(getDrawable(item.type.resId), null, null, null)
        }
        holder.itemView.setOnClickListener {
            callBack.onFileClick(holder.layoutPosition)
        }
    }

    override fun getItemCount(): Int = data.size

    private fun getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)?.apply {
        setBounds(0, 0, minimumWidth, minimumHeight)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setItems(items: List<FileItem>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): FileItem? = data.getOrNull(position)

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView

        init {
            tvItem = view.findViewById(R.id.tvItem)
        }
    }
}


