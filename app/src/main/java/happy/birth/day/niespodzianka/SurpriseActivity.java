package happy.birth.day.niespodzianka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SurpriseActivity extends AppCompatActivity {
    TextView surpriseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);
        getSupportActionBar().setTitle(R.string.niesurpriseBtnText); //title

        surpriseTextView = findViewById(R.id.textViewSurpirse);
        surpriseTextView.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        surpriseTextView.setText(intent.getStringExtra("surpriseText"));
    }
}