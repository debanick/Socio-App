package com.example.socio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.*;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity {


    RecyclerView mfirestoreList;
    int count=0;
    FirebaseFirestore firebaseFirestore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        firebaseFirestore =FirebaseFirestore.getInstance();
        mfirestoreList=findViewById(R.id.firestore_list);

        Query query=firebaseFirestore.collection("events");
        FirestoreRecyclerOptions<com.example.socio.ProductsModel> options = new FirestoreRecyclerOptions.Builder<com.example.socio.ProductsModel>()
                .setQuery(query, com.example.socio.ProductsModel.class)
                .build();


        FirestoreRecyclerAdapter adapter= new FirestoreRecyclerAdapter<com.example.socio.ProductsModel,ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from((parent.getContext())).inflate(R.layout.card,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull com.example.socio.ProductsModel model) {
                holder.evntname.setText(model.getEvnt());
                holder.timedt.setText(model.getTime());
                holder.locname.setText(model.getLoc());
                holder.Descript.setText(model.getDet());
                holder.date.setText(model.getDate());
                holder.mobile.setText(model.getPhn());



            }
        };
        mfirestoreList.setHasFixedSize(true);
        mfirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mfirestoreList.setAdapter(adapter );




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:startActivity(new Intent(getApplicationContext()
                            , com.example.socio.HomeActivity.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext()
                                , EventActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext()
                                , ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        CarouselView carousel = (CarouselView) findViewById(R.id.carousel);
        carousel.setPageCount(4);
        carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.car8);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.car6);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.car7);
                        break;
                    default:
                        imageView.setImageResource(R.drawable.car9);
                        break;
                }
            }


        });
        super.onStart();
        adapter.startListening();




    }


    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView evntname;
        TextView locname;
        TextView timedt;
        TextView Descript;
        TextView date;
        TextView mobile;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            evntname=itemView.findViewById(R.id.evntname);
            locname=itemView.findViewById(R.id.locname);
            timedt=itemView.findViewById(R.id.timedt);
            Descript=itemView.findViewById(R.id.Descript);
            date=itemView.findViewById(R.id.date);
            mobile=itemView.findViewById(R.id.mobile);
        }
    }




}

