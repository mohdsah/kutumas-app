package com.kutumas.webview

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var adView: AdView? = null
    private val START_URL = "http://modalcircle.sytes.net"
    private val VERSION_URL = "http://modalcircle.sytes.net/app_version.json"
    private val APK_URL = "http://modalcircle.sytes.net/kutumas.apk"
    private val CURRENT_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                view?.loadUrl("file:///android_asset/offline.html")
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean { return false }
        }
        // init admob
        MobileAds.initialize(this) {}
        adView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)

        // register network callback for auto reload
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(nr, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { runOnUiThread { try { if(!webView.url?.startsWith("file://")!!) webView.reload() } catch(e:Exception){} } }
        })

        webView.loadUrl(START_URL)
        CheckVersionTask().execute(VERSION_URL)
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) { webView.goBack() }
        else { confirmExit() }
    }

    private fun confirmExit() {
        AlertDialog.Builder(this)
            .setTitle("Keluar")
            .setMessage("Anda pasti mahu keluar dari KUTUMAS?")
            .setPositiveButton("Ya") { _, _ -> finish() }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private inner class CheckVersionTask : AsyncTask<String, Void, Int?>() {
        override fun doInBackground(vararg params: String?): Int? {
            try { 
                val u = URL(params[0])
                val con = u.openConnection() as HttpURLConnection
                con.connectTimeout = 5000; con.readTimeout = 5000
                val br = BufferedReader(InputStreamReader(con.inputStream))
                val sb = StringBuilder(); var line: String?
                while(br.readLine().also{ line = it } != null) sb.append(line)
                br.close()
                val jo = JSONObject(sb.toString())
                return jo.optInt("version", -1)
            } catch(e: Exception) { return null }
        }

        override fun onPostExecute(result: Int?) {
            if(result != null && result > 0 && result > CURRENT_VERSION) {
                AlertDialog.Builder(this@MainActivity)
                  .setTitle("Kemas kini tersedia")
                  .setMessage("Versi aplikasi terbaru tersedia. Muat turun & pasang sekarang?")
                  .setPositiveButton("Muat turun") { _, _ -> openApkUrl() }
                  .setNegativeButton("Nanti", null)
                  .show()
            }
        }
    }

    private fun openApkUrl() {
        try { 
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(APK_URL))
            startActivity(intent)
        } catch(e: ActivityNotFoundException) { }
    }
}
