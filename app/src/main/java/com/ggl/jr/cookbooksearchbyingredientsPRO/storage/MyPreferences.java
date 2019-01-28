package com.ggl.jr.cookbooksearchbyingredientsPRO.storage;

/**
 * Created by Мария on 03.12.2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String KEY_REALM_FLAG = "key_realm_flag";
    private static final String KEY_FLAG_ALERT = "key_flag_alert";
    private static final String KEY_FLAG_RATING = "key_flag_rating";
    private static final String KEY_BUFFER_INGR = "ingrBuffer";
    private static final String KEY_BUFFER_IMAGE = "imageBuffer";
    private static final String KEY_BUFFER_FLAG = "flagBuffer";
    private static final String KEY_RECIPES_V_1_1 = "key_recipes_v_1_1";
    private static final String KEY_RECIPES_V_1_3 = "key_recipes_v_1_3";
    private static final String KEY_RECIPES_V_1_4 = "key_recipes_v_1_4";
    private static final String KEY_RECIPES_V_1_5 = "key_recipes_v_1_5";
    private static final String KEY_RECIPES_V_1_6 = "key_recipes_v_1_6";
    private static final String KEY_RECIPES_V_1_7 = "key_recipes_v_1_7";
    private static final String KEY_RECIPES_V_1_8 = "key_recipes_v_1_8";
    private static final String KEY_RECIPES_V_1_9 = "key_recipes_v_1_9";
    private static final String KEY_RECIPES_V_2_0 = "key_recipes_v_2_0";
    private static final String KEY_RECIPES_V_2_1 = "key_recipes_v_2_1";
    private static final String KEY_RECIPES_V_2_2 = "key_recipes_v_2_2";
    private static final String KEY_RECIPES_V_2_3 = "key_recipes_v_2_3";
    private static final String KEY_RECIPES_V_2_4 = "key_recipes_v_2_4";
    private static final String KEY_RECIPES_V_2_5 = "key_recipes_v_2_5";
    private static final String KEY_RECIPES_V_2_6 = "key_recipes_v_2_6";
    private static final String KEY_RECIPES_V_2_7 = "key_recipes_v_2_7";

    private static final String KEY_RECIPES_FAV_V_2_8 = "key_recipes_fav_v_2_8";
    private static final String KEY_INGR_AND_CAT_V_2_8 = "key_ingr_and_cat_v_2_8";
    private static final String KEY_INGR_FAV_V_2_8 = "key_ingr_fav_v_2_8";
    private static final String KEY_STOP_LIST_V_2_8 = "key_recipes_stop_v_2_8";
    private static final String KEY_RECIPES_V_2_8 = "key_recipes_v_2_8";

    private SharedPreferences preferences;

    public MyPreferences(Context context) {
        preferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
    }

    public boolean getFlag() {
        return preferences.getBoolean(KEY_REALM_FLAG, true);
    }

    public void setFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_REALM_FLAG, flag)
                .apply();
    }

    public void clearFlag() {
        preferences.edit().remove(KEY_REALM_FLAG).apply();
    }

    public boolean getFlagAlert() {
        return preferences.getBoolean(KEY_FLAG_ALERT, true);
    }

    public void setFlagAlert(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_ALERT, flag)
                .apply();
    }

    public boolean getFlagRating() {
        return preferences.getBoolean(KEY_FLAG_RATING, true);
    }

    public void setFlagRating(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_RATING, flag)
                .apply();
    }

    public void clearPrefs() {
        preferences.edit().clear().apply();
    }

    public void setBufferedImage(String images) {
        preferences.edit()
                .putString(KEY_BUFFER_IMAGE, images)
                .apply();
    }

    public String getBufferedIngredients() {
        return preferences.getString(KEY_BUFFER_INGR, "");
    }

    public void setBufferedIngredients(String ingredients) {
        preferences.edit()
                .putString(KEY_BUFFER_INGR, ingredients)
                .apply();
    }

    public String getBufferedImages() {
        return preferences.getString(KEY_BUFFER_IMAGE, "0");
    }

    public boolean getBufferedFlag() {
        return preferences.getBoolean(KEY_BUFFER_FLAG, true);
    }

    public void setBufferedFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_BUFFER_FLAG, flag)
                .apply();
    }

    public void clearBufferedIngredients() {
        preferences.edit().remove(KEY_BUFFER_INGR).apply();
    }

    public void clearBufferedImage() {
        preferences.edit().remove(KEY_BUFFER_IMAGE).apply();
    }

    public boolean getFlagRecipesV1_1() {
        return preferences.getBoolean(KEY_RECIPES_V_1_1, true);
    }

    public void setFlagRecipesV1_1(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_1, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_3() {
        return preferences.getBoolean(KEY_RECIPES_V_1_3, true);
    }

    public void setFlagRecipesV1_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_3, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_4() {
        return preferences.getBoolean(KEY_RECIPES_V_1_4, true);
    }

    public void setFlagRecipesV1_4(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_4, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_5() {
        return preferences.getBoolean(KEY_RECIPES_V_1_5, true);
    }

    public void setFlagRecipesV1_5(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_5, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_6() {
        return preferences.getBoolean(KEY_RECIPES_V_1_6, true);
    }

    public void setFlagRecipesV1_6(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_6, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_7() {
        return preferences.getBoolean(KEY_RECIPES_V_1_7, true);
    }

    public void setFlagRecipesV1_7(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_7, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_8() {
        return preferences.getBoolean(KEY_RECIPES_V_1_8, true);
    }

    public void setFlagRecipesV1_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_8, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_9() {
        return preferences.getBoolean(KEY_RECIPES_V_1_9, true);
    }

    public void setFlagRecipesV1_9(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_9, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_0() {
        return preferences.getBoolean(KEY_RECIPES_V_2_0, true);
    }

    public void setFlagRecipesV2_0(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_0, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_1() {
        return preferences.getBoolean(KEY_RECIPES_V_2_1, true);
    }

    public void setFlagRecipesV2_1(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_1, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_2() {
        return preferences.getBoolean(KEY_RECIPES_V_2_2, true);
    }

    public void setFlagRecipesV2_2(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_2, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_3() {
        return preferences.getBoolean(KEY_RECIPES_V_2_3, true);
    }

    public void setFlagRecipesV2_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_3, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_4() {
        return preferences.getBoolean(KEY_RECIPES_V_2_4, true);
    }

    public void setFlagRecipesV2_4(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_4, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_5() {
        return preferences.getBoolean(KEY_RECIPES_V_2_5, true);
    }

    public void setFlagRecipesV2_5(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_5, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_6() {
        return preferences.getBoolean(KEY_RECIPES_V_2_6, true);
    }

    public void setFlagRecipesV2_6(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_6, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_7() {
        return preferences.getBoolean(KEY_RECIPES_V_2_7, true);
    }

    public void setFlagRecipesV2_7(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_7, flag)
                .apply();
    }

    public boolean getFlagRecipesFavV2_8() {
        return preferences.getBoolean(KEY_RECIPES_FAV_V_2_8, true);
    }

    public void setFlagRecipesFavV2_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_FAV_V_2_8, flag)
                .apply();
    }

    public boolean getFlagIngrFavV2_8() {
        return preferences.getBoolean(KEY_INGR_FAV_V_2_8, true);
    }

    public void setFlagIngrFavV2_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_FAV_V_2_8, flag)
                .apply();
    }

    public boolean getFlagIngrCatV2_8() {
        return preferences.getBoolean(KEY_INGR_AND_CAT_V_2_8, true);
    }

    public void setFlagIngrCatV2_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_AND_CAT_V_2_8, flag)
                .apply();
    }

    public boolean getFlagStopListV2_8() {
        return preferences.getBoolean(KEY_STOP_LIST_V_2_8, true);
    }

    public void setFlagStopListV2_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_STOP_LIST_V_2_8, flag)
                .apply();
    }

    public boolean getFlagRecipesV2_8() {
        return preferences.getBoolean(KEY_RECIPES_V_2_8, true);
    }

    public void setFlagRecipesV2_8(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_2_8, flag)
                .apply();
    }
}
