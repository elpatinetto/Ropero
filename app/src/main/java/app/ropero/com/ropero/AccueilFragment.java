package app.ropero.com.ropero;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import app.ropero.com.ropero.camera.PhotoBDD;
import app.ropero.com.ropero.camera.PhotoHome;

import static android.app.Activity.RESULT_OK;

/**
 * Created by noellodou on 27/07/2017.
 */

public class AccueilFragment extends Fragment {


    private static final int REQUEST_IMAGE_CAPTURE = 10;
    private final int REQUEST_IMAGE_PICKUP = 11;
    private static int REQUEST_TAKE_PHOTO = 1;
    private  String mCurrentPhotoPath;

    private ImageView mImageView;
    private Bitmap mImageBitmap;

    public AccueilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statut, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.idRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RVAdapter adapter = new RVAdapter(new PersonProvider().readPersons(getContext()));
        rv.setAdapter(adapter);




        mImageView = (ImageView) view.findViewById(R.id.imageView1);
        mImageView.setDrawingCacheEnabled(true);
       Boolean hasCamera = checkCameraHardware(getContext());
       Boolean isAllowedCamer = checkPermissionsCamera();
       Boolean isAllowedStorage = checkPermissionStorage();
       if(hasCamera){
           if(isAllowedCamer){
                   FloatingActionButton photo = (FloatingActionButton) view.findViewById(R.id.btn_camera);
                   Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                   photo.startAnimation(pulse);

                   photo.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dispatchTakePictureIntent();
                       }
                   });
                   System.out.println("Camera is allowed");
           }else{
               System.out.println("Check camera condition 1");
           }
       }else{
           System.out.println("Check camera condition");
       }

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }




/*
    private static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Date date = new Date();

        String fname = "Image-"+ date.getTime()+".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
           // SaveImage(imageBitmap);
            System.out.println("Save image or not");
            addDescription(imageBitmap,getContext());

        }
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        Toast.makeText(getContext(),"test 001",Toast.LENGTH_LONG);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Restaure the page
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }else  if (requestCode == REQUEST_IMAGE_PICKUP && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Restaure the page
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }else {
            Toast.makeText(getContext(),"Ropero doit accéder à votre appareil pour vous procurer la meilleure expérience possible",Toast.LENGTH_LONG);

        }
    }


    class RVAdapter extends RecyclerView.Adapter<PersonViewHolder>{

        private final List<Person> persons;

        RVAdapter(List<Person> persons) {
            this.persons = persons;
        }

        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_id, viewGroup, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
           // personViewHolder.personName.setText(persons.get(i).name);
            personViewHolder.description.setText(persons.get(i).age);
            if(persons.get(i).photoBitmap != null){
                personViewHolder.personPhoto.setImageBitmap(persons.get(i).photoBitmap);
            }else{
                personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return persons.size();
        }
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView label;
        TextView description;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            label = (TextView)itemView.findViewById(R.id.photo_description);
            description = (TextView)itemView.findViewById(R.id.description_content);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   System.out.println(description.getText());
                    personPhoto.setDrawingCacheEnabled(true);

                    personPhoto.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    personPhoto.layout(0, 0, personPhoto.getMeasuredWidth(), personPhoto.getMeasuredHeight());

                    personPhoto.buildDrawingCache(true);
                    Bitmap b = Bitmap.createBitmap(personPhoto.getDrawingCache());
                    mImageView.setImageBitmap(b);
                }
            });
        }
    }



    private boolean checkPermissionsCamera() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                ) {

            return true;
        } else {
            requestPermissionsCamera();
            return false;
        }
    }

    private boolean checkPermissionStorage(){
        if( ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            requestPermissionStorage();
            return false;
        }
    }
    private void requestPermissionsCamera() {
        requestPermissions(
                new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

    }
    private void requestPermissionStorage(){
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_IMAGE_PICKUP);
    }


    public void addDescription(Bitmap bitmap, final Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        final Bitmap bitmap1 = bitmap;

        LayoutInflater linf = LayoutInflater.from(context);
        final View inflator = linf.inflate(R.layout.dialog_input, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);


        alert.setTitle("Description photo");
        alert.setMessage("Message");
        alert.setView(inflator);

        final EditText edtDescri = (EditText) inflator.findViewById(R.id.description);

        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String s1=edtDescri.getText().toString();
                System.out.println("DESCRIPTION DETAILS :"+s1);
                //SaveImage(bitmap1);
                storePhotoInBdd(context,bitmap1,s1);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void storePhotoInBdd(Context c, Bitmap bitmap, String description){
        PhotoBDD photoBDD = new PhotoBDD(c);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        PhotoHome photo = new PhotoHome((int)(new Date().getTime()/10000)+1, description,data);

        //On ouvre la base de données pour écrire dedans
        photoBDD.open();
        //On insère le livre que l'on vient de créer
        photoBDD.insertPhoto(photo);
        photoBDD.close();
        mImageView.setImageBitmap(null);

        //Restaure the page
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }

    public void testApiFlight(){
        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "https://www.googleapis.com/qpxExpress/v1/trips/search?key=AIzaSyCSLskkXCb4Qa3bAviK121OCjXOFH4Ag9k";
        URL url            = null;
        try {
            url = new URL( request );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn= null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        try {
            conn.setRequestMethod( "POST" );
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try {
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
