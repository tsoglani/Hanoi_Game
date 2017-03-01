package game.hanoi.tsoglanakos.hanoi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import java.util.ArrayList;

public class Brick extends View {
	private int id;
	private int backColor = Color.BLACK;
	private TubeView tube;
private static final int [] colors ={
		R.color.Teal,
		R.color.Algae_Green,
		R.color.blueback,
		R.color.BlueViolet,
		R.color.articlecolor,
		R.color.Cyan,
		R.color.OliveDrab,
		R.color.Maroon,
		R.color.Gray,
		R.color.colorPrimaryDark,
		R.color.RubberDuckyYellow,
		R.color.BeeYellow,
		R.color.PurpleMonster,
		R.color.PurpleSageBush,
		R.color.PurpleJam,
		R.color.colorAccent,
		R.color.red,
};

	public static void fillBrickRemainingColors(){

		for (int col:colors){
			brickRemainingColors.add(col);
		}
	}

	public static ArrayList <Integer> brickRemainingColors= new ArrayList<>();
	public Brick(Context context, int id) {
		super(context);
		this.id = id;
		setBackColor();
		setWillNotDraw(false);
		setEnabled(false);
		setFocusable(false);
	}

	public int getId() {
		return id;
	}



	public TubeView getTube() {
		return tube;
	}

	public void setTube(TubeView tube) {
		this.tube = tube;
	}

	public int getBackColor() {
		return backColor;
	}

	public void setId(int id) {
		this.id = id;
	}


	boolean isSelected=false;
	void setBackColor(int col) {
		backColor =col;
		postInvalidate();
		isSelected=true;
	}

	void setBackColor() {
		isSelected=false;

		switch (id){



			case 0:
				backColor = getResources().getColor(R.color.Teal);

				break;
			case 1:
				backColor = getResources().getColor(R.color.Fuchsia);

				break;
			case 2:
				backColor = getResources().getColor(R.color.blueback);
				break;
			case 3:
				backColor = getResources().getColor(R.color.BlueViolet);

				break;
			case 4:
				backColor = getResources().getColor(R.color.articlecolor);

				break;
			case 5:
				backColor = getResources().getColor(R.color.red);

				break;
			case 6:
				backColor = getResources().getColor(R.color.PurpleJam);

				break;
			case 7:
				backColor = getResources().getColor(R.color.PurpleSageBush);

				break;
			case 8:
				backColor = getResources().getColor(R.color.OliveDrab);

				break;
			case 9:
				backColor = getResources().getColor(R.color.Maroon);




				break;
			case 10:
				backColor = getResources().getColor(R.color.Gray);

				break;
			case 11:
				backColor = getResources().getColor(R.color.colorPrimaryDark);

				break;
			case 12:
				backColor = getResources().getColor(R.color.RubberDuckyYellow);

				break;
			case 13:
				backColor = getResources().getColor(R.color.PurpleMonster);

				break;
			case 14:
				backColor = getResources().getColor(R.color.BeeYellow);

				break;
			case 15:
				backColor = getResources().getColor(R.color.colorAccent);

				break;


		}



		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.Transparent));
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		// canvas.drawOval(getWidth() / 2- ((id + (float) 1.0) /
		// (TubeView.numOfBricks + 1))
		// * getWidth() / 2, 0, getWidth() / 2
		// + ((id + (float) 1.0) / (TubeView.numOfBricks + 1))
		// * getWidth() / 2, getHeight(), paint);
		paint.setColor(getBackColor());

		canvas.drawRect(getWidth() / 2 - (id) / (float) (TubeView.numOfBricks)
				* getWidth() / 2, 0, getWidth() / 2 + (id)
				/ (float) (TubeView.numOfBricks) * getWidth() / 2, getHeight(),
				paint);

		paint.setTextSize(getWidth() / 8);
		paint.setColor(!isSelected?Color.BLACK:Color.WHITE);
		paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		canvas.drawText(Integer.toString(id), getWidth() / 2 - getWidth() / 30,
				getHeight() / 2+getHeight()/10, paint);
	}

}
