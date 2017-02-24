package game.hanoi.tsoglanakos.hanoi;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class GameVeiwGrpoup extends ViewGroup {
	private TubeView[] tv = new TubeView[3];

	public GameVeiwGrpoup(Activity context) {
		super(context);
		
		for (int i = 0; i < tv.length; i++) {
			tv[i]= new TubeView(context,i);
			tv[i].setColor(getResources().getColor(R.color.Chocolate));
			addView(tv[i]);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			view.layout(i * getWidth() / 3, 0, (i + 1) * getWidth() / 3,
					getHeight());
		}
	}

}
