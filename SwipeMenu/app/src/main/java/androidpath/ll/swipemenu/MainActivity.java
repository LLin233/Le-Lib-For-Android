package androidpath.ll.swipemenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
    private MainUI mainUI;
    private LeftMenu leftMenu;
    private RightMenu rightMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUI = new MainUI(this);
        setContentView(mainUI);
        leftMenu = new LeftMenu();
        rightMenu = new RightMenu();
        getSupportFragmentManager().beginTransaction().add(MainUI.LEFT_ID, leftMenu).commit();
        getSupportFragmentManager().beginTransaction().add(MainUI.RIGHT_ID, rightMenu).commit();
    }

}
