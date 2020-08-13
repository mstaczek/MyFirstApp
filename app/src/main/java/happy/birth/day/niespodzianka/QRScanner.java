package happy.birth.day.niespodzianka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class QRScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Boolean scannedStatus;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        getSupportActionBar().setTitle(R.string.qrTitleMsg);
        scannedStatus = false;

        scannView = findViewById(R.id.scannerView);
        scannView.setFlashButtonVisible(false);
        scannView.setAutoFocusButtonVisible(false);

        codeScanner = new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        backBtn = findViewById(R.id.backQRBtn);

        backBtn.setOnClickListener(new View.OnClickListener() { // just goes back
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                codeScanner.releaseResources();
                finish();
            }
        });



        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(R.string.scanned);
                        scannedStatus = true;
                        Log.d("____Scanned:", "yay");
                        backBtn.setOnClickListener(new View.OnClickListener() { // returns message
                            @Override
                            public void onClick(View view) {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", result.getText());
                                setResult(Activity.RESULT_OK, returnIntent);
                                codeScanner.releaseResources();
                                finish();
                            }
                        });
                    }
                });
            }
        });


        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scannedStatus){
                    Toast.makeText(getApplicationContext(),R.string.scanned, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.scanning, Toast.LENGTH_SHORT).show();
                }
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        codeScanner.stopPreview();
        super.onPause();
    }

    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QRScanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


}
