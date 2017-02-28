package game.hanoi.tsoglanakos.hanoi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Brick extends View {
	private int id;
	private int backColor = Color.BLACK;
	private TubeView tube;

	public Brick(Context context, int id) {
		super(context);
		this.id = id;
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

	void setBackColor(int color) {
		backColor = color;
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
		paint.setColor(backColor);
		canvas.drawRect(getWidth() / 2 - (id) / (float) (TubeView.numOfBricks)
				* getWidth() / 2, 0, getWidth() / 2 + (id)
				/ (float) (TubeView.numOfBricks) * getWidth() / 2, getHeight(),
				paint);
		paint.setTextSize(getWidth() / 10);
		paint.setColor(Color.RED);
		canvas.drawText(Integer.toString(id), getWidth() / 2 - getWidth() / 30,
				getHeight() / 2, paint);
	}

}
