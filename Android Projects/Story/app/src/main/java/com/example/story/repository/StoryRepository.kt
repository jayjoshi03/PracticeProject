package com.example.story.repository

import com.example.story.api.StoryApi
import com.example.story.model.StoryListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StoryRepository @Inject constructor(private val storyApi : StoryApi) {

    private val _story = MutableStateFlow<List<StoryListItem>>(emptyList())
    val story : StateFlow<List<StoryListItem>>
        get() = _story

    suspend fun getStories() {
        val res = storyApi.getStory()
        if(res.isSuccessful && res.body() != null) {
            _story.emit(res.body()!!)
        }
    }
}