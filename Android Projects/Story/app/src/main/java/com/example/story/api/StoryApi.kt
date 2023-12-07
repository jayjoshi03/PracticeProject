package com.example.story.api

import com.example.story.model.StoryListItem
import retrofit2.Response
import retrofit2.http.GET


interface StoryApi {

    @GET("v1/ba74fead-01d8-4034-8446-7a9370e737db")
   suspend fun getStory(): Response<List<StoryListItem>>
}