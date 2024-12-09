package com.dicoding.mybottomnavtest.FragmentDetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.BottomSheetRecipeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDetailRecipe(private val data: Bundle?): BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecipeBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetRecipeBinding.inflate(inflater, container, false)

        binding.tvRecipeHeader.text = data?.getString("name")
        binding.tvRecipeName.text = data?.getString("name")
        binding.tvRecipeDescription.text = data?.getString("description")
        binding.tvRecipeIngredients.text = data?.getString("ingredients")
        binding.tvRecipeSteps.text = data?.getString("steps")
        binding.tvRecipeTips.text = data?.getString("tips")

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