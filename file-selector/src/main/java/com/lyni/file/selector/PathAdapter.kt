package com.lyni.file.selector

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.lyni.file.selector.databinding.ItemPathFilepickerBinding
import java.util.*


class PathAdapter(private val onPathClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<PathAdapter.ItemViewHolder<ItemPathFilepickerBinding>>() {

    companion object {
        private const val ROOT_HINT = "内部储存"
        val sdCardDirectory: String = Environment.getExternalStorageDirectory().absolutePath
    }

    private val paths = LinkedList<String>()

    fun getPath(position: Int): String {
        val tmp = StringBuilder("$sdCardDirectory/")
        //忽略根目录
        if (position == 0) {
            return tmp.toString()
        }
        for (i in 1..position) {
            tmp.append(paths[i]).append("/")
        }
        return tmp.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePath(path: String) {
        var path1 = path
        path1 = path1.replace(sdCardDirectory, "")
        Log.e("TAG", "updatePath: $path1")
        paths.clear()
        paths.add(ROOT_HINT)
        if (path1 != "/" && path1 != "") {
            val subDirs = path1.substring(path1.indexOf("/") + 1)
                .split("/")
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            paths.addAll(subDirs)
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<ItemPathFilepickerBinding> {
        return ItemViewHolder(ItemPathFilepickerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder<ItemPathFilepickerBinding>, position: Int) {
        holder.binding.tvItem.apply {
            text = paths[position]
        }
        holder.itemView.setOnClickListener {
            onPathClicked.invoke(holder.layoutPosition)
        }
    }

    override fun getItemCount(): Int = paths.size
}
