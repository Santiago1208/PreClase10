package appmoviles.com.preclase10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.UUID;

import appmoviles.com.preclase10.model.data.CRUDPhoto;
import appmoviles.com.preclase10.model.entity.Album;
import appmoviles.com.preclase10.model.entity.Photo;
import appmoviles.com.preclase10.util.UtilDomi;


public class NewPhotoActivity extends AppCompatActivity {

    public final static int CAMERA_CALLBACK_ID = 101;

    public final static int GALLERY_CALLBACK_ID = 102;

    private Album album;
    private TextView photoIdTv;
    private EditText photoNameEt;
    private EditText photoDescNameEt;
    private TextView photoFkTv;
    private Button takePhotoBTN;
    private Button openGalBTN;
    private ImageView pictureTaken;
    private Button photoCreateBTN;

    //Se generan desde el principio
    private String id;
    private File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        album = (Album) getIntent().getExtras().getSerializable("album");

        pictureTaken = findViewById(R.id.pictureTaken);
        photoCreateBTN = findViewById(R.id.photoCreateBTN);
        takePhotoBTN = findViewById(R.id.takePhotoBTN);
        openGalBTN = findViewById(R.id.openGalBTN);

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

        takePhotoBTN.setOnClickListener(
                (View v) ->{
                    // Acciones más particulares
                    Intent toCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Log.e(">>>", "" + getExternalFilesDir(null));
                    photoFile = new File(getExternalFilesDir(null) + "/" + id + ".png");
                    // URI: cadena de caracteres que identifica a un recurso. Oculta la ubicación exacta
                    // del recurso por razones de seguridad.
                    Uri photoUri = FileProvider.getUriForFile(NewPhotoActivity.this, getPackageName(), photoFile);
                    toCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(toCamera, CAMERA_CALLBACK_ID);
                }
        );

        openGalBTN.setOnClickListener(
                (View v) -> {
                    // Acciones generales
                    Intent toGallery = new Intent(Intent.ACTION_GET_CONTENT);
                    // Estoy accediendo a las URI que empiecen por image/
                    toGallery.setType("image/*");
                    startActivityForResult(toGallery, GALLERY_CALLBACK_ID);
                }
        );

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CALLBACK_ID && resultCode == RESULT_OK) {
            Bitmap image = BitmapFactory.decodeFile(photoFile.toString());
            pictureTaken.setImageBitmap(image);
        }else if (requestCode == GALLERY_CALLBACK_ID && resultCode == RESULT_OK) {
            // Cuando seleccione la imagen, me va a retornar un URI.
            Uri imageUri = data.getData();
            photoFile = new File(UtilDomi.getPath(this, imageUri));

            // Permite que la imagen que seleccioné haga parte de los directorios de la aplicación
            File destination = new File(getExternalFilesDir(null) + "/" + id + ".png");
            UtilDomi.copyFileUsingStream(photoFile, destination);

            Bitmap image = BitmapFactory.decodeFile(destination.toString());
            pictureTaken.setImageBitmap(image);
        }
    }
}
