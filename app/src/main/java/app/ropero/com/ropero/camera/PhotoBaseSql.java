package app.ropero.com.ropero.camera;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by noellodou on 26/02/2018.
 */

public class PhotoBaseSql extends SQLiteOpenHelper {

    private static final String TABLE_PHOTO = "table_photo";
    private static final String COL_ID = "ID";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_IMAGEID = "IMAGEID";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PHOTO + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DESCRIPTION + " TEXT NOT NULL, "
            + COL_IMAGEID + " BLOB NOT NULL);";

    public PhotoBaseSql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_PHOTO + ";");
        onCreate(db);
    }

}
