package edu.umb.cs.thegeolocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MapClass extends AppCompatActivity implements OnMapReadyCallback {

    private Bitmap thumbnail;
    private GoogleMap mMap;
    private ExifInterface exif;
    private String latlng;
    private double latitude;
    private double longitude;
    private int totalFiles = 0;

    private LatLng latLong;
    private ArrayList<PictureObject> picObjArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        // file name for app "/Image-8077.jpg"
        // file name for phone "/Image-4693.jpg"

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        root = root + "/saved_images/";

        // this collects the file names in the folder /saved_images
        File picFolder = new File(root);
        File fileArray[];
        fileArray = picFolder.listFiles();
        totalFiles = fileArray.length;

        //for(int i = 0; i < fileArray.length; i++ ){
        //
        //    Log.d("FolderLength", ""+fileArray[i].getName() );
        //}
        //////////////////////////////////////////////////////////

        try {

            for(int i = 0; i < fileArray.length; i++ ){
                exif = new ExifInterface(root+fileArray[i].getName());
                latlng = exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
                Log.d("ll", ""+latlng);

                //parse the latitude and longitude from the pic description
                setLatLng(latlng);

                // setting input variables
                latLong = new LatLng(latitude, longitude);
                thumbnail = BitmapFactory.decodeFile(root+fileArray[i].getName());

                // create picture obj and add to the array
                PictureObject newpic = new PictureObject(thumbnail, latLong);
                picObjArray.add(newpic);

                Log.d("FolderLength", ""+fileArray[i].getName() );
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        MapFragment mFragment=((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mFragment.getMapAsync(this);
    }

    // takes in the .jpg's ExifInterface.TAG_IMAGE_DESCRIPTION

    private void setLatLng(String stringLatLng){
        String lat = "", lng = "";
        int counter = 0;

        for(int i = 0; i < stringLatLng.length(); i++){
            char c = stringLatLng.charAt(i);
            if (c != '/') {
                lat = lat + c;
                counter++; //8
            }
            else break;
        }

        for(int j = counter+1; j < stringLatLng.length(); j++){
            char c = stringLatLng.charAt(j);
            lng = lng + c;
        }

        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap=googleMap;

        for (int i = 0; i < totalFiles; i++) {

            Marker marker = googleMap.addMarker((new MarkerOptions().position(picObjArray.get(i).getLatLng())
                    .icon(BitmapDescriptorFactory.fromBitmap(picObjArray.get(i).getBitmap()))));
        }
    }

    public void openPhotoGallery(View view) {
        Intent intent = new Intent(this, PhotoGallery.class);
        startActivity(intent);
    }
    public void camButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("callcam","CAM");
        startActivity(intent);
    }

}
