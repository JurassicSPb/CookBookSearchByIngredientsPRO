package com.ggl.jr.cookbooksearchbyingredientsPRO;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ggl.jr.cookbooksearchbyingredientsPRO.storage.IngredientDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Мария on 13.12.2016.
 */

public class RecipeDetailActivity extends AppCompatActivity {
    private IngredientDatabase ingredientDB;
    private List<Favorites> favorites;
    private int id;
    private String names;
    private String ingredients;
    private String descriptions;
    private String calories;
    private String image;
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.receipe_detail);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            id = savedInstanceState.getInt("id");
            names = savedInstanceState.getString("names");
            ingredients = savedInstanceState.getString("ingredients");
            descriptions = savedInstanceState.getString("descriptions");
            calories = savedInstanceState.getString("calories");
            image = savedInstanceState.getString("image");
            category = savedInstanceState.getString("category");
        }

        ingredientDB = new IngredientDatabase();

        Intent intent = getIntent();

        Typeface typefaceIngredientDescription = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface typefaceCalorieAndIngredient = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");

        id = intent.getIntExtra("id", 0);

        names = intent.getStringExtra("name");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Metrics.smallestWidth() >= 600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(names);

        favorites = ingredientDB.getFavorite(id);

        ImageView largeImage = (ImageView) findViewById(R.id.large_image);
        image = intent.getStringExtra("photo");
        Picasso.with(this)
                .load(image)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timeleft128)
                .error(R.drawable.noconnection128)
                .into(largeImage);

        TextView ingredient = (TextView) findViewById(R.id.ingredients_field);
        ingredients = intent.getStringExtra("ingredients");
        ingredient.setTypeface(typefaceCalorieAndIngredient);
        final Spannable text = new SpannableString(ingredients);
        for (int i = 0; i < SelectedIngredient.getSelectedIngredient().size(); i++) {
            if (ingredients.contains(SelectedIngredient.getSelectedIngredient().get(i))) {
                int position = ingredients.indexOf(SelectedIngredient.getSelectedIngredient().get(i));
                text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.Green)), position, position + SelectedIngredient.getSelectedIngredient().get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        ingredient.setText(text);

        TextView description = (TextView) findViewById(R.id.description_field);
        descriptions = intent.getStringExtra("description");
        description.setText(descriptions);
        description.setTypeface(typefaceIngredientDescription);

        TextView calorie = (TextView) findViewById(R.id.calories_field);
        calories = intent.getStringExtra("calories");
        calorie.setText(calories);
        calorie.setTypeface(typefaceCalorieAndIngredient);

        category = intent.getStringExtra("category");

        Button shoppingCart = (Button) findViewById(R.id.add_to_cart);
        shoppingCart.setOnClickListener(v -> {
            List<IngredientToBuy> ingredientToBuy = ingredientDB.getAllIngrToBuy();
            if (ingredientToBuy.size() < 50) {
                RealmList<IngredientsFromRecipe> ingredientsFromRecipes = new RealmList<>();
                String[] parts = ingredients.split("\n");
                for (String part : parts) {
                    if (!part.equals("")) {
                        ingredientsFromRecipes.add(new IngredientsFromRecipe(id, part, 0));
                    }
                }

                IngredientToBuy ingredientFromCart = new IngredientToBuy(names, id, ingredientsFromRecipes);
                ingredientDB.copyIngredientToCart(ingredientFromCart, id);

                Toast toast = Toast.makeText(this, R.string.toast_shopping_cart, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplication(), R.string.no_more_then_50_records, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (Metrics.smallestWidth() >= 600) {
            inflater.inflate(R.menu.toolbar_buttons_third_activity_tablets, menu);
        } else {
            inflater.inflate(R.menu.toolbar_buttons_third_activity_phones, menu);
        }

        MenuItem item = menu.findItem(R.id.item4);

        if (favorites.size() == 1) {
            if (Metrics.smallestWidth() >= 600) {
                item.setIcon(R.drawable.ic_favorites_selected_tablets);
            } else {
                item.setIcon(R.drawable.ic_favorites_selected_phones);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast;
        Drawable myDrawable;
        switch (item.getItemId()) {
            case R.id.item4:
                if (favorites.size() == 0) {
                    if (Metrics.smallestWidth() >= 600) {
                        myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favorites_selected_tablets);
                        item.setIcon(myDrawable);
                    } else {
                        myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favorites_selected_phones);
                        item.setIcon(myDrawable);
                    }

                    Favorites newFavorites = new Favorites(id, names, ingredients, category, descriptions, calories, image);
                    ingredientDB.copyOrUpdateFavorites(newFavorites);

                    toast = Toast.makeText(this, R.string.toast_favorites, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (favorites.size() == 1) {
                    ingredientDB.deleteFavoritePosition(id);

                    if (Metrics.smallestWidth() >= 600) {
                        myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favourites_tablets);
                        item.setIcon(myDrawable);
                    } else {
                        myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favourites_phones);
                        item.setIcon(myDrawable);
                    }

                    toast = Toast.makeText(this, R.string.toast_favorites_remove, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.item5:
                Intent sendIntent = new Intent();
                StringBuilder sendBuilder = new StringBuilder();
                sendBuilder.append(names).append("\n\n")
                        .append(getResources().getString(R.string.divider)).append("\n\n")
                        .append(ingredients).append("\n\n")
                        .append(getResources().getString(R.string.divider)).append("\n\n")
                        .append(calories).append("\n\n")
                        .append(getResources().getString(R.string.divider)).append("\n\n")
                        .append(descriptions).append("\n\n")
                        .append(getResources().getString(R.string.divider)).append("\n\n")
                        .append(getResources().getString(R.string.more_recipes));
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendBuilder.toString());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_recipe)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putInt("id", id);
        outState.putString("names", names);
        outState.putString("ingredients", ingredients);
        outState.putString("descriptions", descriptions);
        outState.putString("calories", calories);
        outState.putString("image", image);
        outState.putString("category", category);
    }

    @Override
    protected void onDestroy() {
        ingredientDB.close();
        super.onDestroy();
    }
}