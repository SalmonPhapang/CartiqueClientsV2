package car.com.cartique.client.custom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import car.com.cartique.client.QuotesActivity;
import car.com.cartique.client.R;
import car.com.cartique.client.RecordOrderActivity;
import car.com.cartique.client.SearchableActivity;
import car.com.cartique.client.UserProfileActivity;
import car.com.cartique.client.about.AboutActivity;
import car.com.cartique.client.calender.CalenderActivity;
import car.com.cartique.client.model.GridMenu;

public class CustomMenuAdapter extends BaseAdapter {

    public ArrayList<GridMenu> menuItemList;
    public Context context;

    public CustomMenuAdapter(ArrayList<GridMenu> apps, Context context) {
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
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.home_gridview_item, parent, false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.menuIcon = rowView.findViewById(R.id.android_gridview_image);
            viewHolder.title = rowView.findViewById(R.id.android_gridview_text);
            viewHolder.menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (menuItemList.get(position).getTitle()) {
                        case "Profile":
                            context.startActivity(new Intent(context.getApplicationContext(), UserProfileActivity.class));
                            break;
                        case "Service":
                            context.startActivity(new Intent(context.getApplicationContext(), RecordOrderActivity.class));
                            break;
                        case "Paint":
                            context.startActivity(new Intent(context.getApplicationContext(), QuotesActivity.class));
                            break;
                        case "Search":
                            context.startActivity(new Intent(context.getApplicationContext(), SearchableActivity.class));
                            break;
                        case "About":
                            context.startActivity(new Intent(context.getApplicationContext(), AboutActivity.class));
                            break;
                        case "Calender":
                            context.startActivity(new Intent(context.getApplicationContext(), CalenderActivity.class));
                            break;

                    }
                }
            });
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(menuItemList.get(position).getTitle() + "");
        Glide.with(context.getApplicationContext()).load(menuItemList.get(position).getMenuIcon()).into(viewHolder.menuIcon);

        return rowView;


    }

    public class ViewHolder {
        TextView title;
        ImageView menuIcon;

    }


}
 
