package me.ruyeo.kitobz.ui.discussions

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAnswerRepliesAdapter
import me.ruyeo.kitobz.adapter.DiscussAnswersAdapter
import me.ruyeo.kitobz.databinding.FragmentDetailDiscussionBinding
import me.ruyeo.kitobz.managers.PrefsManager
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.model.DiscussDetails
import me.ruyeo.kitobz.model.Reply
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.extensions.scrollToBottomWithoutFocusChange
import me.ruyeo.kitobz.utils.extensions.visible
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class DetailDiscussionFragment : BaseFragment(R.layout.fragment_detail_discussion) {
    private val binding by viewBinding { FragmentDetailDiscussionBinding.bind(it) }

    @Inject
    lateinit var prefsManager: PrefsManager

    private var discuss: Discuss? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        discuss = Gson().fromJson(arguments?.getString("discuss"), Discuss::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupKeyboardListener(binding.nestedScroll)

        setupUI()
        initViews()
    }


    private fun initViews() {

        with(binding) {

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

        }
    }

    private fun setupUI() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fcv_answers) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.answer_nav_graph)
        navGraph.setStartDestination(R.id.answerFragment)
        navController.setGraph(navGraph, bundleOf("discuss" to Gson().toJson(discuss)))

        with(binding) {

            llBack.setOnClickListener {
                findNavController().navigateUp()
            }

            Glide.with(root).load(discuss?.avatar)
                .error(R.drawable.im_audio_book)
                .into(ivDiscussAvatar)
            tvDiscussHeader.text = discuss?.header
            tvDiscussOwner.text = discuss?.owner_name
            tvDiscussDate.text = discuss?.created_date
            tvDiscussTheme.text = discuss?.discuss_theme ?: ""

            tvFollow.setOnClickListener {
                tvFollow.isActivated = !tvFollow.isActivated
                checkFollow()
            }

            if (prefsManager.getUser() == null) {
                tvFollow.visible(true)
                llAnswersTotalCount.visible(true)
                tvNoteRegisterMain.visible(false)
                checkFollow()

            } else{
                tvFollow.visible(false)
                if (discuss?.messages_count == 0) {
                    llAnswersTotalCount.visible(false)
                    tvNoteRegisterMain.visible(true)
                } else {
                    llAnswersTotalCount.visible(true)
                    tvNoteRegisterMain.visible(false)
                }
            }

            tvAnswersTotalCount.text = discuss?.answers_count.toString()
            tvMessagesTotalCount.text = discuss?.messages_count.toString()

        }
    }

    private fun checkFollow(){
        val tvFollow = binding.tvFollow

        if (tvFollow.isActivated){
            tvFollow.text = binding.root.context.getString(R.string.str_you_followed)
            tvFollow.setTextColor(Color.BLACK)
        } else{
            tvFollow.text = binding.root.context.getString(R.string.str_follow)
            tvFollow.setTextColor(Color.WHITE)
        }
    }

    private fun loadAnswers(): List<Answer>{
        return arrayListOf(
            Answer(
                owner_name = "Андрей Рыбаков",
                date = "07.03.2020",
                replies_count = 1,
                replies = loadReplies(),
                message = "Очень интересная книга, всем советую её прочесть, особенно тем кого часто одолевает грусть и печаль. Авто чётко раскрывает тему и указывает путь к счастью."
            ),
            Answer(
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                replies_count = 0,
                message = "Это рассказ об успешном для главного героя танковом бое в Восточной Пруссии, а также о выборе который часто стоит перед каждым."
            )

        )
    }

    private fun loadReplies(): List<Reply>{
        return arrayListOf(
            Reply(
                uid = "1",
                owner_name = "Коля Абрамович",
                date = "07.03.2020",
                message = "Очень интересная книга, всем советую её прочесть, особенно тем кого часто одолевает грусть и печаль."
            )

        )
    }

    private fun setupKeyboardListener(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            if (Math.abs(view.rootView.height - (r.bottom - r.top)) > 100) { // if more than 100 pixels, its probably a keyboard...
                onKeyboardShow(view)
            }
        }
    }

    private fun onKeyboardShow(view: View) {
        (view as NestedScrollView).scrollToBottomWithoutFocusChange()
    }

}