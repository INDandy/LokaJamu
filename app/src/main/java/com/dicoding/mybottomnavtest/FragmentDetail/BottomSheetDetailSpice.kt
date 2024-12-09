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

class BottomSheetDetailSpice(private val data: Bundle?): BottomSheetDialogFragment() {
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

        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.bottom_sheet_recipe, null) as View
        dialog.window?.setDimAmount(0f)
        dialog.setContentView(view)

        val bottomSheetLayout = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
        if (bottomSheetLayout != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        }

        bottomSheetBehavior?.addBottomSheetCallback(mBottomSheetBehaviorCallback)

        return dialog
    }

    private val mBottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    binding.appBarLayout.visibility = View.VISIBLE
                }
                BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> {
                    // Bottom Sheet di bawah, sembunyikan AppBarLayout
                    binding.appBarLayout.visibility = View.GONE
                }
                BottomSheetBehavior.STATE_HIDDEN -> {
                    dismiss() // Menutup Bottom Sheet jika diizinkan
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            binding.appBarLayout.alpha = slideOffset
        }
    }
}