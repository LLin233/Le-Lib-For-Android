package me.muya.todolist.db;


import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "me.muya.todolist.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }
}