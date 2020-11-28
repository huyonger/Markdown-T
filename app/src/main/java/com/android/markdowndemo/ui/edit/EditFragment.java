package com.android.markdowndemo.ui.edit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.markdowndemo.R;
import com.android.markdowndemo.database.Data;
import com.android.markdowndemo.database.MyDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditFragment extends Fragment {
    EditText ed_title,ed_content;
    MyDatabase myDatabase;
    int ids;
    Data data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDatabase = new MyDatabase(getContext());
        ed_title = getView().findViewById(R.id.title);
        ed_content=getView().findViewById(R.id.content);
        ids = getArguments().getInt("id");
        if (ids != 0){
            data = myDatabase.getTiandCon(ids);
            ed_title.setText(data.getTitle());
            ed_content.setText(data.getContent());
        }
        FloatingActionButton transform;
        transform = getView().findViewById(R.id.transform);
        transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getView().findViewById(R.id.content);
                String string = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("text", string);
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_editFragment_to_htmlFragment, bundle);
            }
        });

        FloatingActionButton save;
        save = getView().findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH：mm");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);

                String title = ed_title.getText().toString();
                String content = ed_content.getText().toString();

                if (ids != 0){
                    data=new Data(title,ids, content, time);
                    myDatabase.toUpdate(data);
                }else{
                    data=new Data(title,content,time);
                    myDatabase.toInsert(data);
                    ids = 1;
                }
            }
        });

        FloatingActionButton share;
        share = getView().findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getView().findViewById(R.id.content);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,editText.getText().toString());
                startActivity(intent);
            }
        });
    }
}