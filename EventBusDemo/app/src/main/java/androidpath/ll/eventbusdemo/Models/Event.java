package androidpath.ll.eventbusdemo.Models;

import java.util.List;

/**
 * Created by Le on 2015/5/27.
 */
public class Event {
    /**
     * Loading List
     */
    public static class ItemListEvent {
        private List<Item> items;

        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }

}
