package game.hanoi.tsoglanakos.hanoi;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class TubeView extends ViewGroup {
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	public static int numOfBricks = 5;
	private int color;
	private int id;
	private final static int ERROR=10000;
	private static Brick selectedBrick;

	public TubeView(final Activity context, int id) {
		super(context);
		setWillNotDraw(false);
		this.id = id;
		if (id == 0)
			for (int i = numOfBricks - 1; i >= 0; i--) {
				Brick b = new Brick(context, i + 1);
				b.setTube(this);
				addView(b);
				bricks.add(b);
			}
		setFocusable(true);
		requestFocus();
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e(Integer.toString(getTopBrickValueOfTube()),
						"max value of tube " + TubeView.this.id);

				if (getTopBrickValueOfTube() != ERROR&&selectedBrick==null) {
					selectedBrick = getTopBrickOfTube();
					selectedBrick.setBackColor(Color.GREEN);
				} else {

					if (selectedBrick != null && !contains(selectedBrick)&&(getTopBrickValueOfTube() >selectedBrick.getId())) {
						context.runOnUiThread(new Thread() {
							public void run() {
								selectedBrick.getTube().getBricks()
										.remove(selectedBrick);
								selectedBrick.getTube().removeView(
										selectedBrick);
								bricks.add(selectedBrick);
								addView(selectedBrick);
								selectedBrick.setTube(TubeView.this);
								selectedBrick.setBackColor(Color.BLACK);
								selectedBrick = null;
								invalidate();
								postInvalidate();
								onLayout(true, 0, 0, getWidth(), getHeight());
							}
						});

					} else {
						if (selectedBrick != null) {
							selectedBrick.setBackColor(Color.BLACK);
						}

						selectedBrick = null;
					}
				}

			}
		});

	}

	public int getTopBrickValueOfTube() {
		if (bricks.isEmpty())
			return ERROR;
		return bricks.get(bricks.size() - 1).getId();

	}

	public Brick getTopBrickOfTube() {
		if (bricks.isEmpty())
			return null;
		return bricks.get(bricks.size() - 1);

	}

	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		postInvalidate();
	}

	private boolean contains(View v) {
		int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			if (v == view) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		Log.e(Integer.toString(childCount), "child count " + id);
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			view.layout(0, (int) (getHeight() - ((i + 1)
					/ (float) (numOfBricks) * getHeight() / 3)), getWidth(),
					(int) (getHeight() - ((i) / (float) (numOfBricks)
							* getHeight() / 3)));
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(color);
		canvas.drawRect(getWidth() / 2 - getWidth() / 20, getHeight() / 3,
				getWidth() / 2 + getWidth() / 20, getHeight(), paint);
	}
}
