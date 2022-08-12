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
import me.ruyeo.kitobz.ui.BaseFragment
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
            Log.d("@@@", "Discuss: ${it.followed} full")
        }
        adapter.onClickFollow = {
            Log.d("@@@", "Discuss: ${it.followed} follow")
        }

        with(binding) {
            rvDiscuss.adapter = adapter
        }
    }

    private fun setupObservers() {

    }

    private fun loadDiscussions(): List<Discuss> {

        return arrayListOf(
            Discuss(
                header = "Где убили Пушкина? Когда и кем был убит Александр Пушкин",
                owner_name = "Алина Бакальчук",
                messages_count = 3,
                created_date = "16.02.2015"
            ),
            Discuss(
                header = "Ваш любимы автор? Почему?",
                owner_name = "Илья Гребников",
                messages_count = 29,
                created_date = "02.02.2018"
            ),
            Discuss(
                header = "ТОП 5 книг приключенческ...",
                owner_name = "Vladimir Zelen",
                messages_count = 12,
                created_date = "26.02.2019"
            ),
            Discuss(
                header = "Ваш любимы автор? Почему?",
                owner_name = "Алина Бакальчук",
                messages_count = 20,
                created_date = "16.02.2012"
            ),
        )
    }
}