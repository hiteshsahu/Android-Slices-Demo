package com.hiteshsahu.slicedemo.view.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.slice.widget.SliceView
import com.example.android.sliceviewer.domain.UriDataSource

class SliceViewerViewModel(private val uriDataSource: UriDataSource
) : ViewModel() {

    val selectedMode = MutableLiveData<Int>().apply { value = SliceView.MODE_LARGE }

    fun addSlice(uri: Uri) {
        uriDataSource.addUri(uri)
    }
}