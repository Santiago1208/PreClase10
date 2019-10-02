package appmoviles.com.preclase10.control.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import appmoviles.com.preclase10.R;
import appmoviles.com.preclase10.model.data.CRUDPhoto;
import appmoviles.com.preclase10.model.entity.Photo;

public class PhotoAdapter extends BaseAdapter {

    ArrayList<Photo> photos;

    public PhotoAdapter(){
        photos = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return photos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rowView = inflater.inflate(R.layout.photo_row, null);

        ImageView rowImage = rowView.findViewById(R.id.row_image);
        TextView rowName = rowView.findViewById(R.id.row_name);
        TextView rowDescription = rowView.findViewById(R.id.row_description);
        TextView rowViews = rowView.findViewById(R.id.row_views);
        Button deletePhotoButton = rowView.findViewById(R.id.deletePhotoBtn);

        rowName.setText(photos.get(i).getName());
        rowDescription.setText(photos.get(i).getDescription());
        rowViews.setText(Integer.toString(photos.get(i).getViews()));

        File imageFile = new File(viewGroup.getContext().getExternalFilesDir(null) + "/" + photos.get(i).getId() + ".png");
        Bitmap image = BitmapFactory.decodeFile(imageFile.toString());
        rowImage.setImageBitmap(image);

        deletePhotoButton.setOnClickListener(
                (View v) -> {
                    CRUDPhoto.detelePhoto(photos.get(i));
                    photos.remove(i);
                    notifyDataSetChanged();
                }
        );

        return rowView;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
        notifyDataSetChanged();
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }
}
