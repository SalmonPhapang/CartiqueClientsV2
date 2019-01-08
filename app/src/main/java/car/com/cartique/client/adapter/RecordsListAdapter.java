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

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import car.com.cartique.client.R;
import car.com.cartique.client.RecordOrderDetails;
import car.com.cartique.client.app.AppController;
import car.com.cartique.client.model.Order;

public class RecordsListAdapter extends RecyclerView.Adapter<RecordsListAdapter.ViewHolder> implements Filterable {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private List<Order> orderItems;
    private ViewHolder holder;
    private List<Order> orderListFiltered;
    private View v;

    public RecordsListAdapter(Activity activity, List<Order> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            final Order item = orderItems.get(position);
            holder.clientName.setText(item.getClientName());
            holder.orderNumber.setText(item.getOrderNumber());
            holder.status.setText(item.getOrderStatus().toString());
            holder.orderDate.setText(item.getOrderDate().toString() +"  "+ item.getOrderType().toString());

            holder.clientName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity.getApplicationContext(), RecordOrderDetails.class);
                    intent.putExtra("Order", item);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    orderListFiltered = orderItems;
                } else {
                    List<Order> filteredList = new ArrayList<>();
                    for (Order row : orderItems) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or city number match
                        if (row.getUserName().toLowerCase().contains(charString.toLowerCase()) || row.getOrderNumber().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    orderListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = orderListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                orderListFiltered = (ArrayList<Order>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void insert(int position, Order data) {
        orderItems.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView orderNumber;
        TextView status;
        TextView orderDate;

        ViewHolder(View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.txtName);
            orderNumber = itemView.findViewById(R.id.txtOrderNumber);
            status = itemView.findViewById(R.id.txtstatus);
            orderDate= itemView.findViewById(R.id.txtDateAndOrderType);
        }

    }
}

