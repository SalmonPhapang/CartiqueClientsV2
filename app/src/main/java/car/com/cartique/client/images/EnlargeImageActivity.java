package car.com.cartique.client.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import car.com.cartique.client.R;

public class EnlargeImageActivity extends AppCompatActivity {

    private ImageView enlargedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_image);
        enlargedImage = findViewById(R.id.android_enlarged_image);
        Intent intent = getIntent();
        byte[] bytes =  intent.getByteArrayExtra("enlarged_Image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        enlargedImage.setImageBitmap(Bitmap.createScaledBitmap(bmp,  enlargedImage.getWidth(),
                enlargedImage.getHeight(), false));
    }
}
