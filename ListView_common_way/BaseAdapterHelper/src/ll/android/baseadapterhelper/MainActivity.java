package ll.android.baseadapterhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	
    private ListView mListView;  
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("List",  
            "View", "Common"));  
    private MyAdapter mAdapter;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        mListView = (ListView) findViewById(R.id.id_lv_main);  
        mListView.setAdapter(mAdapter = new MyAdapter(this, mDatas));  
  
    }  
}
