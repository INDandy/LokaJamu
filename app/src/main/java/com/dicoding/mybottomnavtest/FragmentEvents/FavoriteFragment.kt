package com.dicoding.mybottomnavtest.FragmentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.BookmarksFragment.ArticlesFragment
import com.dicoding.mybottomnavtest.BookmarksFragment.RecipesFragment
import com.dicoding.mybottomnavtest.BookmarksFragment.SpicesFragment
import com.dicoding.mybottomnavtest.adapter.FavoriteEventAdapter
import com.dicoding.mybottomnavtest.database.AppDatabase
import com.dicoding.mybottomnavtest.databinding.FragmentFavoriteBinding
import com.dicoding.mybottomnavtest.repository.NewsRepository
import com.dicoding.mybottomnavtest.viewmodel.FavoriteViewModel
import com.dicoding.mybottomnavtest.viewmodel.FavoriteViewModelFactory

//class FavoriteFragment : Fragment() {
//    private var _binding: FragmentFavoriteBinding? = null
//    private val binding get() = _binding!! // non-null assertion
//
//    private lateinit var favoriteViewModel: FavoriteViewModel
//    private lateinit var favoriteEventAdapter: FavoriteEventAdapter
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Set up ProgressBar
//        binding.progressBar.visibility = View.VISIBLE
//        binding.recyclerViewFavorite.visibility = View.GONE
//        binding.emptyStateMessage.visibility = View.GONE
//
//        binding.recyclerViewFavorite.layoutManager = GridLayoutManager(requireContext(), 2)
//        favoriteEventAdapter = FavoriteEventAdapter(this)
//        binding.recyclerViewFavorite.adapter = favoriteEventAdapter
//
//        val eventDao = AppDatabase.getDatabase(requireContext()).eventDao()
//        val repository = NewsRepository(eventDao)
//
//        favoriteViewModel = ViewModelProvider(this, FavoriteViewModelFactory(repository))[FavoriteViewModel::class.java]
//
//        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner, Observer { events ->
//            binding.progressBar.visibility = View.GONE
//
//            if (events.isEmpty()) {
//                binding.emptyStateMessage.visibility = View.VISIBLE
//                binding.recyclerViewFavorite.visibility = View.GONE
//            } else {
//                binding.emptyStateMessage.visibility = View.GONE
//                binding.recyclerViewFavorite.visibility = View.VISIBLE
//                favoriteEventAdapter.submitList(events)
//            }
//        })
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}



import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.mybottomnavtest.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        viewPager2.adapter = ViewPagerAdapter(this)


        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Recipes"
                1 -> "Articles"
                2 -> "Spices"
                else -> null
            }
        }.attach()
    }

    private inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RecipesFragment()
                1 -> ArticlesFragment()
                2 -> SpicesFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}