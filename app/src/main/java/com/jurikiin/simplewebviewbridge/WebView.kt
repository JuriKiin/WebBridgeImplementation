package com.jurikiin.simplewebviewbridge

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.platform.LocalContext
import de.andycandy.android.bridge.Bridge

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(url: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.testTag("webView"),
        factory = {

            val webView = WebView(it)
            val bridge = Bridge(context, webView)
            bridge.addJSInterface(ExampleInterface())

            webView.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    databaseEnabled = true
                    domStorageEnabled = true
                    javaScriptEnabled = true
                    setSupportZoom(false)
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        bridge.init()
                    }
                }
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}