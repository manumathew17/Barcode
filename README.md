# Barcode decoder library with flash light and manual imput button

Barcode scanning library for Android, used [zxing-android-embedded][1].

Features:

1. The flash button is mounted inside the camera view.
2. Included manual input button inside camera view.


By default, Android SDK 24+ is required because of `zxing:core` 3.4.x.
SDK 19+ is supported with additional configuration, see [Older SDK versions](#older-sdk-versions).

## Adding aar dependency with Gradle

Add the following to your `build.gradle` file:

```groovy
// Config for SDK 24+

allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.manumathew17:Barcode:3.1.1'
}
```

## Android Permissions
```xml
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />

    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-feature android:name="android.hardware.camera.flash" />
```

The camera permission is required for barcode scanning to function. It is automatically included as
part of the library. On Android 6 it is requested at runtime when the barcode scanner is first opened.

When using BarcodeView directly (instead of via IntentIntegrator / CaptureActivity), you have to
request the permission manually before calling `BarcodeView#resume()`, otherwise the camera will
fail to open.

## Hardware Acceleration

Hardware acceleration is required since TextureView is used.

Make sure it is enabled in your manifest file:

```xml
    <application android:hardwareAccelerated="true" ... >
```
## usage
```
xml
<com.manu.scanner.ee.barcodescanner.DecoratedBarcodeView
        android:id="@+id/barcode_scanner"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
  />
```

In activity or fragment:
```java
DecoratedBarcodeView barcodeView;
barcodeView = view.findViewById(R.id.barcode_scanner);
 
//adjusting formats
Collection<BarcodeFormat> formats = Arrays.asList(
                    BarcodeFormat.CODE_39, BarcodeFormat.UPC_A,
                    BarcodeFormat.UPC_E,
                    BarcodeFormat.EAN_13,
                    BarcodeFormat.EAN_8,
                    BarcodeFormat.RSS_14,
                    BarcodeFormat.CODE_39,
                    BarcodeFormat.CODE_93,
                    BarcodeFormat.CODE_128,
                    BarcodeFormat.ITF,
                    BarcodeFormat.CODABAR,
                    BarcodeFormat.QR_CODE,
                    BarcodeFormat.DATA_MATRIX,
                    BarcodeFormat.PDF_417);
barcodeView.setDecoderFactory(new DefaultDecoderFactory(formats));
barcodeView.decodeContinuous(callback);

//barcode will start scanning
barcodeView.resume();


//beep manager
 beepManager = new BeepManager(activity);
 
 //callback on scanning 
private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            barcodeView.pauseAndWait(); // barcode stops scaning
            beepManager.playBeepSoundAndVibrate(); //produce a beep sound
            String code = result.getText();  //decoded string
            
            //add your code to process the scanned string
            

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
            
```


### To create manual input dialog
```
Java code 

barcodeView.keyboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Dialog manualInputDialog;
            manualInputDialog=new Dialog(activity);
            manualInputDialog.setContentView(R.layout.general_failure_dialog);

            TextInputLayout textInputLayout=manualInputDialog.findViewById(R.id.edit_manualInput);
            Button submit=manualInputDialog.findViewById(R.id.submit);
            
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textInputLayout.getEditText().getText().length()>0){
                        textInputLayout.setError("enter value");
                    }
                    else {
                        processScan(textInputLayout.getEditText().getText()); //enterd string
                        manualInputDialog.dismiss();
                    }
                }
            });


            manualInputDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            manualInputDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            manualInputDialog.getWindow().setGravity(Gravity.CENTER);
            manualInputDialog.show();
            }
        });

xml code
manual iput dialog layout
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="40dp"
    android:background="@drawable/dialog_bg_white" //create a drawable
    android:orientation="vertical"
    android:paddingHorizontal="10dp"
    android:paddingTop="10dp">

    <TextView

        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Manual input"
        android:textColor="@color/textBlack"
        android:textSize="18dp"
        android:textStyle="bold" />


    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/edit_manualInput"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="20dp"
        android:layout_marginVertical="10dp"
        android:hint="Enter code">

        <com.google.android.material.textfield.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="true"
            android:singleLine="true"
            android:textColor="@color/textBlack" />

    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.button.MaterialButton
        android:id="@+id/submit"
        style="@style/Widget.MaterialComponents.Button.TextButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="SUBMIT"
        android:textSize="16dp" />

</LinearLayout>


```





[1]: <https://github.com/journeyapps/zxing-android-embedded>
