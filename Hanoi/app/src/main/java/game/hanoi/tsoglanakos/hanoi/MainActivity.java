package game.hanoi.tsoglanakos.hanoi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends Activity {
private GameVeiwGrpoup gvg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.r_layout);
		gvg= new GameVeiwGrpoup(this);
		rl.addView(gvg);
		setContentView(rl);



	}




}
