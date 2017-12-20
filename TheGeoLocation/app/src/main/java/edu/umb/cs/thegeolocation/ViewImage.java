package edu.umb.cs.thegeolocation;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {
    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);

        String f = getIntent().getStringExtra("img");
        iv2 = (ImageView) findViewById(R.id.imageView2);
        iv2.setImageURI(Uri.parse(f));

    }

}
