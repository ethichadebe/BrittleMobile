package com.ethichadebe.brittlefinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ethichadebe.brittlefinal.adapters.ShopItemAdapter;
import com.ethichadebe.brittlefinal.adapters.ShopSelectionAdapter;
import com.ethichadebe.brittlefinal.local.model.Shop;
import com.ethichadebe.brittlefinal.viewmodel.ShopSelectionViewModel;
import com.ethichadebe.brittlefinal.viewmodel.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

public class CircularShopActivity extends AppCompatActivity {
    private static final String TAG = "CircularShopActivity";

    private FrameLayout root_layout;

    private List<Shop> shops = new ArrayList<>();
    private ShopSelectionViewModel shopViewModel;
    private ShopSelectionAdapter shopItemAdapter;
    private RecyclerView rvShops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_circular_shop);

        root_layout = findViewById(R.id.root_layout);
        rvShops = findViewById(R.id.rvShops);

        setupAnimation(savedInstanceState);

        setupShops();
    }

    private void setupAnimation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            root_layout.setVisibility(View.INVISIBLE);
            root_layout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = root_layout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            root_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            root_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }

    }

    private void circularRevealActivity() {

        int cx = root_layout.getWidth();
        int cy = root_layout.getHeight();

        float finalRadius = Math.max(root_layout.getWidth(), root_layout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(root_layout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(500);

        // make the view visible and start the animation
        root_layout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    private void setupShops() {
        rvShops.setLayoutManager(new GridLayoutManager(this, 2));
        rvShops.setHasFixedSize(true);
        shopItemAdapter = new ShopSelectionAdapter();
        rvShops.setAdapter(shopItemAdapter);


        shopViewModel = new ViewModelProvider(this).get(ShopSelectionViewModel.class);
        shopViewModel.getShops().observe(this, shops -> {
            Log.d(TAG, "setupShops: done");
            shopItemAdapter.setShopAdapter(CircularShopActivity.this, shops);
            shopItemAdapter.notifyDataSetChanged();
        });

    }

    public void back(View view) {
        finish();
    }
}