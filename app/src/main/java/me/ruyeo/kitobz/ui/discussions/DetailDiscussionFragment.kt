package me.ruyeo.kitobz.ui.discussions

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAnswersAdapter
import me.ruyeo.kitobz.databinding.FragmentDetailDiscussionBinding
import me.ruyeo.kitobz.managers.PrefsManager
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.model.DiscussDetails
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.utils.extensions.visible
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class DetailDiscussionFragment : BaseFragment(R.layout.fragment_detail_discussion) {
    private val binding by viewBinding { FragmentDetailDiscussionBinding.bind(it) }
    private val answersAdapter by lazy { DiscussAnswersAdapter() }

    @Inject
    lateinit var prefsManager: PrefsManager

    private var discuss: Discuss? = null
    private var discussDetails: DiscussDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        discuss = Gson().fromJson(arguments?.getString("discuss"), Discuss::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        discussDetails = DiscussDetails(
            header = "Где убили Пушкина? Когда и кем был убит Александр Пушкин",
            owner_name = "Алина Бакальчук",
            answers_count = 2,
            messages_count = 3,
            created_date = "16.02.2015",
            discuss_theme = "Дуэль между Александром Сергеевичем Пушкиным и Жоржем де Геккерном (Дантесом) состоялась 27 января (8 февраля) 1837 года на окраине Санкт-Петербурга, в районе Чёрной речки близ Комендантской дачи. Дуэлянты стрелялись на пистолетах. В результате дуэли Пушкин был смертельно ранен и через два дня умер.",
            answers = loadAnswers()
        )

        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            Glide.with(root).load(discuss?.avatar)
                .error(R.drawable.ic_launcher_background)
                .into(ivDiscussAvatar)
            tvDiscussHeader.text = discuss?.header
            tvDiscussOwner.text = discuss?.owner_name
            tvDiscussDate.text = discuss?.created_date
//            tvDiscussTheme.text = discussDetails?.discuss_theme

            val messageToAuth = root.context.getString(R.string.str_to_leave_reply_login)
            val clickableMessage = "авторизуйтесь."
            val spannable = SpannableString(messageToAuth)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(requireContext(), "Log in to answer", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = Color.BLACK
                }
            }
//            val foregroundColorSpan = ForegroundColorSpan(Color.BLUE)  //That is for change link color

            spannable.setSpan(
                clickableSpan,
                messageToAuth.length - clickableMessage.length,
                messageToAuth.length - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            tvNoteRegisterMain.text = spannable
            tvNoteRegisterMain.movementMethod = LinkMovementMethod.getInstance()

            tvNoteRegister.text = spannable
            tvNoteRegister.movementMethod = LinkMovementMethod.getInstance()

            setupUI(discussDetails!!)
        }
    }

    private fun setupUI(discussDetails: DiscussDetails) {
        with(binding) {
            fmProgress.visible(false)
            tvDiscussTheme.text = discussDetails.discuss_theme

            answersAdapter.submitList(discussDetails.answers)
            answersAdapter.onClick = {

            }
            rvAnswers.adapter = answersAdapter

            if (prefsManager.getUser() != null) {
                tvFollow.visible(true)
                llAnswersTotalCount.visible(true)
                llUserAnswer.visible(true)
                tvNoteRegisterMain.visible(false)
                tvNoteRegister.visible(false)

                if (discussDetails.answers_count == 0) {
                    flBeFirst.visible(true)

                } else {
                    rvAnswers.visible(true)
                }
            } else{

                if (discussDetails.answers_count == 0) {
                    llAnswersTotalCount.visible(false)
                    tvNoteRegisterMain.visible(true)
                    tvNoteRegister.visible(false)
                } else {
                    llAnswersTotalCount.visible(true)
                    tvNoteRegisterMain.visible(false)
                    tvNoteRegister.visible(true)
                    rvAnswers.visible(true)
                }
            }

            tvAnswersTotalCount.text = discussDetails.answers_count.toString()
            tvMessagesTotalCount.text = discussDetails.messages_count.toString()

        }
    }

    private fun loadAnswers(): List<Answer>{
        return arrayListOf(
            Answer(
                owner_name = "Андрей Рыбаков",
                date = "07.03.2020",
                messages_count = 1,
                message = "Очень интересная книга, всем советую её прочесть, особенно тем кого часто одолевает грусть и печаль. Авто чётко раскрывает тему и указывает путь к счастью."
            ),
            Answer(
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                messages_count = 0,
                message = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )

        )
    }

}