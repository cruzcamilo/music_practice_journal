package com.example.musicpracticejournal.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B> mapWithDefault(
    source: LiveData<A>,
    defaultValue: B,
    mapper: (A) -> B
): LiveData<B> {
    return MediatorLiveData<B>().apply {
        postValue(defaultValue)
        addSource(source) {
            postValue(mapper(it))
        }
    }
}