package app.ropero.com.ropero;

import android.graphics.Bitmap;

/**
 * Created by noellodou on 05/02/2018.
 */

public class Person {
    String name;
    String age;
    int photoId;
    Bitmap photoBitmap;

    Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }

    public Person(String description, String id, Bitmap bitmap) {
        this.name = description;
        this.age = id;
        this.photoBitmap = bitmap;
    }
}
