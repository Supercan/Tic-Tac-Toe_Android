package cw2.com.tictactoe;

// import necessary packages
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Main activity class
public class MainActivity extends AppCompatActivity {

    private EditText textWidget1, textWidget2;
    private Button btnGame;

    public static final String PLAYER1 = "cw2.com.tictactoe.MESSAGE";
    public static final String PLAYER2 = "cw2.com.tictactoe.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textWidget1 = (EditText) findViewById(R.id.txtP1);
        textWidget2 = (EditText) findViewById(R.id.txtP2);
        btnGame = (Button) findViewById(R.id.btnPlay);
    }

    // Function to run when play game button is clicked
    public void onClick(View v) {

        Intent intent = new Intent(this, GameActivity.class);

        String msg1 = textWidget1.getText().toString();
        String msg2 = textWidget2.getText().toString();

        intent.putExtra("PLAYER1", msg1);
        intent.putExtra("PLAYER2", msg2);

        if (textWidget1.getText().toString().length() < 1 ||
                textWidget2.getText().toString().length() < 1){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter Player names", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (textWidget1.getText().toString().length() >= 10 ||
                textWidget2.getText().toString().length() >= 10){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, player names must not be more than 10 characters.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            startActivity(intent);
        }

    }
}

