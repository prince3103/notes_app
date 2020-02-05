package com.example.notesappupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;
    EditText editText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = findViewById(R.id.editText);
        Intent intent= getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if(noteId==-1){
            //Toast.makeText(this,"Wrong noteid", Toast.LENGTH_SHORT).show();

        }
        else {
            editText.setText(MainActivity.notes.get(noteId));
            editText.setSelection(MainActivity.notes.get(noteId).length());
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(noteId==-1) {
                    MainActivity.notes.add("");

                    noteId = MainActivity.notes.size() - 1;
                }
                MainActivity.notes.set(noteId,String.valueOf(charSequence));
                MainActivity.notes_title.clear();
                for(int i4 = 0; i4<MainActivity.notes.size();i4++) {
                    int _length=MainActivity.notes.get(i4).length();
                    if(_length>10){
                        _length=10;
                    }
                    MainActivity.notes_title.add(MainActivity.notes.get(i4).substring(0,_length));
                }
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getSharedPreferences("com.example.notesappupdated", Context.MODE_PRIVATE);
                HashSet<String> noteSet = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
