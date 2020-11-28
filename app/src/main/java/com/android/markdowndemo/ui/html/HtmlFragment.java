package com.android.markdowndemo.ui.html;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.android.markdowndemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HtmlFragment extends Fragment {
    private WebView webView;
    private String text;
    private String html;
    private FloatingActionButton share;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_html, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        share = getView().findViewById(R.id.html_share);
        text = getArguments().getString("text");
        webView = getView().findViewById(R.id.html);
        webView.loadUrl("file:///android_asset/www/markdown.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(HtmlFragment.this, "android");

    }

    @JavascriptInterface
    public String getText(){
        Log.i("qcl0228", "js调用了安卓的方法");
        return text;
    }
}