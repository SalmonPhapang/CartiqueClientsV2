package car.com.cartique.client.images;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toolbar;

import com.bhargavms.dotloader.DotLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import car.com.cartique.client.R;
import car.com.cartique.client.custom.CustomImagesAdapter;
import car.com.cartique.client.model.GridMenu;

public class ViewImagesActivity extends AppCompatActivity {

    private ArrayList<GridMenu> gridMenuArrayList;
    private CustomImagesAdapter imagesAdapter;
    private GridView gridView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private android.support.v7.widget.Toolbar toolbar;
    private DotLoader bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);

        storage = FirebaseStorage.getInstance();
        Intent intent = getIntent();
        ArrayList<String> imagesList =( ArrayList<String>)intent.getStringArrayListExtra("images");
        gridView = findViewById(R.id.grid_view_image_quote);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Paint job images");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridMenuArrayList = new ArrayList<>();
        bar = findViewById(R.id.prgload);

        final long ONE_MEGABYTE = 1024 * 1024;
        for (String imagesName:imagesList) {
           final String[] titles = imagesName.split("/");
            storageReference = storage.getReferenceFromUrl("gs://cartique-1516308965713.appspot.com").child(imagesName);
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    gridMenuArrayList.add(new GridMenu(titles[titles.length-1], bytes));
                    imagesAdapter.notifyDataSetChanged();
                    bar.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            gridMenuArrayList.add(new GridMenu("Records", R.drawable.ic_launcher));
                        }
                    });
        }
        imagesAdapter = new CustomImagesAdapter(gridMenuArrayList,getApplicationContext());
        gridView.setAdapter(imagesAdapter);

    }
}
