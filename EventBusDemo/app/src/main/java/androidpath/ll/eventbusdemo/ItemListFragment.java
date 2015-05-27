package androidpath.ll.eventbusdemo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidpath.ll.eventbusdemo.Models.Event.ItemListEvent;
import androidpath.ll.eventbusdemo.Models.Item;
import de.greenrobot.event.EventBus;

/**
 * Created by Le on 2015/5/26.
 */
public class ItemListFragment extends ListFragment {
    protected DrawerLayout mDrawerLayout;
    protected View mDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer = (View) getActivity().findViewById(R.id.drawer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // open a thread to load list
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000); // fake loading list from server
                    EventBus.getDefault().post(new ItemListEvent(Item.ITEMS)); // Posts the given event to the event bus
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    public void onEventMainThread(ItemListEvent event) {
        setListAdapter(new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);
        Toast.makeText(getActivity(),
                getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        mDrawerLayout.closeDrawer(mDrawer);
        EventBus.getDefault().post(getListView().getItemAtPosition(position));
    }

}