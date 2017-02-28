package game.hanoi.tsoglanakos.hanoi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
private TextView level_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        level_text=(TextView) findViewById(R.id.level_text);

        level_text.setText("Curent Level:"+(getSharedPref(getApplicationContext(),TubeView.DBInfo,3)-2));
    }



public void newGame(View v){

    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);

}

    protected static int getSharedPref(Context context, String text, int defVal) {
        SharedPreferences prefs = context.getSharedPreferences(TubeView.MY_PREFS_NAME,  Context.MODE_PRIVATE);
        return prefs.getInt(text, defVal);

    }

    public void storeSharePref(String text, int value) {

        SharedPreferences.Editor editor = getSharedPreferences(TubeView.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(text, value);
        editor.commit();
    }


    private void resetLevels(){

        storeSharePref(TubeView.DBInfo,3);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void resetFunction(View v){
        dialogBox();
    }




// Dialog box

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to reset your levels ?");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                resetLevels();
                                level_text.setText("Curent Level:"+(getSharedPref(getApplicationContext(),TubeView.DBInfo,3)-2));
                            }
                        });

                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
