package com.android.markdowndemo.ui.grammar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.markdowndemo.R;

import java.io.IOException;
import java.io.InputStream;


public class GrammarFragment extends Fragment {

    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grammar, container, false);
    }

    public String getGrammarFromTxt(String filepath) throws IOException {
        String ans;
        InputStream in = getResources().getAssets().open(filepath);
        int length = in.available();
        byte[] buffer = new byte[length];
        in.read(buffer);
        ans = new String(buffer);
        return ans;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = getView().findViewById(R.id.tv_grammar);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            textView.setText(getGrammarFromTxt("grammar.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}