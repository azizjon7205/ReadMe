package me.ruyeo.kitobz.ui.discussions.create

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentCreateDiscussionBinding
import me.ruyeo.kitobz.managers.PrefsManager
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.utils.extensions.tint
import me.ruyeo.kitobz.utils.extensions.visible
import me.ruyeo.kitobz.utils.spannable.MyClickableSpans
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class CreateDiscussionFragment : BaseFragment(R.layout.fragment_create_discussion) {

    @Inject
    lateinit var prefsManager: PrefsManager

    private val binding by viewBinding { FragmentCreateDiscussionBinding.bind(it) }
    private var allPhotos: ArrayList<Uri> = arrayListOf()

    private var hasPhoto = false
    private var hasTopic = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            llBack.setOnClickListener {
                findNavController().navigateUp()
            }

            checkForPublish()

            clDiscussPhoto.setOnClickListener {
                pickPhotoWithFishBun()
            }

            etDiscussHeader.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    hasTopic = if (s?.length != 0 && s?.length!! <= 70) {
                        ivEnterTopicName.tint(R.color.green)
                        ivEnterTopicName.setImageResource(R.drawable.ic_check_circle)
                        tvEnterTopicName.setTextColor(Color.parseColor("#14CB00"))
                        true

                    } else{
                        ivEnterTopicName.setImageResource(R.drawable.ic_clear)
                        ivEnterTopicName.tint(R.color.red)
                        tvEnterTopicName.setTextColor(Color.RED)
                        false
                    }
                    checkForPublish()

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            tvNoteRegister.visible(prefsManager.getUser() == null)
            if (prefsManager.getUser() != null){
                tvDiscussOwner.visible(true)
                tvPublish.visible(true)
                tvNoteRegister.visible(false)
            } else{
                tvDiscussOwner.visible(false)
                tvPublish.visible(false)
                tvNoteRegister.visible(true)
            }
            tvNoteRegister.text = MyClickableSpans.clickableSpan(
                requireContext().getString(R.string.str_to_leave_reply_login),
                "авторизуйтесь"){
                Toast.makeText(requireContext(), "Log in to answer", Toast.LENGTH_SHORT).show()
            }
            tvNoteRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun checkForPublish(){
        val tvPublish = binding.tvPublish
        tvPublish.isActivated = !(hasPhoto && hasTopic)

        if (tvPublish.isActivated){
            tvPublish.setTextColor(Color.BLACK)
        } else{
            tvPublish.setTextColor(Color.WHITE)
        }
    }

    /**
     * Pick photo using FishBun library
     */
    private fun pickPhotoWithFishBun() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setCamera(true)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()

                with(binding) {
                    if (allPhotos.size != 0) {
                        ivAddPhoto.tint(R.color.green)
                        ivAddPhoto.setImageResource(R.drawable.ic_check_circle)
                        tvAddPhoto.setTextColor(Color.parseColor("#14CB00"))

                        ivGallery.visible(false)
                        fabCreateDiscuss.visible(false)
                        Glide.with(root)
                            .load(allPhotos[0])
                            .placeholder(R.drawable.preload_progress)
                            .into(ivDiscussAvatar)

                        hasPhoto = true
                    } else{
                        ivAddPhoto.setImageResource(R.drawable.ic_clear)
                        ivAddPhoto.tint(R.color.red)
                        tvAddPhoto.setTextColor(Color.RED)
                        hasPhoto = false
                    }
                }
                checkForPublish()
            }
        }
}