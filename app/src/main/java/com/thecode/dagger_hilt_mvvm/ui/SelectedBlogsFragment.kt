package com.thecode.dagger_hilt_mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.databinding.FragmentBlankBinding
import com.thecode.dagger_hilt_mvvm.databinding.FragmentSelectedBlogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedBlogsFragment : DialogFragment() {

    private lateinit var binding: FragmentSelectedBlogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSelectedBlogsBinding.inflate(layoutInflater,
            container, false).let {
            binding = it
            it.root
        }
    }

    // TODO
    // added SelectedBlogsViewModel to provide this with a list of selected blogs from the
    // blogs_selected table
    // then use this fragment to display the information using Android Compose View
    // NOTE will need to create a new git branch for this project and update the project
    // to run in Android Preview
}