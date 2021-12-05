package com.doaa.doaaidris_tutorial6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dCanvas;
    SeekBar strokeWidth;

    Button clearButton;
    Button undoButton;
    Button eraserButton;
    Button lineButton;

    View colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dCanvas = findViewById(R.id.myCanvas);

        strokeWidth = findViewById(R.id.seekBar);

        clearButton = findViewById(R.id.clearBtn);
        undoButton = findViewById(R.id.undoBtn);
        eraserButton = findViewById(R.id.eraserBtn);
        lineButton = findViewById(R.id.lineBtn);

        colorPicker = findViewById(R.id.colorPicker);

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

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = MainActivity.this;
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose color")
                        .initialColor(Color.parseColor("#FFFFFF"))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                Toast.makeText(MainActivity.this,"onColorSelected: 0x" + Integer.toHexString(selectedColor),Toast.LENGTH_SHORT).show();
                                colorPicker.setBackgroundColor(selectedColor);

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                colorPicker.setBackgroundColor(selectedColor);
                                dCanvas.pathColour = selectedColor ;
                                dCanvas.mode = 0;
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        eraserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dCanvas.mode = 1;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dCanvas.clearCanvas();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dCanvas.undoCanvas();
            }
        });

        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dCanvas.mode = 2;
            }
        });

    }


}