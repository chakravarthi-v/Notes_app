package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class add_notes extends AppCompatActivity {
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);
        EditText edit=findViewById(R.id.goa);
        Intent change=getIntent();
        ID=change.getIntExtra("Id",-1);
        if(ID!=-1){
            edit.setText(MainActivity.notes.get(ID));
        }
        else{
            MainActivity.notes.add("");
            ID=MainActivity.notes.size()-1;
        }
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(ID,String.valueOf(s));
                MainActivity.attach.notifyDataSetChanged();
                SharedPreferences globe=getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> store=new HashSet<>(MainActivity.notes);
                globe.edit().putStringSet("notes",store).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}