package hnase2.project5.uis.project5;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    AssetManager assetManager;
    int mySound = -1;
    ImageView currentPlayerCard, currentComputerCard, playerDeck;
    boolean cardSelected = false;
    float x = 0, y = 0;

    String cards[] = {"clubs_1", "clubs_2", "clubs_3", "clubs_4", "clubs_5", "clubs_6", "clubs_7",
            "clubs_8", "clubs_9", "clubs_10", "clubs_jack", "clubs_queen", "clubs_king", "clubs_ace",};
    String pDeck[] = {};
    String cDeck[] = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide status bar
        View view = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);

        //Initialize images
        playerDeck = (ImageView) this.findViewById(R.id.playerDeck);
        currentPlayerCard = (ImageView) this.findViewById(R.id.currentPlayerCard);
        currentComputerCard = (ImageView) this.findViewById(R.id.currentComputerCard);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        assetManager = getAssets();

        try {
            AssetFileDescriptor descriptor = assetManager.openFd("gameover.wav");
            mySound = soundPool.load(descriptor, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        newGame();
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                x = event.getRawX();
                y = event.getRawY();

                if (x > playerDeck.getX() && x < (playerDeck.getX() + playerDeck.getWidth())
                        && y > playerDeck.getY() && y < (playerDeck.getY() + playerDeck.getHeight())) {
                    cardSelected = true;
                    currentPlayerCard.setImageResource(R.drawable.c1);
                    try {
                        AssetFileDescriptor descriptor = assetManager.openFd("card_flip.mp3");
                        mySound = soundPool.load(descriptor, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } break;

//            case MotionEvent.ACTION_MOVE: {
//                x = event.getRawX() - (playerDeck.getWidth() / 2);
//                y = event.getRawY() - 220 - (playerDeck.getHeight() / 2);
//
//                if (cardSelected == true) {
//                    playerDeck.setX(x);
//                    playerDeck.setY(y);
//                }
//            } break;

            case MotionEvent.ACTION_UP: {
                cardSelected = false;
            } break;

        }

        return true;
    }

    public void newGame() {
        shuffleCards();
    }

    public void shuffleCards() {
        pDeck = null;
        cDeck = null;


    }

    public void playSound (View view) {
        soundPool.play(mySound, 1, 1, 0, 0, 1);
    }
}
