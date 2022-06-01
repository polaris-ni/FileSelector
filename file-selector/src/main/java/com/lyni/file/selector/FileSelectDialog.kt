package com.lyni.file.selector

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lyni.file.selector.databinding.DialogFileSelectBinding
import java.io.File

/**
 * @date 2022/5/26
 * @author Liangyong Ni
 * description FileSelectDialog
 */
class FileSelectDialog : DialogFragment(), FileAdapter.CallBack {

    companion object {
        fun show(
            manager: FragmentManager,
            title: String? = null,
            initPath: String? = null,
            isShowHideDir: Boolean = false
        ) {
            FileSelectDialog().apply {
                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putBoolean("isShowHideDir", isShowHideDir)
                bundle.putString("initPath", initPath)
                arguments = bundle
            }.show(manager)
        }
    }

    private lateinit var binding: DialogFileSelectBinding
    private val seed = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStyle(STYLE_NORMAL, android.R.style.ThemeOverlay_Material_Dialog)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return@setOnKeyListener false
                }
                false
            }
            setCanceledOnTouchOutside(true)
            setCancelable(true)
            this@FileSelectDialog.isCancelable = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFileSelectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        val params = dialog?.window?.attributes
        params?.let {
            it.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.height = ViewGroup.LayoutParams.MATCH_PARENT
            it.gravity = Gravity.CENTER
        }
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            decorView.setPadding(dp2pxF(32.0f), dp2pxF(64.0f), dp2pxF(32.0f), dp2pxF(64.0f))
            attributes = params as WindowManager.LayoutParams
        }
        super.onStart()
    }

    //重写show方法，防止重复添加报错
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun show(manager: FragmentManager) = show(manager, this.javaClass.simpleName + seed)
    private var isShowHideDir: Boolean = false
    private var title: String? = null
    private var initPath = FileUtils.getSdCardPath()
    private lateinit var fileAdapter: FileAdapter
    private lateinit var pathAdapter: PathAdapter

    private fun initView() {
        arguments?.let {
            title = it.getString("title")
            isShowHideDir = it.getBoolean("isShowHideDir")
            it.getString("initPath")?.let { path -> initPath = path }
        }
        initContentView()
        refreshCurrentDirPath(initPath)
    }

    private fun initContentView() {
        fileAdapter = FileAdapter(requireContext(), this)
        pathAdapter = PathAdapter { refreshCurrentDirPath(pathAdapter.getPath(it)) }
        binding.rvFile.adapter = fileAdapter
        binding.rvPath.adapter = pathAdapter
    }

    override fun onFileClick(position: Int) {
        val fileItem = fileAdapter.getItem(position)
        if (fileItem?.isDirectory == true) {
            refreshCurrentDirPath(fileItem.path)
        } else {
            fileItem?.path?.let { path ->
                setData(path)
                dismissAllowingStateLoss()
            }
        }
    }

    override fun isShowHideDir() = isShowHideDir

    private fun refreshCurrentDirPath(currentPath: String) {
        pathAdapter.updatePath(currentPath)
        fileAdapter.loadData(currentPath)
    }

    private fun setData(path: String) {
        val data = Intent().setData(Uri.fromFile(File(path)))
        (parentFragment as? FileSelectCallBack)?.onResult(data)
        (activity as? FileSelectCallBack)?.onResult(data)
    }

    private fun dp2pxF(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}