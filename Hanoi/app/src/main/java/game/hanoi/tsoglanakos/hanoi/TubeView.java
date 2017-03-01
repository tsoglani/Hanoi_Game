package game.hanoi.tsoglanakos.hanoi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TubeView extends ViewGroup {
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    public static int numOfBricks;
    private int color;
    private int id;
    private final static int ERROR = 10000;
    private static Brick selectedBrick;
    private Bitmap pipe;
    private static int minTime = 0;
    private Activity context;
    static boolean isTimeEnabled = false;
    static boolean stopCountTime = false;
    int counter = 0;
    private TextView moves_text, min_moves_text, timer_text;

    public TubeView(final Activity context, final int id) {
        super(context);
        setWillNotDraw(false);
        this.id = id;
        this.context = context;
        stopCountTime = false;
        isTimeEnabled = getSharedPrefBool(getContext(), TubeView.DBCHALLENGEINFO, false);
        moves_text = (TextView) context.findViewById(R.id.moves_text);
        min_moves_text = (TextView) context.findViewById(R.id.min_moves_text);
        timer_text = (TextView) context.findViewById(R.id.timer_text);
        double minMoves = Math.pow(2, numOfBricks) - 1;
        min_moves_text.setText("Min Moves : " + (int) minMoves);
        double minTime = minMoves * 3;
        counter = (int) minTime;
        timer_text.setText("Time : " + counter);
        numOfBricks = getSharedPref(getContext(), DBInfo, 3);
        GameActivity.moves_counter = 0;
        if (id == 0)
            for (int i = numOfBricks - 1; i >= 0; i--) {
                Brick b = new Brick(context, i + 1);
                b.setTube(this);
                addView(b);
                bricks.add(b);
            }
        pipe = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);

        setFocusable(true);
        requestFocus();
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e(Integer.toString(getTopBrickValueOfTube()),
                        "max value of tube " + TubeView.this.id);

                if (getTopBrickValueOfTube() != ERROR && selectedBrick == null) {
                    selectedBrick = getTopBrickOfTube();
                    selectedBrick.setBackColor(Color.BLACK);
                } else {

                    if (selectedBrick != null && !contains(selectedBrick) && (getTopBrickValueOfTube() > selectedBrick.getId())) {
                        context.runOnUiThread(new Thread() {
                            public void run() {
                                selectedBrick.getTube().getBricks()
                                        .remove(selectedBrick);
                                selectedBrick.getTube().removeView(
                                        selectedBrick);
                                bricks.add(selectedBrick);
                                addView(selectedBrick);
                                selectedBrick.setTube(TubeView.this);
                                selectedBrick.setBackColor();
                                GameActivity.moves_counter++;
                                context.runOnUiThread(new Thread() {

                                    @Override
                                    public void run() {


                                        moves_text.setText("Moves : " + GameActivity.moves_counter);
                                    }
                                });
                                selectedBrick = null;
                                invalidate();
                                postInvalidate();
                                onLayout(true, 0, 0, getWidth(), getHeight());
                            }
                        });

                    } else {
                        if (selectedBrick != null) {
                            selectedBrick.setBackColor();
                        }

                        selectedBrick = null;
                    }
                }


                if (id == 2 && getChildCount() == numOfBricks) {
                    winnerMethod();


                }

            }
        });


        if (isTimeEnabled&&id==2) {
            timer_text.setVisibility(VISIBLE);
            new Thread() {
                @Override
                public void run() {
                    Log.e("runn" + stopCountTime, "runn" + counter);

                    while (!stopCountTime && counter >= 0) {
                        Log.e("runn", "runn");
                        try {
                            sleep(1000);
                            counter--;
                            context.runOnUiThread(new Thread() {
                                @Override
                                public void run() {

                                    timer_text.setText("Time : " + counter);

                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    if (counter < 0) {
                        context.runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                dialogBoxLose();
                            }
                        });
                    }

                }
            }.start();

        } else {
            timer_text.setVisibility(INVISIBLE);

        }
    }


    protected static final String MY_PREFS_NAME = "Hanoi_Game";
    protected static final String DBInfo = "LEVEL";
    protected static final String DBCHALLENGEINFO = "TIMER_";

    protected static boolean getSharedPrefBool(Context context, String text, boolean defVal) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(text, defVal);

    }

    protected static int getSharedPref(Context context, String text, int defVal) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(text, defVal);

    }

    public void storeSharePref(String text, int value) {

        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(text, value);
        editor.commit();
    }

    private void winnerMethod() {
        Log.e("winner", "winner");
        if (numOfBricks < 15)
            numOfBricks++;
        storeSharePref(DBInfo, numOfBricks);
        stopCountTime = true;
        dialogBox();

        // choose next level or menu

    }

    public void dialogBoxLose() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Time is up" + ".\nWhat will you do ?");

        alertDialogBuilder.setPositiveButton("Try again.",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        context.runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                nextLevel();
                            }
                        });

                    }
                });

        alertDialogBuilder.setNegativeButton("Menu",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        context.runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                goToMenu();
                            }
                        });
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Congratulations !!!! you manage to pass level " + (getSharedPref(getContext(), TubeView.DBInfo, 3) - 3) + ".\nWhat will you do ?");

        alertDialogBuilder.setPositiveButton(numOfBricks < 15 ? "Next Level." : "Play again.",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        context.runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                nextLevel();
                            }
                        });

                    }
                });

        alertDialogBuilder.setNegativeButton("Menu",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        context.runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                goToMenu();
                            }
                        });
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToMenu() {
        context.runOnUiThread(new Thread() {
            @Override
            public void run() {

                Intent intent = new Intent(getContext(), MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);


            }
        });

    }

    private void nextLevel() {
        context.runOnUiThread(new Thread() {
            @Override
            public void run() {

                Intent intent = new Intent(getContext(), GameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
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

        Bitmap newBitmap = Bitmap.createScaledBitmap(pipe, getWidth() / 10,
                getHeight(), false);
        canvas.drawBitmap(newBitmap, getWidth() / 2 - getWidth() / 20, 0, paint);
    }
}
