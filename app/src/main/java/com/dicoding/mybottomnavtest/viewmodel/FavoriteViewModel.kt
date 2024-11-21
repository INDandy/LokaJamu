package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mybottomnavtest.data.Event
import com.dicoding.mybottomnavtest.repository.NewsRepository

class FavoriteViewModel(repository: NewsRepository) : ViewModel() {
    val favoriteEvents: LiveData<List<Event>> = repository.getAllFavorites()
}