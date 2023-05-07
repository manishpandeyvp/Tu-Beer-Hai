package com.example.tubeerhai.presentation

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.tubeerhai.R
import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.databinding.BsBeerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BeerDetailsBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BsBeerLayoutBinding
    private var model: BeerModel? = null

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        initDialog()
    }

    private fun initDialog() {
        model = arguments?.getParcelable(DATA) as? BeerModel

        model?.let {
            binding = BsBeerLayoutBinding.inflate(layoutInflater)
            this.isCancelable = true
            setData()
            dialog?.setContentView(binding.root)

            (binding.root.parent as ViewGroup).setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
        }
    }

    private fun setData() {
        model?.let { _model ->
            context?.let { Glide.with(it).load(_model.imageUrl).into(binding.ivBeerImage) }
            binding.tvBsTitle.text = _model.name
            binding.tvBsTag.text = _model.tagline
            binding.tvBsDesc.text = _model.description
            binding.tvBsFirstBrewed.text = resources.getString(R.string.first_brewed, _model.firstBrewed)
            binding.tvBsBrewersTip.text = _model.brewersTips
        }
    }

    companion object {
        private const val DATA = "MODEL_DATA"

        fun newInstance(model: BeerModel): BeerDetailsBottomSheet {
            val bottomSheet = BeerDetailsBottomSheet()
            val args = Bundle()
            args.putParcelable(DATA, model)
            bottomSheet.arguments = args
            return bottomSheet
        }
    }
}