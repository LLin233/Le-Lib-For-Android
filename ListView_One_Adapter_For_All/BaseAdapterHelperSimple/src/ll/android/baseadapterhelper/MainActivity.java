package ll.android.baseadapterhelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView mListView;
	private List<Bean> mDatas = new ArrayList<Bean>();

	private CommonAdapter<Bean> mAdapter2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mListView = (ListView) findViewById(R.id.id_lv_main);
		initDatas();

		mListView.setAdapter(mAdapter2 = new CommonAdapter<Bean>(
				getApplicationContext(), mDatas, R.layout.list_item_complex) {
			@Override
			public void convert(ViewHolder viewHolder, Bean item) {
				viewHolder.setText(R.id.tv_title, item.getTitle());
				viewHolder.setText(R.id.tv_describe, item.getDesc());
				viewHolder.setText(R.id.tv_phone, item.getPhone());
				viewHolder.setText(R.id.tv_time, item.getTime());
			}

		});

	}
	
	private void initDatas()
	{
		Bean bean = null;
		bean = new Bean("TextTitle1", "feeds content: I ", "510857xxxx", "20150128");
		mDatas.add(bean);
		bean = new Bean("TextTitle2", "feeds content: got", "480857xxxx", "20150128");
		mDatas.add(bean);
		bean = new Bean("TextTitle3", "feeds content: it", "401857xxxx", "20150128");
		mDatas.add(bean);
		bean = new Bean("TextTitle4", "feeds content: save more time!", "800857xxxx",
				"20150128");
		mDatas.add(bean);
	}
	
	
}
