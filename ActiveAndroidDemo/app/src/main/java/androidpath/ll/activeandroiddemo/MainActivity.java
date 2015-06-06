package androidpath.ll.activeandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.util.List;

import androidpath.ll.activeandroiddemo.Model.Category;
import androidpath.ll.activeandroiddemo.Model.Item;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);


        // Create a category
        Category restaurants = new Category();
        restaurants.remoteId = 1;
        restaurants.name = "Restaurants";
        restaurants.save();

        // Create an item
        Item item = new Item();
        item.remoteId = 1;
        item.category = restaurants;
        item.name = "Outback Steakhouse";
        item.save();

        List<Item> list = Item.getAll(restaurants);
        for (Item i : list) {
            Log.i("TAG", i.toString());
        };

        // Deleting items
        //Item item = Item.load(Item.class, 1);
        //item.delete();
        // or with
        new Delete().from(Item.class).where("remote_id = ?", 1).execute();

    }


}
