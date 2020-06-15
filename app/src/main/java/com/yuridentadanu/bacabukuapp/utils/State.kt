package com.yuridentadanu.bacabukuapp.model

sealed class State
object Loading : State()
data class Success(val response: Any) : State()
data class Failed(val exception: Exception) : State()