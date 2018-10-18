package car.com.cartique.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import car.com.cartique.client.R;
import car.com.cartique.client.app.CircularNetworkImageView;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Quote;

public class ClientSearchAdapter extends RecyclerView.Adapter<ClientSearchAdapter.ViewHolder> implements Filterable {
    private Activity activity;
    private List<Client> feedItems;
    private View v;
    private List<Client> clientListFiltered;

    public ClientSearchAdapter(Activity activity, List<Client> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
        this.clientListFiltered = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_row_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Client item = clientListFiltered.get(position);
        holder.feedname.setText(item.getName());
        holder.location.setText(item.getAddress());
        // user profile pic
        if (item.getImageUrl() != null) {
            //StorageReference httpsReference = storage.getReferenceFromUrl(item.getImageUrl());
            Picasso.with(activity.getApplicationContext())
                    .load(item.getImageUrl())
                    .fit()
                    .transform(new CircularNetworkImageView(activity.getApplicationContext()))
                    .into(holder.profilePic);
        } else {
            Picasso.with(activity.getApplicationContext())
                    .load(R.drawable.ic_menu_user)
                    .fit()
                    .transform(new CircularNetworkImageView(activity.getApplicationContext()))
                    .into(holder.profilePic);
        }

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getApplicationContext(), Quote.class);
                intent.putExtra("clientObj", item);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    clientListFiltered = feedItems;
                } else {
                    List<Client> filteredList = new ArrayList<>();
                    for (Client row : feedItems) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or city number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getCity().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    clientListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = clientListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clientListFiltered = (ArrayList<Client>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return clientListFiltered.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Client data) {
        clientListFiltered.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircularNetworkImageView profilePic;
        TextView feedname;
        TextView location;

        ViewHolder(View itemView) {
            super(itemView);

            feedname = itemView.findViewById(R.id.clientName);
            profilePic = itemView
                    .findViewById(R.id.clientImage);
            location = itemView.findViewById(R.id.clientLocation);

        }

    }
}

