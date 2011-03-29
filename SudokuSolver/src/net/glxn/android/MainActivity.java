package net.glxn.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View newGridBtn = findViewById(R.id.newgrid);
        newGridBtn.setOnClickListener(this);
        View aboutBtn = findViewById(R.id.about);
        aboutBtn.setOnClickListener(this);
        View exitBtn = findViewById(R.id.exit);
        exitBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                startActivity(new Intent(this, About.class));
                break;
            case R.id.newgrid:
                newGridDialogue();
                break;
            case R.id.exit:
                finish();
                break;
            default:
                Log.e(TAG, "handler not implemented for " + v.getId());
        }
    }

    private void newGridDialogue() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.newgrid_title)
                .setItems(GridSizes.charSequences(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                openGrid(i);
                            }
                        })
                .show();
    }

    private void openGrid(int i) {
        Log.d(TAG, "open grid " + GridSizes.charSequenceAt(i));
        Intent intent = new Intent(MainActivity.this, Solver.class);
        switch (i) {
            case 0:
                intent.putExtra(Solver.KEY_SIZE, i);
                startActivity(intent);
                break;
            default:
                Log.e(TAG, "no handler for grid " + GridSizes.charSequenceAt(i));
        }
    }
}
