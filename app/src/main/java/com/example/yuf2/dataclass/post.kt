package com.example.yuf2.dataclass

data class post(
    val title: String ="",
    val content: String="",
    val nickname: String="",
    val uid: String="",
    var starCount: Int = 0,
    var stars: MutableMap<String, Boolean> = HashMap()
)
