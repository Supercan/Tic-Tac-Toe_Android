package cw2.com.tictactoe;

// Import necessary packages
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Tic Tac Toe group project
 *
 * @author Taiwo Kareem
 * @author Pam Iwalewa
 */

// Game activity class
public class GameActivity extends AppCompatActivity {
    /**
     * Representing the game state:
     */
    // Turns false = X true = O
    private boolean xTurn = false;
    private char board[][] = new char[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Called when the activity is first created
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Calling methods
        setupOnClickListeners();
        resetButtons();

    }

    /**
     * Called when the New Game button is clicked
     * @param view the New Game Button
     */

    /**
     * Reset each button in the grid to be blank and enabled.
     */
    private void resetButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tblLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button Btn = (Button) R.getChildAt(x);
                        Btn.setText("");
                        Btn.setEnabled(true);
                    }
                }
            }
        }
        TextView t = (TextView) findViewById(R.id.txtHeader);
        t.setText(R.string.title);
    }

    // Triggers new game
    public void newGame(View view) {
        xTurn = false;
        board = new char[3][3];
        resetButtons();
    }

    // Toasts to display after each cell clicks
    public void toast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Custom OnClickListener for TicTacToe
     */
    private class PlayOnClick implements View.OnClickListener {

        // Field variables
        private int x = 0;
        private int y = 0;

        /**
         * Constructor initializing inner class fields
         */
        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            String player1 = intent.getStringExtra("PLAYER1");
            String player2 = intent.getStringExtra("PLAYER2");

            if(view instanceof Button) {
                Button Btn = (Button) view;

                // Short hand for if/else
                board[x][y] =  xTurn ? 'O' : 'X';
                Btn.setText(xTurn ? "O" : "X");
                Btn.setTextColor(xTurn ? Color.RED : Color.BLUE);
                toast(xTurn ? player1 + "'s turn" : player2 + "'s turn");

                Btn.setEnabled(false);
                xTurn = !xTurn;


                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                }
            }
        }
    }

    /**
     * This will add the OnClickListener to each button inside out TableLayout
     */
    private void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tblLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            // Getting each table rows
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    // Selecting each button on the grid
                    View V = R.getChildAt(x);
                    V.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }


    /**
     * This is a generic algorithm for checking if a specific player has won on a tic tac toe board of any size.
     *
     * @param board  the board itself
     * @param size   the width and height of the board
     * @param player the player, 'X' or 'O'
     * @return true if the specified player has won
     */
    private boolean checkWinner(char[][] board, int size, char player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[y][x] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check Horizontal
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // Check Vertical
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[y][x] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diagonal check
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[y][x] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diagonal check
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[y][x] == player) {
                    total++;
                }
            }
        }

        // return true or false
        return (total >= size);
    }

    /**
     * Method that returns true when someone has won and false when nobody has.
     * It also display the winner on screen.
     *
     * @return a boolean
     */
    private boolean checkWin() {

        TextView T = (TextView) findViewById(R.id.txtHeader);
        Intent intent = getIntent();

        String player1 = intent.getStringExtra("PLAYER1");
        String player2 = intent.getStringExtra("PLAYER2");

        String winner = "0";
        if (checkWinner(board, 3, 'X')) {
            winner = player1;
        } else if (checkWinner(board, 3, 'O')) {
            winner = player2;
        }

        if (winner.equals("0")) {
            return false; // nobody won
        } else {
            // display winner
            T.setText(winner + " wins");
            return true;
        }
    }

    private void disableButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tblLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button Btn = (Button) R.getChildAt(x);
                        Btn.setEnabled(false);
                    }
                }
            }
        }
    }
}

