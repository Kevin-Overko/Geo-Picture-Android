package edu.umb.cs.thegeolocation;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kevin on 12/4/2017.
 */

public class PictureObject {

    Bitmap bitmap;
    LatLng latlng;

    public PictureObject(Bitmap bitmap, LatLng latlng){
        Log.d("created", "created");
        this.bitmap = bitmap;
        this.latlng = latlng;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
    public LatLng getLatLng(){
        return latlng;
    }
}
