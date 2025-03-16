package com.example.homeworks.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatest(flow: Flow<T>, collector: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collectLatest { collector(it) }
    }
}
