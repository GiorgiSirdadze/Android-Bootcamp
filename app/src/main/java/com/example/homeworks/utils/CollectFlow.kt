package com.example.homeworks.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(stateFlow: StateFlow<T>, collector: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        stateFlow.collectLatest(collector)
    }
}
