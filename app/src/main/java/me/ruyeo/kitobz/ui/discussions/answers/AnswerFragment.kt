package me.ruyeo.kitobz.ui.discussions.answers

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAnswersAdapter
import me.ruyeo.kitobz.databinding.FragmentAnswerBinding
import me.ruyeo.kitobz.managers.PrefsManager
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.utils.extensions.visible
import me.ruyeo.kitobz.utils.spannable.MyClickableSpans
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class AnswerFragment : BaseFragment(R.layout.fragment_answer) {

    @Inject
    lateinit var prefsManager: PrefsManager

    private val binding by viewBinding { FragmentAnswerBinding.bind(it) }
    private val answersAdapter by lazy { DiscussAnswersAdapter() }

    private lateinit var discuss: Discuss

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        discuss = Gson().fromJson(arguments?.getString("discuss"), Discuss::class.java)
        Log.d("@@@", "Discuss -> ${discuss.toString()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        answersAdapter.submitList(loadAnswers())
        answersAdapter.onClick = {
            findNavController().navigate(R.id.repliesFragment, bundleOf("answer" to Gson().toJson(it)))
        }
        with(binding){

            rvAnswers.adapter = answersAdapter

            bWriteAnswer.setOnClickListener {
                bWriteAnswer.visible(false)
                bPublishAnswer.visible(true)
                llAnswer.visible(true)
                showKeyboard(etAnswer)
            }

            if (prefsManager.getUser() != null) {
                llUserAnswer.visible(true)
                tvNoteRegister.visible(false)

                if (discuss.messages_count == 0) {
                    flBeFirst.visible(true)

                } else {
                    flBeFirst.visible(false)
                }
            } else{
                if (discuss.messages_count == 0) {
                    tvNoteRegister.visible(false)

                } else {
                    tvNoteRegister.visible(true)
                }
                llAnswer.visible(false)
            }

            tvNoteRegister.text = MyClickableSpans.clickableSpan(
                requireContext().getString(R.string.str_to_leave_reply_login),
                "авторизуйтесь"){
                Toast.makeText(requireContext(), "Log in to answer", Toast.LENGTH_SHORT).show()
            }
            tvNoteRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun loadAnswers(): List<Answer>{
        return arrayListOf(
            Answer(
                uid = "1",
                owner_name = "Андрей Рыбаков",
                date = "07.03.2020",
                replies_count = 1,
                message = "Очень интересная книга, всем советую её прочесть, особенно тем кого часто одолевает грусть и печаль. Авто чётко раскрывает тему и указывает путь к счастью."
            ),
            Answer(
                uid = "2",
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                replies_count = 0,
                message = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            ),
            Answer(
                uid = "2",
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                replies_count = 0,
                message = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            ),
            Answer(
                uid = "2",
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                replies_count = 0,
                message = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )

        )
    }


}