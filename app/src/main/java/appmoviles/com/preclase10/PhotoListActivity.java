package appmoviles.com.preclase10;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import appmoviles.com.preclase10.model.data.CRUDPhoto;
import appmoviles.com.preclase10.model.entity.Album;
import appmoviles.com.preclase10.model.entity.Photo;


public class PhotoListActivity extends AppCompatActivity {

    private TextView titlePhotolist;
    private Album album;
    private Button addPhotoBtn;
    private ListView photoLV;
    private ArrayAdapter<Photo> adapter;
    private ArrayList<Photo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        titlePhotolist = findViewById(R.id.title_album);
        addPhotoBtn = findViewById(R.id.addPhotoBtn);
        photoLV = findViewById(R.id.photoLV);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        photoLV.setAdapter(adapter);


        album = (Album) getIntent().getExtras().getSerializable("album");
        titlePhotolist.setText(album.getName());


        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhotoListActivity.this, NewPhotoActivity.class);
                i.putExtra("album", album);
                startActivity(i);
            }
        });

        photoLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoListActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar la foto?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CRUDPhoto.deteleTask(list.get(pos));
                                refreshTasks();
                                dialogInterface.dismiss();
                            }
                        });

                builder.show();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTasks();
    }

    private void refreshTasks() {
        ArrayList<Photo> group = CRUDPhoto.getAllTaskOfTasklist(album);
        list.clear();
        for(int i=0 ; i<group.size() ; i++){
            list.add(group.get(i));
        }
        adapter.notifyDataSetChanged();
    }

}
