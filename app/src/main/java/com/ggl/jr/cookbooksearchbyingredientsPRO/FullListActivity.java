package com.ggl.jr.cookbooksearchbyingredientsPRO;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ggl.jr.cookbooksearchbyingredientsPRO.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 27.12.2016.
 */

public class FullListActivity extends AppCompatActivity {
    private FullListAdapter adapter;
    private IngredientDatabase recipeDB;
    private EditText searchEditText;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            int id = adapter.getRecipe(position).getId();
            String name = adapter.getRecipe(position).getName();
            String photo = adapter.getRecipe(position).getImage();
            String ingredients = adapter.getRecipe(position).getIngredient();
            String description = adapter.getRecipe(position).getDescription();
            String calories = adapter.getRecipe(position).getCalories();
            String category = adapter.getRecipe(position).getCategory();

            Intent intent = new Intent(FullListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("photo", photo);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("description", description);
            intent.putExtra("calories", calories);
            intent.putExtra("category", category);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_list_recyclerview);

        recipeDB = new IngredientDatabase();

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
        getSupportActionBar().setTitle(R.string.full_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FullListAdapter(performRecipesCopy(), clickListener);
        recyclerView.setAdapter(adapter);

        searchEditText = (EditText) findViewById(R.id.search);
        searchEditText.setFocusable(false);

        searchEditText.setOnTouchListener((v, event) -> {
            searchEditText.setFocusableInTouchMode(true);
            return false;
        });

        Button searchClearButton = (Button) findViewById(R.id.search_button);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchClearButton.setOnClickListener(v -> searchEditText.setText(""));
    }

    private List<RecipeFilter> performRecipesCopy() {
        List<Recipe> recipes = recipeDB.getRecipesSorted();
        List<RecipeFilter> newRecipes = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe r = recipes.get(i);
            newRecipes.add(new RecipeFilter(r.getId(), r.getName(), r.getIngredient(),
                    r.getCategory(), r.getDescription(), r.getCalories(), r.getImage()));
        }
        return newRecipes;
    }

    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
