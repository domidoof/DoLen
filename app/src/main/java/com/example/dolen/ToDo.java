package com.example.dolen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dolen.ui.main.FileHelper;

import java.util.ArrayList;
import java.util.Objects;

//klasse für die to-do-liste
public class ToDo extends Fragment {

    public ArrayList<String> items;
    public ArrayAdapter<String> adapter;
    private EditText itemAdd;

    //Button zum hinzufügen von neuen to-dos
    private View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String itemEntered = itemAdd.getText().toString();
            Log.d("ITEM", itemAdd.getText().toString());
            adapter.add(itemEntered);
            itemAdd.setText("");
            FileHelper.writeData(items, Objects.requireNonNull(getActivity()));
            Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
        }
    };

    //Wenn auf ein item in der to-do liste geklickt wird, wird es gelöscht und eine Nachricht erscheint
    private AdapterView.OnItemClickListener itemListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            items.remove(position);
            adapter.notifyDataSetChanged();
            FileHelper.writeData(items, Objects.requireNonNull(getActivity()));
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
        }
    };

    public ToDo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_to_do, container, false);
        itemAdd = v.findViewById(R.id.item_edit_text);
        Button btnAdd = v.findViewById(R.id.add_btn);
        ListView itemsList = v.findViewById(R.id.items_list);

        items = FileHelper.readData(Objects.requireNonNull(getActivity()));

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        btnAdd.setOnClickListener(btnAddListener);

        itemsList.setOnItemClickListener(itemListClickListener);

        return v;
        }

}
