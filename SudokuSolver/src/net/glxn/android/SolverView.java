package net.glxn.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by IntelliJ IDEA.
 * User: ken
 * Date: 29.03.11
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public class SolverView extends View {
    private static final String TAG = SolverView.class.getName();
    private final Solver solver;

    private float width; // width of one tile
    private float height; // height of one tile
    private int selX; // X index of selection
    private int selY; // Y index of selection
    private final Rect selRect = new Rect();

    public SolverView(Context context) {
        super(context);
        this.solver = (Solver) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
        getRect(selX, selY, selRect);
        Log.d(TAG, "onSizeChanged: width " + width + ", height "
                + height);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the background...
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);
        // Draw the board...
        Paint gridlines = new Paint();
        gridlines.setColor(getResources().getColor(R.color.puzzle_dark));
        Paint hilite = new Paint();
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));
        // Draw the minor grid lines
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, light);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }
        // Draw the major grid lines
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0) {
                continue;
            }
            canvas.drawLine(0, i * height, getWidth(), i * height, gridlines);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
            canvas.drawLine(i * width, 0, i * width, getHeight(), gridlines);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }
        // Draw the numbers...
        // Draw the hints...
        // Draw the selection...
    }

    private void getRect(int x, int y, Rect rect) {
        rect.set(
                (int) (x * width),
                (int) (y * height),
                (int) (x * width + width),
                (int) (y * height + height)
        );
    }
}
