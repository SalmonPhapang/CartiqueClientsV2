package car.com.cartique.client.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import car.com.cartique.client.R;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.custom.CustomMenuAdapter;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.GridMenu;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GridView gridView;
    private TextView txtUsername;
    private CustomMenuAdapter menuAppAdapter;
    private ArrayList<GridMenu> gridMenuArrayList;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gridMenuArrayList = new ArrayList<>();
        gridMenuArrayList.add(new GridMenu("Service", R.mipmap.ic_menu_repair_circular));
        gridMenuArrayList.add(new GridMenu("Paint", R.mipmap.ic_menu_paint_circular));
        gridMenuArrayList.add(new GridMenu("Profile", R.mipmap.ic_menu_user_circular));
        gridMenuArrayList.add(new GridMenu("Calender", R.mipmap.ic_calender_circular));
        gridMenuArrayList.add(new GridMenu("About", R.mipmap.ic_menu_help_circular));
        gridMenuArrayList.add(new GridMenu("Search", R.mipmap.ic_menu_search_circular));

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = v.findViewById(R.id.grid_view_image_text);
        txtUsername = v.findViewById(R.id.userName);
        txtUsername.setText("Logged-in as : "+getUserObjInPref().getName());
        menuAppAdapter = new CustomMenuAdapter(gridMenuArrayList, getActivity());
        gridView.setAdapter(menuAppAdapter);

        return v;
    }
    private Client getUserObjInPref() {
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getContext());
        String userString = pref.getString(Config.USER_OBJECT,"");
        Gson gson = new Gson();
        return gson.fromJson(userString,Client.class);
    }
}
