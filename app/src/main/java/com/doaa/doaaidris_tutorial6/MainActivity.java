package com.doaa.doaaidris_tutorial6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dCanvas;
    SeekBar strokeWidth;
    Spinner colourDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dCanvas = findViewById(R.id.myCanvas);

        strokeWidth = findViewById(R.id.seekBar);
        colourDropdown = findViewById(R.id.spinner);

        strokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                System.out.println("progress" + strokeWidth.getProgress());
                dCanvas.pathWidth = strokeWidth.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        colourDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String colour = colourDropdown.getSelectedItem().toString();
                dCanvas.pathColour = Color.parseColor(colour);
                System.out.println(colour);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


}