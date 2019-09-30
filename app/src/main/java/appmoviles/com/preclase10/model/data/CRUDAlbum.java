package appmoviles.com.preclase10.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import appmoviles.com.preclase10.app.AlbumApp;
import appmoviles.com.preclase10.model.driver.DBDriver;
import appmoviles.com.preclase10.model.entity.Album;

public class CRUDAlbum {

    public static void insertAlbum(Album album){
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "INSERT INTO $TABLE($ID,$NAME,$DATE) VALUES('$VID','$VNAME', $VDATE)";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_ALBUM)
                .replace("$ID", DBDriver.ALBUM_ID)
                .replace("$NAME", DBDriver.ALBUM_NAME)
                .replace("$DATE", DBDriver.ALBUM_DATE)
                .replace("$VID", album.getId())
                .replace("$VNAME", album.getName())
                .replace("$VDATE", ""+album.getDate().getTime());


        db.execSQL(sql);
        db.close();
    }

    public static ArrayList<Album> getAllAlbums(){
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getReadableDatabase();
        ArrayList<Album> group = new ArrayList<>();

        String sql = "SELECT * FROM "+DBDriver.TABLE_ALBUM;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(  cursor.getColumnIndex(DBDriver.ALBUM_ID)  );
                String name = cursor.getString(  cursor.getColumnIndex(DBDriver.ALBUM_NAME)  );
                long ut = cursor.getLong(  cursor.getColumnIndex(DBDriver.ALBUM_DATE)  );
                Date date = new Date(ut);
                Album tasklist = new Album(id,name,date);
                group.add(tasklist);
            }while (cursor.moveToNext());
        }

        db.close();
        return group;
    }

    public static void deteleAlbum(Album album) {
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "DELETE FROM $TABLE WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_ALBUM)
                .replace("$ID",DBDriver.ALBUM_ID)
                .replace("$FID",album.getId());
        db.execSQL(sql);
        db.close();
    }
}
