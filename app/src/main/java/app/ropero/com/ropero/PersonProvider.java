package app.ropero.com.ropero;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.ropero.com.ropero.camera.PhotoBDD;
import app.ropero.com.ropero.camera.PhotoHome;

/**
 * Created by noellodou on 05/02/2018.
 */

public class PersonProvider {



    public List<Person> readPersons(Context c) {
        PhotoBDD photoBDD = new PhotoBDD(c);

        photoBDD.open();
        List<Person> persons = new ArrayList<Person>();

        PhotoHome photoHome = photoBDD.getPhotoWithDescription("Effeil");
        Cursor cursor = photoBDD.getBDD().rawQuery("select * from table_photo",null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String desc = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                byte[] imageID = cursor.getBlob(cursor.getColumnIndex("IMAGEID"));

                Person person = new Person(String.valueOf(id),desc,BitmapFactory.decodeByteArray(imageID, 0, imageID.length));

                persons.add(person);
                cursor.moveToNext();
            }
            photoBDD.close();
            return persons;

        }else{
            photoBDD.close();
            return Arrays.asList(
                    new Person("Paris", "Effeil tower", R.drawable.parisguidetower),
                    new Person("Abidjan ", "the place to be", R.drawable.abidjan),
                    new Person("London", "Mr potter waiting", R.drawable.london),
                    new Person("Sri Lanka", "Holidays dream", R.drawable.srilanka),
                    new Person("Alger", "Welcome in a warm country", R.drawable.alger)
            );
        }

    }
}
