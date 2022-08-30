package me.ruyeo.kitobz.ui.pdfview

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentPdfViewBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

class PdfViewFragment : BaseFragment(R.layout.fragment_pdf_view) {
    private val binding by viewBinding { FragmentPdfViewBinding.bind(it) }

    private val TAG = PdfViewFragment::class.simpleName

    private var currentPage: MutableLiveData<String> = MutableLiveData()
    private var allPages = 0

    var isSaved = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        setupActivityListener()
    }

    private fun setupUI() {
        binding.apply {
            pdfView
                .fromAsset("would.pdf")
                .defaultPage(0)
                .onRender { nbPages, _, _ ->
                    Log.d(TAG, "setupUI: ")
                    seekBar.max = nbPages - 1
                    tvAllPages.text = (nbPages - 1).toString()
                    allPages = nbPages
                }
                .onPageChange { page, _ ->
                    seekBar.progress = page
                    currentPage.value = page.toString()
                }
                .enableSwipe(true)
                .load()

            setSeekBar()
            setTvs()
        }

        binding.ivBack.setOnClickListener { }

        binding.ivSave.setOnClickListener {
            binding.ivSaved.setOnClickListener {
                setUpUnSaved()
            }
            binding.ivSave.setOnClickListener {
                setUpSaved()
            }
            binding.ivResize.setOnClickListener {
                openResizeSettings()
            }
            binding.ivMore.setOnClickListener {
                openSettings()
            }
        }
    }

    private fun openSettings() {
        val popupMenu = PopupMenu(requireContext(), binding.ivMore)
        popupMenu.menuInflater.inflate(R.menu.popup_menu_pdf, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuContent -> {
                    showToast("content")
                }
                R.id.menuBookmarks -> {

                }
                R.id.menuFind -> {

                }
            }

            false
        }

        binding.ivMore.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun openResizeSettings() {
        val bottomSheet = BottomSheetDialog()
        bottomSheet.show(
            childFragmentManager,
            "ModalBottomSheet"
        )
    }

    private fun setTvs() {
        binding.apply {
            currentPage.value = pdfView.currentPage.toString()
            currentPage.observe(viewLifecycleOwner) {
                // back touchable
                if (it.toInt() == 0) {
                    tvBack.setTextColor(resources.getColor(R.color.black))
                    tvBack.isEnabled = false
                } else {
                    tvBack.setTextColor(resources.getColor(R.color.blue_light))
                    tvBack.isEnabled = true
                }
                // forward touchable
                if (it.toInt() == allPages - 1) {
                    tvForward.setTextColor(resources.getColor(R.color.black))
                    tvForward.isEnabled = false
                } else {
                    tvForward.setTextColor(resources.getColor(R.color.blue_light))
                    tvForward.isEnabled = true
                }
            }

            tvBack.setOnClickListener {
                backPressed()
            }
            tvForward.setOnClickListener {
                forwardPressed()
            }
        }
    }

    private fun setSeekBar() {
        binding.apply {
            tvCurrentPageNumber.text = currentPage.value

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvCurrentPageNumber.text = progress.toString()
                    pdfView.jumpTo(seekBar!!.progress)
                    setTvs()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }

    private fun forwardPressed() {
        binding.apply {
            var curP = currentPage.value!!.toInt()
            currentPage.postValue((++curP).toString())
            tvCurrentPageNumber.text = curP.toString()
            pdfView.jumpTo(curP)
            seekBar.progress = curP
        }
    }

    private fun backPressed() {
        binding.apply {
            var curP = currentPage.value!!.toInt()
            currentPage.postValue((--curP).toString())
            tvCurrentPageNumber.text = curP.toString()
            pdfView.jumpTo(curP)
            seekBar.progress = curP
        }
    }

    private fun setUpSaved() {
        binding.apply {
            ivSave.setOnClickListener {
                ivSave.visibility = View.GONE
                ivSaved.visibility = View.VISIBLE
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpUnSaved() {
        binding.apply {
            ivSaved.setOnClickListener {
                ivSaved.visibility = View.GONE
                ivSave.visibility = View.VISIBLE
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupActivityListener() {
        activity?.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })

        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
    }

    override fun onResume() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onResume()
    }

    override fun onPause() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
        super.onPause()
    }
}