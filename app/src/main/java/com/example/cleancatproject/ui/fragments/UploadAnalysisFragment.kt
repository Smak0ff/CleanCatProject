package com.example.cleancatproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.FragmentUploadAnalysisBinding
import com.example.cleancatproject.viewmodel.UploadAnalysisFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class UploadAnalysisFragment : Fragment(R.layout.fragment_upload_analysis) {
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val mUploadAnalysisFragmentViewModel: UploadAnalysisFragmentViewModel by viewModels { vmFactory }
    private lateinit var mBinding: FragmentUploadAnalysisBinding
    private lateinit var mTextView: TextView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUploadAnalysisBinding.inflate(layoutInflater)
        mTextView = mBinding.analysisTextId
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mUploadAnalysisFragmentViewModel.apiLiveData.observe(this, Observer {
            mTextView.text = it.toString()
        })
    }
}