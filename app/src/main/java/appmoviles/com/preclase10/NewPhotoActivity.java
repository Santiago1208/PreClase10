package appmoviles.com.preclase10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import appmoviles.com.preclase10.model.data.CRUDPhoto;
import appmoviles.com.preclase10.model.entity.Album;
import appmoviles.com.preclase10.model.entity.Photo;


public class NewPhotoActivity extends AppCompatActivity {


    private Album album;
    private TextView photoIdTv;
    private EditText photoNameEt;
    private EditText photoDescNameEt;
    private TextView photoFkTv;
    private ImageView pictureTaked;
    private Button photoCreateBTN;

    //Se generan desde el principio
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        album = (Album) getIntent().getExtras().getSerializable("album");


        pictureTaked = findViewById(R.id.pictureTaked);
        photoCreateBTN = findViewById(R.id.photoCreateBTN);

        photoIdTv = findViewById(R.id.photoIdTv);
        photoNameEt = findViewById(R.id.photoNameEt);
        photoDescNameEt = findViewById(R.id.photoDescNameEt);
        photoFkTv = findViewById(R.id.photoFkTv);

        id = UUID.randomUUID().toString();
        photoIdTv.setText("ID: "+id);
        photoFkTv.setText("The photo will be added to the album "+ album.getName());


        photoCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo task = new Photo(id, photoNameEt.getText().toString(), photoDescNameEt.getText().toString(), 0);
                CRUDPhoto.insertPhoto(album, task);
                finish();
            }
        });

    }
}
