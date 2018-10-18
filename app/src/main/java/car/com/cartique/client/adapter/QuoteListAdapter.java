package car.com.cartique.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import java.util.List;
import car.com.cartique.client.QuoteDetailsActivity;
import car.com.cartique.client.R;
import car.com.cartique.client.app.AppController;
import car.com.cartique.client.model.Order;

public class QuoteListAdapter extends RecyclerView.Adapter<QuoteListAdapter.ViewHolder> {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private List<Order> orderItems;
    private ViewHolder holder;

    private View v;

    public QuoteListAdapter(Activity activity, List<Order> orderItems) {
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
                    Intent intent = new Intent(activity.getApplicationContext(), QuoteDetailsActivity.class);
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

