package com.dicoding.mybottomnavtest.FragmentDetail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.IngredientsItem
import com.dicoding.mybottomnavtest.Response.RecipesItem
import com.dicoding.mybottomnavtest.Response.StepsItem
import com.dicoding.mybottomnavtest.database.Recipe
import com.dicoding.mybottomnavtest.database.RecipeDao
import com.dicoding.mybottomnavtest.database.RecipeDatabase
import com.dicoding.mybottomnavtest.databinding.BottomSheetRecipeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class BottomSheetDetailRecipe(private val data: Bundle?): BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecipeBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var database: RecipeDatabase
    private lateinit var recipeDao: RecipeDao
    private var articleDetail: RecipesItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetRecipeBinding.inflate(inflater, container, false)
        binding.tvRecipeHeader.text = data?.getString("name")
        binding.tvRecipeName.text = data?.getString("name")
        binding.tvRecipeDescription.text = data?.getString("description")
        binding.tvRecipeIngredients.text = data?.getString("ingredients")
        binding.tvRecipeSteps.text = data?.getString("steps")
        binding.tvRecipeTips.text = data?.getString("tips")

        val applicationContext = requireActivity().applicationContext

        database = RecipeDatabase.getRecipeDatabase(applicationContext)
        recipeDao = database.recipeDao()

        val tags = data?.getString("tags")?.split(", ")
        if (tags != null && tags.isNotEmpty()) {
            for (tag in tags) {
                val chip = Chip(context).apply {
                    text = tag
                    isClickable = true
                    isCheckable = false

                }
                binding.tagsRecipe.addView(chip)
            }
        } else {
            binding.tagsRecipe.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
//        binding.fabBookmark.setOnClickListener {
//            articleDetail?.let { recipe ->
//                toggleBookmark(recipe)
//            }
//        }


        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.bottom_sheet_recipe, null) as View
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

    //DATABASE

//    private fun toggleBookmark(article: RecipesItem) {
//        lifecycleScope.launch {
//            val isFavorite = recipeDao.isFavorite(article.id ?: -1)
//            if (isFavorite > 0) {
//                recipeDao.delete(article.id ?: -1)
//                binding.fabBookmark.setImageResource(R.drawable.favorite_border)
//
//            } else {
//                recipeDao.insert(article.toRecipe())
//                binding.fabBookmark.setImageResource(R.drawable.favorite)
//
//            }
//        }
//    }


    private fun RecipesItem.toRecipe(): Recipe {
        return Recipe(
            id = this.id ?: -1,
            name = this.name ?: "",
            tags = this.tags ?: emptyList(),
            imageUrl = this.imageUrl,
            ingredients = this.ingredients?.map {
                IngredientsItem(
                    ingredient = it?.ingredient,
                    quantity = it?.quantity,
                    notes = it?.notes
                )
            } ?: emptyList(),
            description = this.description ?: "",
            steps = this.steps?.map {
                StepsItem(
                    instruction = it?.instruction,
                    stepNumber = it?.stepNumber?.toString()
                )
            } ?: emptyList(),
            servingSize = this.servingSize ?: "",
            prepTime = this.prepTime ?: "",
            cookTime = this.cookTime ?: "",
            totalTime = this.totalTime ?: "",
            isFavorite = true
        )
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //DATABASE
}