package car.com.cartique.client.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import car.com.cartique.client.R;
import car.com.cartique.client.images.EnlargeImageActivity;
import car.com.cartique.client.model.GridMenu;

public class CustomImagesAdapter extends BaseAdapter {
    public ArrayList<GridMenu> menuItemList;
    public Context context;

    public CustomImagesAdapter(ArrayList<GridMenu> apps, Context context) {
        this.menuItemList = apps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        CustomImagesAdapter.ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.images_gridview_item, parent, false);
            // configure view holder
            viewHolder = new CustomImagesAdapter.ViewHolder();
            viewHolder.menuIcon = rowView.findViewById(R.id.android_gridview_image);
            viewHolder.title = rowView.findViewById(R.id.android_gridview_text);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (CustomImagesAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(menuItemList.get(position).getTitle() + "");
        if (menuItemList.get(position).getBytes().length != 0){
            Bitmap bmp = BitmapFactory.decodeByteArray(menuItemList.get(position).getBytes(), 0, menuItemList.get(position).getBytes().length);
            viewHolder.menuIcon.setImageBitmap(bmp);
            viewHolder.menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent enlargeImageIntent = new Intent(context,EnlargeImageActivity.class);
                    enlargeImageIntent.putExtra("enlarged_Image",menuItemList.get(position).getBytes());
                    enlargeImageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(enlargeImageIntent);

                }
            });
        }
        return rowView;
    }

    public class ViewHolder {
        TextView title;
        ImageView menuIcon;

    }
}
