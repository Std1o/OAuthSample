package com.stdio.oauthsample.presentation.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.stdio.oauthsample.R
import com.stdio.oauthsample.common.showSnackbar
import com.stdio.oauthsample.common.subscribeInUI
import com.stdio.oauthsample.common.viewBinding
import com.stdio.oauthsample.databinding.FragmentBankListBinding
import com.stdio.oauthsample.presentation.ui.adapter.BanksAdapter
import com.stdio.oauthsample.presentation.ui.dialog.WebViewDialogFragment
import com.stdio.oauthsample.presentation.ui.dialog.WebViewDialogFragment.Companion.ARG_URL
import com.stdio.oauthsample.presentation.ui.dialog.WebViewDialogFragment.Companion.KEY_WEB_VIEW_DIALOG
import com.stdio.oauthsample.presentation.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class BankListFragment : Fragment(R.layout.fragment_bank_list) {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by viewBinding(FragmentBankListBinding::bind)
    lateinit var adapter: BanksAdapter
    private val BASE_URL: String by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BanksAdapter {

        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        subscribeObservers()
        WebViewDialogFragment
            .newInstance("${BASE_URL}authorize?client_id=51463205&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=code&v=5.131&state=123456")
            .show(parentFragmentManager, KEY_WEB_VIEW_DIALOG)
        setFragmentResultListener(KEY_WEB_VIEW_DIALOG) {  requestKey, bundle ->
            val result = bundle.getString(ARG_URL)
            val uri = Uri.parse(result)
            val queryAfterFragment = uri.fragment
            val dummyUrl = "http://localhost?$queryAfterFragment"
            val dummyUri = Uri.parse(dummyUrl)
            val code = dummyUri.getQueryParameter("code")
            viewModel.getAccessToken(code!!)
        }
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            showSnackbar(it.token)
        }
    }
}