package ll.android.baseadapterhelper;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private List<String> mDatas;

	public MyAdapter(Context context, List<String> mDatas) {
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	@Override  
    public View getView(int position, View convertView, ViewGroup parent)  
    {  
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,  
                R.layout.list_item, position);  
        // get TextView by getView()  
        TextView tv = viewHolder.getView(R.id.id_tv_title);  
        // update info
        tv.setText(mDatas.get(position));  
        return viewHolder.getConvertView();  
    } 


}