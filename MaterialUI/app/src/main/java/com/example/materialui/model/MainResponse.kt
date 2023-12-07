package com.example.materialui.model

data class MainResponse(
    val currentPage: Int,
    val listResult: List<Result>,
    val message: Any,
    val otherParameter: Any,
    val pages: Int,
    val primaryKey: Int,
    val refreshToken: Any,
    val result: String,
    val singleResult: Any,
    val status: Int,
    val tokenNo: Any,
    val totalPage: Int
)