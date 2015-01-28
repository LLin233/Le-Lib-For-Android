package ll.android.baseadapterhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView mListView;
	private List<String> mDatas = new ArrayList<String>(Arrays.asList("List",
			"View", "Common"));

	private CommonAdapter<String> mAdapter2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mListView = (ListView) findViewById(R.id.id_lv_main);

		mListView.setAdapter(mAdapter2 = new CommonAdapter<String>(
				getApplicationContext(), mDatas, R.layout.list_item) {
			@Override
			public void convert(ViewHolder viewHolder, String item) {
				 viewHolder.setText(R.id.id_tv_title, item);
			}

		});

	}
}
