package com.workman.pokemonitor;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_MAIN = 234;
    private Button but;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    } // end of onCreate()

    private void init(){
        but = this.findViewById(R.id.main_button);
        tv = this.findViewById(R.id.main_textView);
    } // end of init()

    public void go(View v) {
        new AsyncRetriever(78, this).execute("https://pokeapi.co/api/v2/evolution-chain/");
    } //end of go()


    private void callListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MAIN);
    } // end of callListActivity()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == REQUEST_CODE_MAIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Welcome back, try again if you like", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Action cancelled.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ahh... you used the 'BACK' button.", Toast.LENGTH_SHORT).show();
        }
    } // onActivityResult()

    public void writeToView(String var){
        tv.setText(var);
    }// end of writeToView()

    public void receiveDataSet(ConcurrentLinkedQueue<SingleTask> var){
        tv.setText(tv.getText()+ "EOF Size:" + var.size());
        new AsyncSorter(this).execute(var);
    } // end of receiveDataSet()

    public void receiveSortedChains(){
       callListActivity();
    }// end of receiveSortedChains()
} // end of class
