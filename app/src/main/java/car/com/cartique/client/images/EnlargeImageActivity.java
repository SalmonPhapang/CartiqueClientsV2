package car.com.cartique.client.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import car.com.cartique.client.R;

public class EnlargeImageActivity extends AppCompatActivity {

    private ImageView enlargedImage;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_image);
        enlargedImage = findViewById(R.id.android_enlarged_image);
        toolbar  = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Full Image");
        Intent intent = getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        byte[] bytes =  intent.getByteArrayExtra("enlarged_Image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        enlargedImage.setImageBitmap(bmp);
        enlargedImage.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        enlargedImage.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        enlargedImage.setAdjustViewBounds(false);
        enlargedImage.setScaleType(ImageView.ScaleType.FIT_XY);
        getSupportActionBar().hide();
    }
}
