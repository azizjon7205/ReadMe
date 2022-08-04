package me.ruyeo.kitobz.ui.audioplayer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.PopupAdapter
import me.ruyeo.kitobz.databinding.FragmentAudioPlayerBinding
import me.ruyeo.kitobz.model.Popup
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding


@AndroidEntryPoint
class AudioPlayerFragment : BaseFragment(R.layout.fragment_audio_player) {
    private lateinit var filterPopup: PopupWindow
    private lateinit var popupAdapter: PopupAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private val binding by viewBinding { FragmentAudioPlayerBinding.bind(it) }
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


            /* Click more button */
            icMore.setOnClickListener {
                showPopup()
            }

            /* Click play button */
            icPlay.setOnClickListener {
                playBtnClick()
            }

            /*Click rewind button*/
            icRewindRight.setOnClickListener {
                rewindBtnClick()
            }

            setupAudioPlayer()
        }
    }


    private fun setupAudioPlayer() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.simple)
        mediaPlayer.isLooping = true
        mediaPlayer.seekTo(0)
        mediaPlayer.setVolume(1f, 1f)
        val totalTime = mediaPlayer.duration


        binding.positionBar.max = totalTime
        binding.positionBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    binding.positionBar.setProgress(progress);
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    fun playBtnClick() {
        if (!mediaPlayer.isPlaying()) {
            // Stopping
            mediaPlayer.start()
            Glide.with(binding.icPlay).load(R.drawable.ic_stop).into(binding.icPlay)
        } else {
            // Playing
            mediaPlayer.pause()
            Glide.with(binding.icPlay).load(R.drawable.ic_play).into(binding.icPlay)
        }
    }

    fun rewindBtnClick() {
        mediaPlayer.deselectTrack(300)
    }


    private fun popupMenu(): PopupWindow {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_popup, null)
        val recyclerview = view.findViewById<RecyclerView>(R.id.recycler_view)

        popupAdapter = PopupAdapter()
        recyclerview.apply {

            adapter = popupAdapter
        }
        popupAdapter.submitList(popupItems())

        return PopupWindow(view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun popupItems(): ArrayList<Popup> {
        val items = ArrayList<Popup>()
        items.add(Popup(getString(R.string.str_bookmarks), R.drawable.ic_save))
        items.add(Popup(getString(R.string.str_content), R.drawable.ic_list))
        items.add(Popup(getString(R.string.str_sleep_timer), R.drawable.ic_alarm_clock))
        items.add(Popup(getString(R.string.str_search), R.drawable.ic_search))
        items.add(Popup(getString(R.string.str_speed), R.drawable.ic_speed))

        return items
    }


    private fun showPopup() {
        filterPopup = popupMenu()
        filterPopup.isOutsideTouchable = true
        filterPopup.isFocusable = true
        filterPopup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterPopup.showAsDropDown(binding.icMore)
    }

    private fun dismissPopup() {
        filterPopup?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            filterPopup = null!!
        }

    }


}