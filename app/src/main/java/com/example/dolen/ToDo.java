package com.example.dolen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

//klasse für die to-do-liste
public class ToDo extends Fragment {

    public ArrayList<String> items;
    public ArrayList<String> itemsOnline = new ArrayList<>();
    public ArrayList<String> itemsOnlineKey = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    private EditText itemAdd;

    FirebaseDatabase database;
    DatabaseReference refAddTasks;
    DatabaseReference refTaskList;

    //Button zum hinzufügen von neuen to-dos
    private View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refAddTasks = database.getReference("tasks");

            String itemEntered = itemAdd.getText().toString();
            Log.d("ITEM", itemAdd.getText().toString());
            adapter.add(itemEntered);
            Log.d("ITEM-123", String.valueOf(refAddTasks.child("1")));

            DatabaseReference newPostRef = refAddTasks.push();
            newPostRef.setValue(itemEntered);
            itemAdd.setText("");

            //FileHelper.writeData(items, Objects.requireNonNull(getActivity()));
            Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
        }
    };

    //Inhalte der to-do liste mit online firebase aktualisieren
    private View.OnClickListener fabUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDatabaseTasks();
        }
    };

    private void getDatabaseTasks() {

        refTaskList = database.getReference("tasks");
        refTaskList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsOnline.clear();
                itemsOnlineKey.clear();
                for (DataSnapshot snp : dataSnapshot.getChildren()) {
                    itemsOnline.add(String.valueOf(snp.getValue()));
                    itemsOnlineKey.add(snp.getKey());
                    Log.d("TAG-DB-Items", "Value is: " + snp);
                }
                adapter.notifyDataSetChanged();

                //FileHelper.writeData(items, Objects.requireNonNull(getActivity()));

                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());

            }
        });

    }

    //Wenn auf ein item in der to-do liste geklickt wird, wird es gelöscht und eine Nachricht erscheint
    private AdapterView.OnItemClickListener itemListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemsOnline.remove(position);
            Log.d("TAG-DB-Items", String.valueOf(position));
            adapter.notifyDataSetChanged();
            refTaskList.child(itemsOnlineKey.get(position)).removeValue();
            //FileHelper.writeData(items, Objects.requireNonNull(getActivity()));
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
        FloatingActionButton fab = v.findViewById(R.id.fab);

        database = FirebaseDatabase.getInstance();

        refTaskList = database.getReference("tasks");

        refTaskList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemsOnlineKey.clear();
                itemsOnline.clear();
                for (DataSnapshot snp : dataSnapshot.getChildren()) {
                    itemsOnline.add(snp.getValue(String.class));
                    itemsOnlineKey.add(snp.getKey());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());

            }
        });

        //items = FileHelper.readData(Objects.requireNonNull(getActivity()));

        adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, itemsOnline);

        itemsList.setAdapter(adapter);

        btnAdd.setOnClickListener(btnAddListener);

        itemsList.setOnItemClickListener(itemListClickListener);

        fab.setOnClickListener(fabUpdateListener);

        return v;
        }

}
