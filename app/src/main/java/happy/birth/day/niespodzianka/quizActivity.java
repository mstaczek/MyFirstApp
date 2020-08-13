package happy.birth.day.niespodzianka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class quizActivity extends AppCompatActivity {
    TextView questionText;
    TextView[] Solutions = new TextView[10];
    Button ansA, ansB, ansC;
    Integer questionNum;
    Resources res;
    String[] questions;
    String[] answers;
    String[] solutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setTitle(R.string.quizTitle);

        prepare();

        next(); // prints first questions etc, see below
    }

    // loads Strings, finds buttons and text views
    private void prepare(){
        res = getResources();
        questions = res.getStringArray(R.array.questions);
        answers = res.getStringArray(R.array.answers);
        solutions = res.getStringArray(R.array.solution);

        Solutions[1] = findViewById(R.id.a1);
        Solutions[2] = findViewById(R.id.a2);
        Solutions[3] = findViewById(R.id.a3);
        Solutions[4] = findViewById(R.id.a4);
        Solutions[5] = findViewById(R.id.a5);
        Solutions[6] = findViewById(R.id.a6);
        Solutions[7] = findViewById(R.id.a7);
        Solutions[8] = findViewById(R.id.a8);
        Solutions[9] = findViewById(R.id.a9);
        questionText = findViewById(R.id.textViewQuestion);
        ansA = findViewById(R.id.button);
        ansB = findViewById(R.id.button2);
        ansC = findViewById(R.id.button3);

        questionNum = 1;
    }

    // updates buttons and all text
    private void next() {

        questionText.setText(questions[questionNum - 1]);

        int a = (questionNum - 1) * 3;
        ansA.setText(answers[a]);
        ansB.setText(answers[a + 1]);
        ansC.setText(answers[a + 2]);

        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Solutions[questionNum].setText(solutions[questionNum - 1]);
                questionNum++;
                if(questionNum <= 9){
                    next();
                }
                else{
                    end();
                }
            }
        });
    }

    // end of quiz
    private void end(){
        getSupportActionBar().setTitle(R.string.quizTitleEnd);

        questionText.setText(R.string.quiz_end);
        ansA.setText(R.string.quiz_back);
        ansB.setText(R.string.quiz_back);
        ansC.setText(R.string.quiz_back);

        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", true);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        ansB.setVisibility(View.GONE);
        ansC.setVisibility(View.GONE);
    }


}