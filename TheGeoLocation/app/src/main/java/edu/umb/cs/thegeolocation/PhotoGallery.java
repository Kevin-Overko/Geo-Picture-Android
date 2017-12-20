package edu.umb.cs.thegeolocation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class PhotoGallery extends AppCompatActivity {

    /* UNUSED SHIT
    private int totalFiles = 0;
    private ExifInterface exif;
    private String latlng;
    private double latitude;
    private double longitude;
    private LatLng latLong;
    private Bitmap thumbnail;
    private ArrayList<PictureObject> picObjArray = new ArrayList<>();
    */
    ArrayList<File> list;
    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/saved_images/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.gallery_grid );

        File picFolder = new File(root);

        list = imageReader(picFolder);

        GridView gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ViewImage.class).putExtra("img", list.get(position).toString()) );
            }
        });

    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.grid_image, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);

            iv.setImageURI( Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }

    ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();

        File[] files = root.listFiles();
        for(int i = 0; i<files.length; i++){
            if(files[i].isDirectory()){
                a.addAll(imageReader(files[i]));
            }else {
                if(files[i].getName().endsWith(".jpg")){
                    a.add(files[i]);
                }
            }
        }
        return a;
    }

    public void mapButton(View view) {
        Intent intent = new Intent(this, MapClass.class);
        startActivity(intent);;
    }
    public void camButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("callcam","CAM");
        startActivity(intent);
    }
/*
    public void getPhotos(){
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        root = root + "/saved_images/";
        File picFolder = new File(root);
        ArrayList<File> fileArray = new ArrayList<>();
        fileArray = picFolder.listFiles();
        totalFiles = fileArray.length;

        try {

            for(int i = 0; i < fileArray.length; i++ ){
                exif = new ExifInterface(root+fileArray[i].getName());
                latlng = exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
                Log.d("ll", ""+latlng);

                // setting input variables
                latLong = new LatLng(latitude, longitude);
                thumbnail = BitmapFactory.decodeFile(root+fileArray[i].getName());

                // create picture obj and add to the array
                PictureObject newpic = new PictureObject(thumbnail, latLong);
                picObjArray.add(newpic);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_image, getData());
        gridView.setAdapter(gridAdapter);

    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
    */



}
