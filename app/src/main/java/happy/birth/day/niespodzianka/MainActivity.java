package happy.birth.day.niespodzianka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button quizBtn, scanBtn, surpriseBtn;
    CheckBox quizCheckbox, qrCheckBox;

    private int REQUEST_CODE_QUIZ = 2; // qr scanner code (whatever)
    private int REQUEST_CODE_QR = 1; // qr scanner code (whatever)
    Boolean quizPassed;
    String result;
    private static int msgHASH = 1007380140;  //hash of scanned qr


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.menuTitle);
        result = "";
        quizPassed = false;

        // Preparing checkboxes and buttons
        quizCheckbox = findViewById(R.id.checkBox1);
        qrCheckBox = findViewById(R.id.checkBox2);

        quizBtn = findViewById(R.id.quizBtn);
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), quizActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QUIZ);
            }
        });

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QRScanner.class);
                startActivityForResult(intent, REQUEST_CODE_QR);
            }
        });

        surpriseBtn = findViewById(R.id.supriseBtn);
        surpriseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SurpriseActivity.class);
                intent.putExtra("surpriseText", result);
                startActivity(intent);
            }
        });
    }

    // compare hashes/return true (accept every scanned message)
    private boolean verifyScannedCode(String string){
//        return msgHASH == string.hashCode();
        return true;
    }

    // after QR code is scanned - here some check if the right was scanned should appear soon
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QR) {
            if (resultCode == Activity.RESULT_OK) {
                result = data.getStringExtra("result");
                Log.d("________QR______:",result);
                Log.d("________QR______HASH:", String.valueOf(result.hashCode()));

                if(verifyScannedCode(result)){
                    qrCheckBox.setChecked(true);
                    if(quizCheckbox.isChecked()){
                        surpriseBtn.setVisibility(View.VISIBLE);  // show hidden button
                        Toast.makeText(getApplicationContext(), R.string.qrPassed, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.qrFailedWrongHASH, Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.qrFailed, Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CODE_QUIZ) {        // checks if quiz was finished
            if (resultCode == Activity.RESULT_OK) {
                quizPassed = data.getBooleanExtra("result", false);
                Toast.makeText(getApplicationContext(), R.string.quizPassed, Toast.LENGTH_SHORT).show();
                Log.d("________QUIZ______:", String.valueOf(quizPassed));

                quizCheckbox.setChecked(true);
                if(qrCheckBox.isChecked()){
                    surpriseBtn.setVisibility(View.VISIBLE);  // show hidden button
                    Toast.makeText(getApplicationContext(), R.string.qrPassed, Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.quizFailed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
