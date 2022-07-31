package me.ruyeo.kitobz.ui.audioplayer

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentAudioPlayerBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding


@AndroidEntryPoint
class AudioPlayerFragment : BaseFragment(R.layout.fragment_audio_player) {
    val binding by viewBinding { FragmentAudioPlayerBinding.bind(it) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            Glide.with(requireContext()).load(R.drawable.im_audio_book)
                .apply(bitmapTransform(BlurTransformation(25)))
                .into(imBackground)


        }
    }
}