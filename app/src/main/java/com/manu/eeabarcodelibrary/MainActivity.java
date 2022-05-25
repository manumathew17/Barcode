package com.manu.eeabarcodelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.manu.scanner.ee.barcodescanner.DecoratedBarcodeView;

public class MainActivity extends AppCompatActivity {
    DecoratedBarcodeView decoratedBarcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decoratedBarcodeView = findViewById(R.id.barcode_view);
        decoratedBarcodeView.resume();
        decoratedBarcodeView.keyboardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "use input");
            }
        });

    }
}