package me.ruyeo.kitobz.ui.audioplayer

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint

import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Runnable
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.PopupAdapter
import me.ruyeo.kitobz.databinding.FragmentAudioPlayerBinding
import me.ruyeo.kitobz.model.Popup
import me.ruyeo.kitobz.ui.BaseFragment
import okhttp3.internal.wait
import viewBinding
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class AudioPlayerFragment : BaseFragment(R.layout.fragment_audio_player), java.lang.Runnable {
    private lateinit var filterPopup: PopupWindow
    private lateinit var popupAdapter: PopupAdapter
    private lateinit var audioListBottomSheet: AudioListBottomSheet

    private lateinit var mediaPlayer: MediaPlayer
    private var startTime: Double = 0.0
    private var finalTime: Double = 0.0

    private var handler = Handler()
    private val forwardTime = 30000
    private val backwardTime = 30000

    private var speedOn: Boolean = false

    private var oneTimeOnly = 0

    private val binding by viewBinding { FragmentAudioPlayerBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            Glide.with(requireContext()).load(R.drawable.im_audio_book)
                .apply(bitmapTransform(BlurTransformation(25)))
                .into(imBackground)

            audioListBottomSheet = AudioListBottomSheet()


            /*Close button */
            icClose.setOnClickListener {
                findNavController().popBackStack()
            }

            /*Bookmark button */
            icBookmark.setOnClickListener {
                showToast("Bookmark")
            }

            /* Click more button */
            icMore.setOnClickListener {
                showPopup()
            }

        }
        setupAudioPlayer()
    }

    private fun setupAudioPlayer() {
        binding.apply {


            /* Click play button */
            icPlay.setOnClickListener {
                playBtnClick()
            }

            /*Click rewind button*/
            icRewindRight.setOnClickListener {
                rightRewindBtnClick()
            }

            icRewindLeft.setOnClickListener {
                leftRewindBtnClick()
            }


            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.simple)
            mediaPlayer.isLooping = true
            mediaPlayer.seekTo(0)
            mediaPlayer.setVolume(1f, 1f)
            val totalTime = mediaPlayer.duration


            finalTime = mediaPlayer.duration.toDouble()
            startTime = mediaPlayer.currentPosition.toDouble()

            seekbar.max = totalTime
            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        binding.seekbar.setProgress(progress);
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

            /* Set in TextView start and final Time */
            tvFinal.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())))
            )

            tvStart.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())))
            )
        }
    }

    fun playBtnClick() {
        if (!mediaPlayer.isPlaying()) {
            // Stopping
            mediaPlayer.start()
            Glide.with(binding.icPlay).load(R.drawable.ic_stop).into(binding.icPlay)

            if (oneTimeOnly == 0) {
                binding.seekbar.max = finalTime.toInt()
                oneTimeOnly = 1
            }


            binding.seekbar.setProgress(startTime.toInt())
            handler.postDelayed(UpdateSongTime, 100)

        } else {
            // Playing
            mediaPlayer.pause()
            Glide.with(binding.icPlay).load(R.drawable.ic_play).into(binding.icPlay)
        }
    }

    fun rightRewindBtnClick() {
        var temp = startTime.toInt()

        if ((temp + forwardTime) <= finalTime) {
            startTime = startTime + forwardTime
            mediaPlayer.seekTo(startTime.toInt())
        }
    }

    fun leftRewindBtnClick() {
        var temp = startTime.toInt()

        if ((temp - backwardTime) > 0) {
            startTime = startTime - backwardTime
            mediaPlayer.seekTo(startTime.toInt())
        }
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


        popupAdapter.onClick = {
            when (it.id) {
                1 -> showToast(it.text)
                2 -> showContent()
                3 -> showTimerDialog()
                4 -> showToast(it.text)
                5 -> setSpeed(it)
            }
        }

        return PopupWindow(view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun popupItems(): ArrayList<Popup> {
        val items = ArrayList<Popup>()
        items.add(Popup(1, getString(R.string.str_bookmarks), R.drawable.ic_save))
        items.add(Popup(2, getString(R.string.str_content), R.drawable.ic_list))
        items.add(Popup(3, getString(R.string.str_sleep_timer), R.drawable.ic_alarm_clock))
        items.add(Popup(4, getString(R.string.str_search), R.drawable.ic_search))
        items.add(Popup(5, getString(R.string.str_speed), R.drawable.ic_speed))

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
        }

    }


    private var UpdateSongTime = Runnable() {
        run()
    }

    override fun run() {
        startTime = mediaPlayer.currentPosition.toDouble()
        binding.tvStart.setText(String.format("%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())))
        )
        binding.seekbar.setProgress(startTime.toInt())
        handler.postDelayed(this@AudioPlayerFragment, 100)
    }


    private fun showTimerDialog() {
        dismissPopup()

        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_timer)


        dialog.show()
    }

    private fun setSpeed(popup: Popup) {
        dismissPopup()

        if (mediaPlayer.isPlaying) {
            if (speedOn) {
                mediaPlayer.setPlaybackParams(mediaPlayer.playbackParams.setSpeed(1.5f))
                speedOn = false
            } else {
                mediaPlayer.setPlaybackParams(mediaPlayer.playbackParams.setSpeed(1.0f))
                speedOn = true
            }
        }
    }

    private fun showContent() {
        dismissPopup()
        audioListBottomSheet.show(childFragmentManager, "AudioBottomSheet")
    }

}