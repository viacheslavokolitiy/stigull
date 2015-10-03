package org.satorysoft.stigull.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import org.satorysoft.stigull.GradientProgressView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout included = (RelativeLayout) findViewById(R.id.content_included);
        GradientProgressView gradientProgressView = (GradientProgressView) included.findViewById(R.id.gradient_progress);
        gradientProgressView.setStartColor(ContextCompat.getColor(this, R.color.material_white));
        gradientProgressView.setCenterColor(ContextCompat.getColor(this, R.color.app_gradient_center_color));
        gradientProgressView.setEndColor(ContextCompat.getColor(this, R.color.app_gradient_end_color));
        gradientProgressView.setUnfinishedColor(ContextCompat.getColor(this,R.color.stigull_grey_500));
        gradientProgressView.setProgress(75);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
