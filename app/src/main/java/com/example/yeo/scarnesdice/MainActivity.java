package com.example.yeo.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public static int user_score_total = 0;
    public static int user_score_turn = 0;
    public static int computer_score_total = 0;
    public static int computer_score_turn = 0;
    public static int roll()
    {
        Random r = new Random();
        int result = r.nextInt(7-1) + 1;
        switch(result)
        {
            case 1:
                mDie.setImageResource(R.drawable.dice1);
                break;
            case 2:
                mDie.setImageResource(R.drawable.dice2);
                break;
            case 3:
                mDie.setImageResource(R.drawable.dice3);
                break;
            case 4:
                mDie.setImageResource(R.drawable.dice4);
                break;
            case 5:
                mDie.setImageResource(R.drawable.dice5);
                break;
            case 6:
                mDie.setImageResource(R.drawable.dice6);
                break;
        }
        return result;
    }

    public static void updateScore()
    {
        String output;
        output = "Your score: " + user_score_total + " Computer score: " + computer_score_total + " Your turn score: " + user_score_turn;
        mScore.setText(output);
    }

    public static int roll_user()
    {
        int result = roll();
        if (result != 1)
            user_score_turn += result;
        else
            user_score_turn = 0;
        updateScore();
        return result;
    }

    public static long startTime = 0;
    public static Handler timerHandler = new Handler();
    public static Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            int result;
            result = roll();
            if (result != 1)
            {
                computer_score_turn += result;
                if(computer_score_turn < 20)
                {
                    timerHandler.postDelayed(this, 500);
                }
                else
                {
                    computer_score_total += computer_score_turn;
                    computer_score_turn = 0;
                    updateScore();
                    mRollButton.setEnabled(true);
                    mHoldButton.setEnabled(true);
                    timerHandler.removeCallbacks(timerRunnable);
                }

            }
            else
            {
                computer_score_turn = 0;
                updateScore();
                mRollButton.setEnabled(true);
                mHoldButton.setEnabled(true);
                timerHandler.removeCallbacks(timerRunnable);
            }
        }
    };
    public static void computerTurn()
    {
        mRollButton.setEnabled(false);
        mHoldButton.setEnabled(false);
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public static Button mRollButton;
    public static Button mResetButton;
    public static Button mHoldButton;
    public static ImageView mDie;
    public static TextView mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRollButton = (Button) findViewById(R.id.roll_button);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mHoldButton = (Button) findViewById(R.id.hold_button);
        mDie = (ImageView) findViewById(R.id.die);
        mScore = (TextView) findViewById(R.id.textView);
        mRollButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int result = roll_user();
                if (result == 1)
                    computerTurn();

            }
        });
        mResetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_score_total = 0;
                user_score_turn = 0;
                computer_score_total = 0;
                computer_score_turn = 0;
                updateScore();
            }
        });
        mHoldButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_score_total += user_score_turn;
                user_score_turn = 0;
                updateScore();
                computerTurn();
            }
        });
    }
}
