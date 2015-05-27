package androidpath.ll.eventbusdemo.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le on 2015/5/27.
 */
public class Item {
    public String id;
    public String content;

    public static List<Item> ITEMS = new ArrayList<Item>();

    static {
        // Add 6 sample items.
        addItem(new Item("1", "Item 1"));
        addItem(new Item("2", "Item 2"));
        addItem(new Item("3", "Item 3"));
        addItem(new Item("4", "Item 4"));
        addItem(new Item("5", "Item 5"));
        addItem(new Item("6", "Item 6"));
        addItem(new Item("7", "Item 7"));
        addItem(new Item("8", "Item 8"));
        addItem(new Item("9", "Item 9"));
        addItem(new Item("10", "Item 10"));
        addItem(new Item("11", "Item 11"));
        addItem(new Item("12", "Item 12"));
        addItem(new Item("13", "Item 13"));
        addItem(new Item("14", "Item 14"));
        addItem(new Item("15", "Item 15"));
        addItem(new Item("16", "Item 16"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
    }

    public Item(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}