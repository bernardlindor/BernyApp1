package com.bernardinc.bernyapp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<String> Items;
    ArrayAdapter<String> ItemsAdapter;
    ListView lvItems;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Items=new ArrayList<>();
        readItems();
        ItemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Items);
        lvItems=(ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(ItemsAdapter);
        //Items.add("First Item");
        //Items.add("Second Item");
        setListViewListener();
    }
    //Creation d'une methode permettant de faire l'ajout des element
    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.editText);
        String itemiext = etNewItem.getText().toString();
        ItemsAdapter.add(itemiext);
        writeItems();
        Toast.makeText(getApplicationContext(),"ajout avec succes", Toast.LENGTH_SHORT).show();
        etNewItem.setText("");
    }

    //creation d'une nouvelle methode basant sur l'ecouteur
    private void setListViewListener(){
        Log.i("MainActivity", "Setting up Listener on list view");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("MainActivity", "items remove from list" +position);
                Items.remove(position);
                Toast.makeText(getApplicationContext(),"Suppression avec succes", Toast.LENGTH_SHORT).show();
                ItemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
                                               }
                                           }

        );
    }

    private void readItems() {

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            Items=new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            Items=new ArrayList<String>();
            //Log.e("MainActivity", "Erreur de lecture du fichier", e);
        }
    }

    private void writeItems() {

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile,Items);
        } catch (IOException e) {
            Log.e("MainActivity", "Erreur d'ecriture du fichier", e);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
