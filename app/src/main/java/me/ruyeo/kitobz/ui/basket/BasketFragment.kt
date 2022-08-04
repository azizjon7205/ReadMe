package me.ruyeo.kitobz.ui.basket

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.BasketBookAdapter
import me.ruyeo.kitobz.databinding.FragmentBasketBinding
import me.ruyeo.kitobz.databinding.FragmentBasketInformationBinding
import me.ruyeo.kitobz.databinding.FragmentSubMyLibraryElectronBinding
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.ui.basket.information.InfoModel
import viewBinding

@AndroidEntryPoint
class BasketFragment : BaseFragment(R.layout.fragment_basket) {
    private val binding by viewBinding { FragmentBasketBinding.bind(it) }
    private val basketBookAdapter by lazy { BasketBookAdapter() }

    // Helper for Select Payment Type
    private var cashHelper = false
    private var capitalCardHelper = false
    private var visaCardHelper = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cashHelper = false
        capitalCardHelper = false
        visaCardHelper = false

        initViews()
    }

    private fun initViews() {
        binding.ivInfo.setOnClickListener {
            findNavController().navigate(R.id.informationFragment)
        }
        Log.d("TAGBasketItems", "initViews: ${getAllBasketBooks()}")
        basketBookAdapter.submitList(getAllBasketBooks())
        binding.rvBasket.adapter = basketBookAdapter
        
        setComment()
        setPaymentState()
        clickBCheckOut()
    }

    private fun clickBCheckOut() {
        binding.bCheckOut.setOnClickListener { 
            if (cashHelper){
                findNavController().navigate(R.id.basketOrderFragment, bundleOf("paymentType" to "Cash"))
            }else if (capitalCardHelper){
                findNavController().navigate(R.id.basketOrderFragment, bundleOf("paymentType" to "Capital Card"))
            }else if (visaCardHelper){
                findNavController().navigate(R.id.basketOrderFragment, bundleOf("paymentType" to "Visa Card"))
            }else{
                Toast.makeText(requireContext(), "Please choose the desired payment system!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPaymentState() {
        with(binding){
            ivPayCash.setOnClickListener {
                capitalCardHelper = false
                ivPayCapitalCard.setImageResource(R.drawable.ic_circle)
                visaCardHelper = false
                ivPayVisaCard.setImageResource(R.drawable.ic_circle)
                if (cashHelper){
                    ivPayCash.setImageResource(R.drawable.ic_circle)
                    bCheckOut.setBackgroundResource(R.drawable.tv_rounded)
                    bCheckOut.setTextColor(Color.BLACK)
                }else{
                    ivPayCash.setImageResource(R.drawable.ic_check_circle)
                    bCheckOut.setBackgroundResource(R.drawable.btn_bgn)
                    bCheckOut.setTextColor(Color.WHITE)
                }
                cashHelper = !cashHelper
            }
            ivPayCapitalCard.setOnClickListener {
                cashHelper = false
                ivPayCash.setImageResource(R.drawable.ic_circle)
                visaCardHelper = false
                ivPayVisaCard.setImageResource(R.drawable.ic_circle)
                if (capitalCardHelper){
                    ivPayCapitalCard.setImageResource(R.drawable.ic_circle)
                    bCheckOut.setBackgroundResource(R.drawable.tv_rounded)
                    bCheckOut.setTextColor(Color.BLACK)
                }else{
                    ivPayCapitalCard.setImageResource(R.drawable.ic_check_circle)
                    bCheckOut.setBackgroundResource(R.drawable.btn_bgn)
                    bCheckOut.setTextColor(Color.WHITE)
                }
                capitalCardHelper = !capitalCardHelper
            }
            ivPayVisaCard.setOnClickListener {
                capitalCardHelper = false
                ivPayCapitalCard.setImageResource(R.drawable.ic_circle)
                cashHelper = false
                ivPayCash.setImageResource(R.drawable.ic_circle)
                if (visaCardHelper){
                    ivPayVisaCard.setImageResource(R.drawable.ic_circle)
                    bCheckOut.setBackgroundResource(R.drawable.tv_rounded)
                    bCheckOut.setTextColor(Color.BLACK)
                }else{
                    ivPayVisaCard.setImageResource(R.drawable.ic_check_circle)
                    bCheckOut.setBackgroundResource(R.drawable.btn_bgn)
                    bCheckOut.setTextColor(Color.WHITE)
                }
                visaCardHelper = !visaCardHelper
            }
        }
    }

    private fun setComment() {
        binding.ivAddComment.setOnClickListener{
            binding.ivAddComment.visibility = View.GONE
            binding.llClearAndChecked.visibility = View.VISIBLE
            binding.edtComment.visibility = View.VISIBLE
            binding.tvCommentState.text = getString(R.string.str_comment)
        }
        binding.ivCheckedComment.setOnClickListener{
            if (binding.edtComment.text.toString().isNotEmpty()){
                binding.edtComment.visibility = View.GONE
                binding.llComment.visibility = View.VISIBLE
                binding.tvComment.text = binding.edtComment.text.toString()
                binding.edtComment.text.clear()
                binding.ivCheckedComment.visibility = View.GONE
                hideKeyboard()
            }
        }
        binding.ivClearComment.setOnClickListener {
            binding.ivAddComment.visibility = View.VISIBLE
            binding.llClearAndChecked.visibility = View.GONE
            binding.edtComment.visibility = View.GONE
            binding.llComment.visibility = View.GONE
            binding.tvCommentState.text = getString(R.string.str_add_comment)
        }
    }

    private fun getAllBasketBooks(): ArrayList<InfoModel>{
        val items: ArrayList<InfoModel> = ArrayList()
        items.add(InfoModel("paper", true))
        items.add(InfoModel("electronic", false))
        items.add(InfoModel("electronic", false))
        items.add(InfoModel("audio", false))
        items.add(InfoModel("paper", false))
        return items
    }

}