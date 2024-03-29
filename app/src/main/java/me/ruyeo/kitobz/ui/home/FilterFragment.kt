package me.ruyeo.kitobz.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentFilterBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    private val binding by viewBinding { FragmentFilterBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        with(binding){
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            sldCost.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
                override fun onStartTrackingTouch(slider: RangeSlider) {

                }

                override fun onStopTrackingTouch(slider: RangeSlider) {

                }

            })

            sldCost.addOnChangeListener { slider, value, fromUser ->

            }

            flCheckPaper.setOnClickListener {
                flCheckPaper.isActivated = !flCheckPaper.isActivated
            }
            flCheckElectronic.setOnClickListener {
                flCheckElectronic.isActivated = !flCheckElectronic.isActivated
            }
            flCheckAudio.setOnClickListener {
                flCheckAudio.isActivated = !flCheckAudio.isActivated
            }


            chipStandard.setOnClickListener {
                chipStandard.isActivated = !chipStandard.isActivated
            }
            chipPopular.setOnClickListener {
                chipPopular.isActivated = !chipPopular.isActivated
            }
            chipNew.setOnClickListener {
                chipNew.isActivated = !chipNew.isActivated
            }
            chipCheap.setOnClickListener {
                chipCheap.isActivated = !chipCheap.isActivated
            }
            chipDears.setOnClickListener {
                chipDears.isActivated = !chipDears.isActivated
            }
            chipDiscounts.setOnClickListener {
                chipDiscounts.isActivated = !chipDiscounts.isActivated
            }
            chipBigs.setOnClickListener {
                chipBigs.isActivated = !chipBigs.isActivated
            }

            flCheckCash.setOnClickListener {
                flCheckCash.isActivated = !flCheckCash.isActivated
            }
            flCheckDeliverTomorrow.setOnClickListener {
                flCheckDeliverTomorrow.isActivated = !flCheckDeliverTomorrow.isActivated
            }
            flCheckNotCash.setOnClickListener {
                flCheckNotCash.isActivated = !flCheckNotCash.isActivated
            }
            flCheckPreOrder.setOnClickListener {
                flCheckPreOrder.isActivated = !flCheckPreOrder.isActivated
            }

            bFilter.setOnClickListener {
                findNavController().navigateUp()
            }

        }
    }

}