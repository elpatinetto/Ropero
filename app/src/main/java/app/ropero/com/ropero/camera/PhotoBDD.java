package app.ropero.com.ropero.camera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by noellodou on 26/02/2018.
 */

public class PhotoBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "ropero.db";

    private static final String TABLE_PHOTO = "table_photo";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final int NUM_COL_DESCRIPTION = 1;
    private static final String COL_IMAGEID = "IMAGEID";
    private static final int NUM_COL_IMAGEID = 2;

    private SQLiteDatabase bdd;

    private PhotoBaseSql maPhotoBaseSql;

    public PhotoBDD(Context context){
        //On crée la BDD et sa table
        maPhotoBaseSql = new PhotoBaseSql(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maPhotoBaseSql.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertPhoto(PhotoHome photo){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_DESCRIPTION,photo.getDescription());
        values.put(COL_IMAGEID, photo.getImageID());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PHOTO, null, values);
    }

    public int updatePhoto(int id, PhotoHome photo){
        //La mise à jour d'un photo dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel photo on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_DESCRIPTION,photo.getDescription());
        values.put(COL_IMAGEID, photo.getImageID());
        return bdd.update(TABLE_PHOTO, values, COL_ID + " = " +id, null);
    }

    public int removePhotoWithID(int id){
        //Suppression d'un photo de la BDD grâce à l'ID
        return bdd.delete(TABLE_PHOTO, COL_ID + " = " +id, null);
    }

    public PhotoHome getPhotoWithDescription(String description){
        //Récupère dans un Cursor les valeurs correspondant à un photo contenu dans la BDD (ici on sélectionne le photo grâce à son titre)
        Cursor c = bdd.query(TABLE_PHOTO, new String[] {COL_ID, COL_DESCRIPTION, COL_IMAGEID}, COL_DESCRIPTION + " LIKE \"" + description +"\"", null, null, null, null);
        return cursorToPhoto(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private PhotoHome cursorToPhoto(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        PhotoHome photo = new PhotoHome();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        photo.setId(c.getInt(NUM_COL_ID));
        photo.setDescription(c.getString(NUM_COL_DESCRIPTION));
        photo.setImageID(c.getBlob(NUM_COL_IMAGEID));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return photo;
    }
}
