package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adib.androideatit.R;

import Interface.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress;
    public Button btnOrderBill;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(@NonNull View itemView) {
        super( itemView );

        txtOrderAddress = itemView.findViewById( R.id.order_address );
        txtOrderId = itemView.findViewById( R.id.order_id );
        txtOrderStatus = itemView.findViewById( R.id.order_status );
        txtOrderPhone = itemView.findViewById( R.id.order_phone );
        btnOrderBill = itemView.findViewById( R.id.order_bill );


        itemView.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false );
    }
}
