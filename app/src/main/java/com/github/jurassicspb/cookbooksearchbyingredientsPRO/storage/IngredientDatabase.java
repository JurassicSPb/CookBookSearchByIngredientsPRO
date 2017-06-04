package com.github.jurassicspb.cookbooksearchbyingredientsPRO.storage;

import com.github.jurassicspb.cookbooksearchbyingredientsPRO.Categories;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.CategoryTable;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.Favorites;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.Ingredient;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.IngredientFavorites;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.IngredientToBuy;
import com.github.jurassicspb.cookbooksearchbyingredientsPRO.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientDatabase {
    private Realm realm;

    public IngredientDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("ingredient_db")
                .build();
//               Realm.deleteRealm(configuration);
        realm = Realm.getInstance(configuration);
    }

    public void copyOrUpdate(List<Ingredient> ingredient) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingredient);
        realm.commitTransaction();
    }

    public void copyOrUpdateRecipe(List<Recipe> recipe) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();
    }

    public void copyOrUpdateFavorites(Favorites favorite) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(favorite);
        realm.commitTransaction();
    }

    public void copyOrUpdateIngrFavorites(IngredientFavorites ingrFavorites) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingrFavorites);
        realm.commitTransaction();
    }

    public List<IngredientFavorites> getAllIngrFavorites() {
        realm.beginTransaction();
        RealmResults<IngredientFavorites> results = realm.where(IngredientFavorites.class).findAll();
        List<IngredientFavorites> newIngrFav = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrFav;
    }

    public List<IngredientFavorites> getAllIngrFavoritesSorted() {
        realm.beginTransaction();
        RealmResults<IngredientFavorites> results = realm.where(IngredientFavorites.class).findAllSorted("ingredient", Sort.ASCENDING);
        List<IngredientFavorites> newIngrFav = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrFav;
    }

    public void deleteIngrFavoritePosition(String name) {
        realm.beginTransaction();
        RealmResults<IngredientFavorites> results = realm.where(IngredientFavorites.class).equalTo("ingredient", name).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void copyOrUpdateCategories(List<Categories> categories) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(categories);
        realm.commitTransaction();
    }

    public List<Favorites> getAllFavorites() {
        return realm.where(Favorites.class).findAll();
    }

    public List<Favorites> getFavorite(String fav) {
        return realm.where(Favorites.class).equalTo("name", fav, Case.INSENSITIVE).findAll();
    }

    public void deleteFavoritePosition(String name) {
        realm.beginTransaction();
        RealmResults<Favorites> results = realm.where(Favorites.class).equalTo("name", name).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public List<Ingredient> copyFromRealm(List<Ingredient> ingredient) {
        realm.beginTransaction();
        List<Ingredient> newingr = realm.copyFromRealm(ingredient);
        realm.commitTransaction();
        return newingr;
    }

    public void copyOrUpdateCategoryTable(List<CategoryTable> categoryTable) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(categoryTable);
        realm.commitTransaction();
    }

    public void delete(List<Ingredient> ingredient) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void deleteRecipes(List<Recipe> recipe) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public List<Ingredient> getAll() {
        return realm.where(Ingredient.class).findAllSorted("ingredient", Sort.ASCENDING);
    }

    public List<CategoryTable> getAllCategoryTables() {
        return realm.where(CategoryTable.class).findAllSorted("num", Sort.ASCENDING);
    }

    public List<Categories> getAllCategories() {
        return realm.where(Categories.class).findAllSorted("name", Sort.ASCENDING);
    }

    public List<Ingredient> getCategory(int i) {
        return realm.where(Ingredient.class).equalTo("category", i).findAllSorted("ingredient", Sort.ASCENDING);
    }

    public List<Recipe> getRecipe(ArrayList<String> selected) {
        RealmQuery<Recipe> query = realm.where(Recipe.class);
        query.contains("ingredient", selected.get(0));
        for (int i = 1; i < selected.size(); i++) {
            query.or().contains("ingredient", selected.get(i));
        }
        return query.findAll();
    }


    public List<Recipe> getRecipesSorted() {
        String[] fieldNames = {"category", "name"};
        Sort sort[] = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(Recipe.class).findAllSorted(fieldNames, sort);
    }

    public List<Recipe> getRecipesByCategories(String name) {
        return realm.where(Recipe.class).contains("category", name).findAllSorted("name", Sort.ASCENDING);
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void copyIngredientToBuy(List<IngredientToBuy> ingrToBuy) {
        realm.beginTransaction();
        realm.copyToRealm(ingrToBuy);
        realm.commitTransaction();
    }

    public void copyIngredientToBuy(IngredientToBuy ingrToBuy) {
        realm.beginTransaction();
        realm.copyToRealm(ingrToBuy);
        realm.commitTransaction();
    }

    public void copyIngredientToBuyList(List<IngredientToBuy> ingrToBuy) {
        realm.beginTransaction();
        realm.copyToRealm(ingrToBuy);
        realm.commitTransaction();
    }

    public List<IngredientToBuy> getAllIngrToBuy() {
        return realm.where(IngredientToBuy.class).findAll();
    }

    public List<IngredientToBuy> copyIngrToBuyFromRealm() {
        realm.beginTransaction();
        RealmResults<IngredientToBuy> results = realm.where(IngredientToBuy.class).findAll();
        List<IngredientToBuy> newIngrsToBuy = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrsToBuy;
    }

    public void clearIngrToBuy() {
        realm.beginTransaction();
        RealmResults<IngredientToBuy> results = realm.where(IngredientToBuy.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void addChangeListener(RealmChangeListener<Realm> changeListener) {
        realm.addChangeListener(changeListener);
    }
}
