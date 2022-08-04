package me.ruyeo.kitobz.ui.library

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.MyLibraryVPAdapter
import me.ruyeo.kitobz.databinding.FragmentMyLibraryBinding
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.library.audio.MyLAudioFragment
import me.ruyeo.kitobz.ui.library.electron.MyLElectronFragment
import viewBinding

@AndroidEntryPoint
class MyLibraryFragment : BaseFragment(R.layout.fragment_my_library) {
    private val binding by viewBinding{FragmentMyLibraryBinding.bind(it)}
    private val vpAdapter by lazy { MyLibraryVPAdapter(childFragmentManager) }
    private var bookType: String = "audio"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        if (vpAdapter.fragments.isEmpty()){
            vpAdapter.addFragmentAndTitle(MyLAudioFragment(), getString(R.string.str_audio_books))
            vpAdapter.addFragmentAndTitle(MyLElectronFragment(), getString(R.string.str_electronic_books))
        }

        binding.viewPager.adapter = vpAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                bookType = when(position){
                    0 -> "audio"
                    else -> "electronic"
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.myLSearchFragment, bundleOf("bookType" to bookType))
        }
    }

}