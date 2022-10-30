package com.stdio.oauthsample.presentation.ui.dialog

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.setFragmentResult
import com.stdio.oauthsample.common.showIf
import com.stdio.oauthsample.databinding.DialogWebViewBinding

class WebViewDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        CookieManager.getInstance().setAcceptCookie(true);
        arguments?.getString(ARG_URL)?.let {
            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    CookieSyncManager.getInstance().sync()
                    binding.progressBar.showIf(false)
                    if (url.startsWith("https://oauth.vk.com/blank.html")) {
                        val bundle = Bundle()
                        bundle.putString(ARG_URL, url)
                        setFragmentResult(KEY_WEB_VIEW_DIALOG, bundle)
                        dismiss()
                    }
                }
            }
            binding.webView.loadUrl(it)
        }
    }

    companion object {
        const val KEY_WEB_VIEW_DIALOG = "web_view_dialog"
        const val ARG_URL = "item_count"

        fun newInstance(url: String): WebViewDialogFragment =
            WebViewDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}