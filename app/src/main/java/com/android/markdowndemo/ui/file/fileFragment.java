package com.android.markdowndemo.ui.file;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.markdowndemo.MainActivity;
import com.android.markdowndemo.R;
import com.android.markdowndemo.database.Data;
import com.android.markdowndemo.database.MyDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * 主页面,文件列表
 */


public class fileFragment extends Fragment {

    ListView listView;
    TextView empty;
    FloatingActionButton floatingActionButton;
    LayoutInflater layoutInflater;
    ArrayList<Data> arrayList;
    MyDatabase myDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getView().findViewById(R.id.layout_listview);

        empty = getView().findViewById(R.id.emptytext);

        listView.setEmptyView(empty);
        floatingActionButton = getView().findViewById(R.id.addfile);
        layoutInflater = getLayoutInflater();

        myDatabase = new MyDatabase(getActivity());

        arrayList = myDatabase.getarray();
        MyAdapter adapter = new MyAdapter(layoutInflater,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", arrayList.get(i).getIds());
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_nav_file_to_editFragment,bundle);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                new AlertDialog.Builder(getContext()).setMessage("确定要删除吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        myDatabase.toDelete(arrayList.get(i).getIds());
                        arrayList = myDatabase.getarray();
                        MyAdapter myAdapter = new MyAdapter(layoutInflater,arrayList);
                        listView.setAdapter(myAdapter);
                    }
                })
                .create()
                .show();
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_nav_file_to_editFragment,bundle);
            }
        });
    }
}