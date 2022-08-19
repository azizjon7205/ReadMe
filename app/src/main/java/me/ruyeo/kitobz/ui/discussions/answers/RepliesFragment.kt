package me.ruyeo.kitobz.ui.discussions.answers

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAnswerRepliesAdapter
import me.ruyeo.kitobz.databinding.FragmentRepliesBinding
import me.ruyeo.kitobz.managers.PrefsManager
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Reply
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.extensions.visible
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class RepliesFragment : BaseFragment(R.layout.fragment_replies) {

    @Inject
    lateinit var prefsManager: PrefsManager

    private val binding by viewBinding { FragmentRepliesBinding.bind(it) }
    private val repliesAdapter by lazy { DiscussAnswerRepliesAdapter() }

    private lateinit var answer: Answer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        answer = Gson().fromJson(arguments?.getString("answer"), Answer::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        repliesAdapter.submitList(loadReplies())

        with(binding) {
            fmProgress.visible(false)
            llCloseAnswers.setOnClickListener {
                findNavController().navigateUp()
            }
            Glide.with(root).load(answer.owner_image).error(R.drawable.ic_account)
                .into(ivAnswerOwnerImage)
            tvAnswerOwner.text = answer.owner_name
            tvAnswerDate.text = answer.date
            tvAnswerMessage.text = answer.message

            rvReplies.adapter = repliesAdapter

            bWriteAnswer.setOnClickListener {
                bWriteAnswer.visible(false)
                bPublishAnswer.visible(true)
                llAnswer.visible(true)
                showKeyboard(etAnswer)
            }

            llUserAnswer.visible(prefsManager.getUser() == null)
            tvNoteRegister.visible(prefsManager.getUser() != null)
        }
    }

    private fun loadReplies(): List<Reply> {
        return arrayListOf(
            Reply(
                uid = "1",
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                message = "Очень интересная книга, всем советую её прочесть, особенно тем кого часто одолевает грусть и печаль."
            )

        )
    }
}