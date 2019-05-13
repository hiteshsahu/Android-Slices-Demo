package com.hiteshsahu.slicedemo.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.slice.widget.SliceView
import com.hiteshsahu.slicedemo.R
import com.hiteshsahu.slicedemo.domain.ktx.bind
import com.hiteshsahu.slicedemo.domain.ktx.convertToOriginalScheme
import com.hiteshsahu.slicedemo.domain.ktx.hasSupportedSliceScheme
import com.hiteshsahu.slicedemo.view.activity.SliceViewerActivity
import com.hiteshsahu.slicedemo.view.viewmodel.SliceViewerViewModel
import com.hiteshsahu.slicedemo.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.slice_viewer_fragment.*


/**
 * Host a single slice with given URI
 */
class SliceHostFragment : Fragment() {

    private lateinit var viewModel: SliceViewerViewModel

    companion object {
        fun newInstance(sliceURL: String) = SliceHostFragment().apply {
            arguments = Bundle(1).apply {
                putString(SLICE_URL, sliceURL)
            }
        }

        const val SLICE_URL = "SLICE_URL"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.slice_viewer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = ViewModelFactory.getInstance(activity!!.application)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SliceViewerViewModel::class.java)

        val intent = activity!!.intent

        val sliceURL = arguments!!.getString(SLICE_URL, "")

        val sliceURI = Uri.parse(sliceURL)

        sliceURI?.let {

            // If a URI was passed in has a supported slice scheme, present the Slice and save it to the
            // persistent list of Slices
            if (it.hasSupportedSliceScheme()) {

                // add slice for URI
                val sliceUri = it.convertToOriginalScheme()
                viewModel.addSlice(sliceUri)
                bindSlice(sliceUri)
            } else {

                // No Slice found, fall back to main page.
                Toast.makeText(activity!!.applicationContext, "No Slice URI found, sending to SliceViewerActivity", Toast.LENGTH_LONG).show()
                startActivity(Intent(activity, SliceViewerActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                })
            }
        }
    }

    private fun bindSlice(uri: Uri) {
        sliceView.bind(
                context = activity!!.applicationContext,
                lifecycleOwner = this,
                uri = uri,
                scrollable = true
        )
        viewModel.selectedMode.observe(this,
                Observer {
                    sliceView.mode = it ?: SliceView.MODE_LARGE
                })
        uriValue.text = uri.toString()
    }


}
