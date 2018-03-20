package app.ropero.com.ropero.camera;

/**
 * Created by noellodou on 26/02/2018.
 */

public class PhotoHome {
    private int id;
    private String description;
    private byte[] imageID;

    public PhotoHome(){

    }
    public PhotoHome(int id, String desc, byte[] bitmap){
        this.id = id;
        this.description = desc;
        this.imageID = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageID() {
        return imageID;
    }

    public void setImageID(byte[] imageID) {
        this.imageID = imageID;
    }

    public String toString(){
        return "ID ="+this.id+"\nDescription ="+this.description+"\nImageId = "+this.imageID;
    }
}
