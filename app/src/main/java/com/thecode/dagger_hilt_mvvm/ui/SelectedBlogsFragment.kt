package com.thecode.dagger_hilt_mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import com.thecode.dagger_hilt_mvvm.databinding.FragmentSelectedBlogsBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Text

@AndroidEntryPoint
class SelectedBlogsFragment : DialogFragment() {

    companion object {

        const val TAG = "SELECTED_BLOGS_DIALOG"

        fun newInstance() = SelectedBlogsFragment()
    }

    private lateinit var binding: FragmentSelectedBlogsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO Using Jetpack compose display list of selected blogs
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                Text("Displaying text using Jetpack compose")
            }
        }
        return view
    }

    // TODO
    // added SelectedBlogsViewModel to provide this with a list of selected blogs from the
    // blogs_selected table
    // then use this fragment to display the information using Android Compose View
    // NOTE will need to create a new git branch for this project and update the project
    // to run in Android Preview
}
