package com.thecode.dagger_hilt_mvvm.ui.selectedblogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens.ShowNoBlogsFoundScreen
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens.ShowSelectedBlogsScreen

@AndroidEntryPoint
class SelectedBlogsFragment : DialogFragment() {

    companion object {
        const val TAG = "SELECTED_BLOGS_DIALOG"

        fun newInstance() = SelectedBlogsFragment()
    }

    private lateinit var contentView: ComposeView

    private val viewModel: SelectedBlogsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog)
    }

    override fun onResume() {
        super.onResume()
        if (showsDialog) {
            dialog?.setCanceledOnTouchOutside(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        contentView = ComposeView(requireContext())

        with(viewModel) {
            viewModelState.observe(viewLifecycleOwner, ::onViewStateChanged)
        }

        return contentView
    }

    private fun onViewStateChanged(viewState: SelectedBlogsViewModel.State) {
        contentView.apply {
            setContent {
                when (viewState) {
                    is SelectedBlogsViewModel.State.ReceivedSelectedBlogs -> {
                        ShowSelectedBlogsScreen(viewState)
                    }
                    is SelectedBlogsViewModel.State.NoSelectedBlogs -> {
                        ShowNoBlogsFoundScreen()
                    }
                }
            }
        }
    }
}
