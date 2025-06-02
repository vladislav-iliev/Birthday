package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import com.vladislaviliev.birthday.kid.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val state = repository.state
}