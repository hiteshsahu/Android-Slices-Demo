/*
 *  Copyright 2018 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hiteshsahu.slicedemo.view.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.sliceviewer.domain.LocalUriDataSource
import com.example.android.sliceviewer.domain.UriDataSource

class ViewModelFactory private constructor(
        private val uriDataSource: UriDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SliceViewerViewModel::class.java)) {
            return SliceViewerViewModel(uriDataSource) as T
        } else if (modelClass.isAssignableFrom(SliceViewerViewModel::class.java)) {
            return SliceViewerViewModel(uriDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        val sharedPrefs = context.getSharedPreferences(
                                LocalUriDataSource.SHARED_PREFS_NAME,
                                Context.MODE_PRIVATE
                        )
                        INSTANCE = ViewModelFactory(LocalUriDataSource(sharedPrefs))
                    }
                }
            }
            return INSTANCE
        }
    }
}