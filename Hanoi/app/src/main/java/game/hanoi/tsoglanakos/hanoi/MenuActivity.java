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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
private TextView level_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        level_text=(TextView) findViewById(R.id.level_text);

        level_text.setText("Curent Level:"+(getSharedPref(getApplicationContext(),TubeView.DBInfo,3)-2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        level_text=(TextView) findViewById(R.id.level_text);

        level_text.setText("Curent Level:"+(getSharedPref(getApplicationContext(),TubeView.DBInfo,3)-2));
    }

    public void newGame(View v){

    Intent intent= new Intent(getApplicationContext(),GameActivity.class);
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
public void prefFunction(View v){
//    Intent intent= new Intent(getApplicationContext(),Pref.class);
//    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    startActivity(intent);
runOnUiThread(new Thread(){
    @Override
    public void run() {
        prefDialog();
    }
});
}

private void prefDialog(){

    View checkBoxView = View.inflate(this, R.layout.pref, null);
   final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
    checkBox.setChecked(getSharedPrefBool(getApplicationContext(), TubeView.DBCHALLENGEINFO, false));
    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            // Save to shared preferences
        }
    });
    checkBox.setText("Time");

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));

    builder.setTitle("Do you want to make it hard?");
    builder.setMessage("timer mode.")
            .setView(checkBoxView)
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    storeSharePref(TubeView.DBCHALLENGEINFO,checkBox.isChecked());
                                             }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).show();
}

    protected static boolean getSharedPrefBool(Context context, String text, boolean defVal) {
        SharedPreferences prefs = context.getSharedPreferences(TubeView.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(text, defVal);

    }
    public void storeSharePref(String text, boolean value) {

        SharedPreferences.Editor editor = getSharedPreferences(TubeView.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(text, value);
        editor.commit();
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
