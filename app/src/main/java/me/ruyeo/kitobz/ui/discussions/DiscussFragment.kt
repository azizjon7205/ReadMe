package me.ruyeo.kitobz.ui.discussions

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAdapter
import me.ruyeo.kitobz.databinding.FragmentDiscussBinding
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

@AndroidEntryPoint
class DiscussFragment : BaseFragment(R.layout.fragment_discuss) {

    private val binding by viewBinding { FragmentDiscussBinding.bind(it) }
    private val adapter by lazy { DiscussAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        adapter.submitList(loadDiscussions())
        adapter.onClick = {
            findNavController().navigate(
                R.id.action_discussFragment_to_detailDiscussionFragment,
                bundleOf("discuss" to Gson().toJson(it))
            )
            Log.d("@@@", "Discuss: ${it.followed} follow state")
        }
        adapter.onClickFollow = {
            Log.d("@@@", "Discuss: ${it.followed} follow")
        }

        with(binding) {
            rvDiscuss.adapter = adapter

            flInfo.setOnClickListener {
                findNavController().navigate(R.id.action_discussFragment_to_informationFragment)
            }

            fabCreate.setOnClickListener {
                findNavController().navigate(R.id.action_discussFragment_to_createDiscussionFragment)
            }
        }
    }

    private fun setupObservers() {

    }

    private fun loadDiscussions(): List<Discuss> {

        return arrayListOf(
            Discuss(
                uid = "1",
                header = "Где убили Пушкина? Когда и кем был убит Александр Пушкин",
                owner_name = "Алина Бакальчук",
                discuss_theme = "Дуэль между Александром Сергеевичем Пушкиным и Жоржем де Геккерном (Дантесом) состоялась 27 января (8 февраля) 1837 года на окраине Санкт-Петербурга, в районе Чёрной речки близ Комендантской дачи. Дуэлянты стрелялись на пистолетах. В результате дуэли Пушкин был смертельно ранен и через два дня умер.",
                messages_count = 3,
                answers_count = 2,
                created_date = "16.02.2015"
            ),
            Discuss(
                uid = "2",
                header = "Ваш любимы автор? Почему?",
                owner_name = "Илья Гребников",
                discuss_theme = "Дуэль между Александром Сергеевичем Пушкиным и Жоржем де Геккерном (Дантесом) состоялась 27 января (8 февраля) 1837 года на окраине Санкт-Петербурга, в районе Чёрной речки близ Комендантской дачи. Дуэлянты стрелялись на пистолетах. В результате дуэли Пушкин был смертельно ранен и через два дня умер.",
                messages_count = 29,
                answers_count = 18,
                created_date = "02.02.2018"
            ),
            Discuss(
                uid = "3",
                header = "ТОП 5 книг приключенческ...",
                owner_name = "Vladimir Zelen",
                discuss_theme = "Дуэль между Александром Сергеевичем Пушкиным и Жоржем де Геккерном (Дантесом) состоялась 27 января (8 февраля) 1837 года на окраине Санкт-Петербурга, в районе Чёрной речки близ Комендантской дачи. Дуэлянты стрелялись на пистолетах. В результате дуэли Пушкин был смертельно ранен и через два дня умер.",
                messages_count = 12,
                answers_count = 7,
                created_date = "26.02.2019"
            ),
            Discuss(
                uid = "4",
                header = "Ваш любимы автор? Почему?",
                owner_name = "Алина Бакальчук",
                discuss_theme = "Дуэль между Александром Сергеевичем Пушкиным и Жоржем де Геккерном (Дантесом) состоялась 27 января (8 февраля) 1837 года на окраине Санкт-Петербурга, в районе Чёрной речки близ Комендантской дачи. Дуэлянты стрелялись на пистолетах. В результате дуэли Пушкин был смертельно ранен и через два дня умер.",
                messages_count = 0,
                answers_count = 0,
                created_date = "16.02.2012"
            ),
        )
    }
}