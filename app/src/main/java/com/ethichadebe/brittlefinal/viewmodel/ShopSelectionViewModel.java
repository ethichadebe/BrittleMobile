package com.ethichadebe.brittlefinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ethichadebe.brittlefinal.local.model.Shop;
import com.ethichadebe.brittlefinal.repository.PriceCheckRepo;

import java.util.List;

public class ShopSelectionViewModel extends AndroidViewModel {
    private static final String TAG = "ShopViewModel";
    private PriceCheckRepo repository;
    private LiveData<List<Shop>> shops;


    public ShopSelectionViewModel(@NonNull Application application) {
        super(application);
        repository = new PriceCheckRepo(application);
        shops = repository.getShops();
    }

    public void insert(Shop shop){
        repository.insertShop(shop);
    }

    public void update(Shop shop){
        repository.updateShop(shop);
    }

    public void delete(Shop shop){
        repository.deleteShop(shop);
    }
    public void deleteAllItems(){
        repository.deleteAllShops();
    }

    public LiveData<List<Shop>> getShops(){
        return shops;
    }
}
