package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adib.androideatit.R;

import Interface.ItemClickListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super( itemView );

        txtMenuName = itemView.findViewById( R.id.menu_name );
        imageView = itemView.findViewById( R.id.menu_image );

        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick( v,getAdapterPosition(),false );
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
