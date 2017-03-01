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

public class GameActivity extends Activity {
private GameVeiwGrpoup gvg;
	public static int  moves_counter=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.frame);
		RelativeLayout gl=(RelativeLayout) findViewById(R.id.r_layout);

		Brick.fillBrickRemainingColors();
		gvg= new GameVeiwGrpoup(this);
		setContentView(rl);
		gl.addView(gvg);

		moves_counter=0;


	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		TubeView.stopCountTime=true;
	}
}
