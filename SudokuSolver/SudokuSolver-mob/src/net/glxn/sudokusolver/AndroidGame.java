package net.glxn.sudokusolver;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class AndroidGame extends AndroidApplication {
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new Game(), false);
    }
}
