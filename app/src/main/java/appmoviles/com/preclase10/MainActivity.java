package appmoviles.com.preclase10;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

import appmoviles.com.preclase10.model.data.CRUDAlbum;
import appmoviles.com.preclase10.model.entity.Album;

public class MainActivity extends AppCompatActivity {

    ListView LVAlbum;
    ArrayAdapter<Album> adapter;
    ArrayList<Album> list;
    Button addAlbumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        LVAlbum = findViewById(R.id.LVAlbum);
        LVAlbum.setAdapter(adapter);
        addAlbumBtn = findViewById(R.id.addAlbumBtn);



        LVAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent i = new Intent(MainActivity.this, PhotoListActivity.class);
                i.putExtra("album", list.get(pos));
                startActivity(i);
            }
        });

        LVAlbum.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar la lista de tareas?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CRUDAlbum.deteleAlbum(list.get(pos));
                                refreshTaskList();
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
                return true;
            }
        });

        addAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewAlbumActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTaskList();
    }

    private void refreshTaskList() {
        ArrayList<Album> group = CRUDAlbum.getAllAlbums();
        list.clear();
        for(int i=0 ; i<group.size() ; i++){
            list.add(group.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
