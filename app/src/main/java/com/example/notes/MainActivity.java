package com.example.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes=new ArrayList<>();
    ListView add;
    static  ArrayAdapter attach;
    SharedPreferences globe;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater fool=getMenuInflater();
        fool.inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.no){
            Intent nexa=new Intent(getApplicationContext(),add_notes.class);
            startActivity(nexa);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.open);
        globe=getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) globe.getStringSet("note",null);
        if (set == null) {
            notes.add("new notes");
        }
        else{
            notes=new ArrayList(set);
        }

        attach=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        add.setAdapter(attach);
        add.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next=new Intent(getApplicationContext(),add_notes.class);
                next.putExtra("Id",position);
                startActivity(next);
            }
        });
        add.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               final int delo=position;
               new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are you sure?")
                       .setMessage("Do you want to delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       notes.remove(delo);
                       attach.notifyDataSetChanged();
                       HashSet<String> store=new HashSet<>(MainActivity.notes);
                       globe.edit().putStringSet("note",store).apply();
                   }
               }).setNegativeButton("No",null).show();

                return true;
            }
        });

    }
}