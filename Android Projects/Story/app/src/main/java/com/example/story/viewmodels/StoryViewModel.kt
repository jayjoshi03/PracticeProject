package com.example.story.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.story.model.StoryListItem
import com.example.story.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val repository : StoryRepository) : ViewModel() {
    val storyList : StateFlow<List<StoryListItem>>
        get() = repository.story

    init {
        viewModelScope.launch {
            repository.getStories()
        }
    }
}