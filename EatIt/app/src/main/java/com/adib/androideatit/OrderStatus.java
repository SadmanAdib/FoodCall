package com.adib.androideatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Common.Common;
import Model.Request;
import ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_status );

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById( R.id.listOrders );
        recyclerView.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );

        loadOrders( Common.currentUser.getPhone());



    }

    private void loadOrders(String phone) {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(Request.class,R.layout.order_layout,OrderViewHolder.class,requests.orderByChild( "phone" ).equalTo( phone )) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                orderViewHolder.txtOrderId.setText( adapter.getRef( i ).getKey() );
                orderViewHolder.txtOrderStatus.setText( convertCodeToStatus(request.getStatus()) );
                orderViewHolder.txtOrderAddress.setText( request.getAddress() );
                orderViewHolder.txtOrderPhone.setText( request.getPhone() );
                orderViewHolder.btnOrderBill.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage( "com.bKash.customerapp" );
                        if (intent!=null)
                        {
                            startActivity( intent );
                        }
                    }
                } );

            }
        };

        recyclerView.setAdapter( adapter );

    }

    private String convertCodeToStatus(String status) {

        if (status.equals( "0" ))
            return "Placed";
        else if (status.equals( "1" ))
            return "On my way";
        else
            return "Shipped";




    }
}
