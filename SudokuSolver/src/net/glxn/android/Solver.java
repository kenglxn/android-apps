package net.glxn.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: ken
 * Date: 29.03.11
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class Solver extends Activity {
    public static final String KEY_SIZE = "net.glxn.android.MainActivity.GridSize";
    private static final String TAG = Solver.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        int size = getIntent().getIntExtra(KEY_SIZE, 0);
        Log.d(TAG, "size="+GridSizes.charSequenceAt(size));

        SolverView solverView = new SolverView(this);
        setContentView(solverView);
        solverView.requestFocus();
    }
}