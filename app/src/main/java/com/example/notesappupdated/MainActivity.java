package com.example.notesappupdated;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int noteId;
    HashSet<String> noteSet;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<String> notes_title = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        sharedPreferences = this.getSharedPreferences("com.example.notesappupdated", Context.MODE_PRIVATE);

        noteSet = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

//        Log.i("Shared Preference data ", noteSet.toString());

        if(noteSet!=null){
            notes= new ArrayList<>(noteSet);

        } else {
           notes.add("Example Note");
        }
        Log.i("Data in notes is ", notes.toString());
        notes_title.clear();
        for(int i = 0; i<notes.size();i++) {
            int _length=notes.get(i).length();
            if(_length>10){
                _length=10;
            }
            notes_title.add(notes.get(i).substring(0,_length));
        }
        arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,notes_title);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final  int item_del_index=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure")
                        .setMessage("Do you really want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(item_del_index);
                                notes_title.clear();
                                for(int i4 = 0; i4<notes.size();i4++) {
                                    int _length=notes.get(i4).length();
                                    if(_length>10){
                                        _length=10;
                                    }
                                    notes_title.add(notes.get(i4).substring(0,_length));
                                }
                                arrayAdapter.notifyDataSetChanged();
                                noteSet= new HashSet<>(notes);
                                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.add_note){

            Intent intent = new Intent(this, NoteEditorActivity.class);

            startActivity(intent);
        }
        return true;
    }
}
