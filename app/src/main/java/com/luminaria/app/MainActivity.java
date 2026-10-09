package com.luminaria.app;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

/** Luminaria: loads the bundled offline app (assets/index.html) in a full-screen WebView. */
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private WebView web;
    private TextToSpeech tts;
    private boolean ttsReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);          // needed for file:///android_asset
        s.setAllowContentAccess(false);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setTextZoom(100);                  // same look regardless of the phone's font size

        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Everything is bundled; never leave the app.
                return !request.getUrl().toString().startsWith("file:///android_asset/");
            }
        });

        tts = new TextToSpeech(this, this);
        web.addJavascriptInterface(new Bridge(), "LuminariaTTS");

        if (savedInstanceState != null) {
            web.restoreState(savedInstanceState);
        } else {
            web.loadUrl("file:///android_asset/index.html");
        }
    }

    @Override
    public void onInit(int status) {
        ttsReady = (status == TextToSpeech.SUCCESS);
    }

    /** Lets the app's "Read aloud" buttons use the phone's own text-to-speech voice. */
    public class Bridge {
        @JavascriptInterface
        public void speak(String text, String lang) {
            if (!ttsReady || tts == null || text == null) return;
            Locale loc = Locale.US;
            if ("fil".equals(lang)) {
                loc = new Locale("fil", "PH");
                if (tts.isLanguageAvailable(loc) < TextToSpeech.LANG_AVAILABLE) {
                    loc = new Locale("tl", "PH");
                }
                if (tts.isLanguageAvailable(loc) < TextToSpeech.LANG_AVAILABLE) {
                    loc = Locale.getDefault();
                }
            }
            tts.setLanguage(loc);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "luminaria");
        }
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) {
            web.goBack();          // steps back inside the app first
        } else {
            super.onBackPressed(); // then leaves the app
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (web != null) web.saveState(outState);
    }

    @Override
    protected void onPause() {
        if (tts != null) tts.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) { tts.stop(); tts.shutdown(); }
        if (web != null) { web.destroy(); }
        super.onDestroy();
    }
}
