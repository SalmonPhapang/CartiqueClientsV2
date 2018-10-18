package car.com.cartique.client.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import car.com.cartique.client.R;
import car.com.cartique.client.model.Quote;

public class QuoteDetailsAdapter extends RecyclerView.Adapter<QuoteDetailsAdapter.ViewHolder> {
    private Activity activity;
    private List<Quote> quoteItems;
    private ViewHolder holder;
    private static final String AMOUNT = "R0.00";

    private View v;

    public QuoteDetailsAdapter(Activity activity, List<Quote> quoteItems) {
        this.activity = activity;
        this.quoteItems = quoteItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            final Quote item = quoteItems.get(position);
            holder.clientName.setText(item.getClientName());
            if(item.getAmount().isEmpty() || item.getAmount() ==null)
                holder.amount.setText(AMOUNT);
                else
            holder.amount.setText("R"+item.getAmount());
            holder.status.setText(item.getQuoteStatus().toString());
            holder.orderDate.setText(item.getQuoteDate().toString());

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
        return quoteItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Quote data) {
        quoteItems.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView amount;
        TextView status;
        TextView orderDate;

        ViewHolder(View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.txtquoteName);
            amount = itemView.findViewById(R.id.txtquoteAmount);
            status = itemView.findViewById(R.id.txtquoteStatus);
            orderDate= itemView.findViewById(R.id.txtquoteDate);
        }

    }
}

