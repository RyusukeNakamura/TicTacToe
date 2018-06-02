package com.lifeistech.android.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int[]PLAYER_IMAGES={R.drawable.icon_batsu,R.drawable.icon_maru};
    public int turn;
    public int[]gameBoard;
    public ImageButton[]boardButtons;
    public TextView playerTextView,winnerTextView;
    public int[] buttonIds={
            R.id.imageButton1,R.id.imageButton2,R.id.imageButton3,R.id.imageButton4,R.id.
            imageButton5,R.id.imageButton6,R.id.imageButton7, R.id.imageButton8,R.id.imageButton9
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerTextView=(TextView)findViewById(R.id.playerText);
        winnerTextView=(TextView)findViewById(R.id.winnerText);

        boardButtons=new ImageButton[9];
        for(int i=0;i<boardButtons.length;i++){
            boardButtons[i]=(ImageButton)findViewById(buttonIds[i]);
        }
        init();
        setPlayer();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        if(id==R.id.action_menu_reset){
            init();
            setPlayer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void init(){
        turn=1;

        gameBoard=new int[boardButtons.length];
        for(int i=0;i<boardButtons.length;i++){
            gameBoard[i]=-1;
            boardButtons[i].setImageBitmap(null);
        }
        winnerTextView.setVisibility(View.GONE);
    }
    public void setPlayer(){
        if(turn%2==0){
            playerTextView.setText("Player:x(2)");
        }else{
            playerTextView.setText("Player:o(1)");
        }
    }
    public void tapImageButton(View v){

        if(winnerTextView.getVisibility()==View.VISIBLE)return;

        int tappedButtonPosition=0;
        int viewId=v.getId();



        //改変
        for(int i=0;i<buttonIds.length;i++){
            if(viewId==buttonIds[i]){
                tappedButtonPosition=i;

            }
        }
        Log.d("tappedButtonPosit",tappedButtonPosition+"");

        if(gameBoard[tappedButtonPosition]==-1){
            boardButtons[tappedButtonPosition].setImageResource(PLAYER_IMAGES[turn%2]);
            boardButtons[tappedButtonPosition].setScaleType(ImageView.ScaleType.FIT_CENTER);
            gameBoard[tappedButtonPosition]=turn%2;

            int judge=judgeGame();

            if(judge!=-1){
                if(judge==0){
                    winnerTextView.setText("Game End\nPlayer:x(2)\nWin");
                    winnerTextView.setTextColor(Color.BLUE);
                }else{
                    winnerTextView.setText("Game End\nPlayer:o(1)\nWin");
                    winnerTextView.setTextColor(Color.RED);
                }
                winnerTextView.setVisibility(View.VISIBLE);
            }else{
                if(turn>=gameBoard.length){
                    winnerTextView.setText("Game End\nDraw");
                    winnerTextView.setTextColor(Color.YELLOW);
                    winnerTextView.setVisibility(View.VISIBLE);

                }
            }
            turn++;
            setPlayer();
        }
    }
    public int judgeGame(){
        for(int i=0;i<3;i++){
            if(isMarkedHorizontal(i)){
                return gameBoard[i*3];
            }
            if(isMarkedVertical(i)){
                return gameBoard[i];

            }
        }
        if(isMarkedDiagonal()){
            return gameBoard[4];
        }
        return -1;
    }
    public boolean isMarkedHorizontal(int i){
        if(gameBoard[i*3]!=-1&&gameBoard[i*3]==gameBoard[i*3+1]&&gameBoard[i*3]==gameBoard[i*3+2]){
            return true;
        }else{
            return false;
        }
    }
    public boolean isMarkedVertical(int i){
        if(gameBoard[i]!=-1&&gameBoard[i+3]==gameBoard[i]&&gameBoard[i*3]==gameBoard[i+6]){
            return true;
        }else{
            return false;
        }
    }
    public boolean isMarkedDiagonal(){
        if(gameBoard[0]!=-1&&gameBoard[0]==gameBoard[4]&&gameBoard[0]==gameBoard[8]){
            return true;
        }else if(gameBoard[2]!=-1&&gameBoard[2]==gameBoard[4]&&gameBoard[2]==gameBoard[6]){
            return true;
        }else{
            return false;
        }
    }

}
