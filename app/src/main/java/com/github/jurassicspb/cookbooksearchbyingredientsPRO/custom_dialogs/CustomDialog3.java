package com.github.jurassicspb.cookbooksearchbyingredientsPRO.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.github.jurassicspb.cookbooksearchbyingredientsPRO.R;

/**
 * Created by Мария on 14.03.2017.
 */

public class CustomDialog3 extends Dialog {
    private Button btnClose;
    private Activity activity;


    public CustomDialog3(Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_3);

        setCancelable(false);

        btnClose = (Button) findViewById(R.id.close_button);
        btnClose.setOnClickListener(v -> {
            dismiss();
            new CustomDialog4(activity).show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
        new CustomDialog4(activity).show();
    }
}
