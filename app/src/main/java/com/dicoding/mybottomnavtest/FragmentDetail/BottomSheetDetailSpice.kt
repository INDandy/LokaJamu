package com.dicoding.mybottomnavtest.FragmentDetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.BottomSheetSpiceBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class BottomSheetDetailSpice(private val data: Bundle?) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetSpiceBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSpiceBinding.inflate(inflater, container, false)
        binding.tvSpiceHeader.text = data?.getString("name")
        binding.tvSpiceName.text = data?.getString("name")
        binding.tvSpiceDescription.text = data?.getString("description")
        binding.tvSpiceBenefits.text = data?.getString("benefits")
        binding.tvSpiceJamulist.text = data?.getString("jamulist")


        val tags = data?.getString("tags")?.split(", ")
        if (tags != null && tags.isNotEmpty()) {
            for (tag in tags) {
                val chip = Chip(context).apply {
                    text = tag
                    isClickable = true
                    isCheckable = false

                }
                binding.tagsSpice.addView(chip)
            }
        } else {
            binding.tagsSpice.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.bottom_sheet_spice, null) as View
        dialog.setContentView(view)

        val bottomSheetLayout =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
        if (bottomSheetLayout != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        }

        bottomSheetBehavior?.addBottomSheetCallback(mBottomSheetBehaviorCallback)

        return dialog
    }


    private val mBottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback =
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.appBarLayout.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> {
                        binding.appBarLayout.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        dismiss()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.appBarLayout.alpha = slideOffset
            }
        }
}