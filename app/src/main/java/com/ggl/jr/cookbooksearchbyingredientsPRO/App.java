package com.ggl.jr.cookbooksearchbyingredientsPRO;

import androidx.multidex.MultiDexApplication;

import io.realm.Realm;

/**
 * Created by Мария on 04.11.2016.
 */

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
