package com.kien.quote.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kien.quote.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kien on 11/01/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "quotesManager";

    // Contacts table name
    private static final String TABLE_QUOTES = "quotes";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TYPE_QUOTE = "type_quote";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTE_TABLE = "CREATE TABLE " + TABLE_QUOTES + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_IMAGE + " TEXT, "
                + KEY_TYPE_QUOTE + " INTEGER" + " )";
        db.execSQL(CREATE_QUOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);

        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_TYPE_QUOTE, item.getType_quote());

        db.insert(TABLE_QUOTES, null, values);
        db.close();
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUOTES, new String[]{KEY_ID, KEY_IMAGE,
                KEY_TYPE_QUOTE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor!=null) {
            cursor.moveToFirst();
        }



        Item item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        db.close();
        return  item;
    }

    public ArrayList<Item> getAllItems(int status){
        ArrayList<Item> listItem = new ArrayList<Item>();

        String selectQuery = "SELECT * FROM " + TABLE_QUOTES + " WHERE " + KEY_TYPE_QUOTE + " = " + status + " ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setImage(cursor.getString(1));
                item.setType_quote(cursor.getInt(2));

                listItem.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItem;
    }

    public int getItemCount(){
        return 0;
    }

    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_TYPE_QUOTE, item.getType_quote());


         int index = db.update(TABLE_QUOTES, values, KEY_ID + "=?",
                 new String[]{String.valueOf(item.getId())});
        db.close();
        return index;
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUOTES, KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

        db.close();
    }

    public int lastIndex(){
        SQLiteDatabase db = this.getReadableDatabase();
        int index = 0;
        String query = "SELECT * FROM "+ TABLE_QUOTES +" ORDER BY "+ KEY_ID +" DESC LIMIT 1";
        Log.d("KIENDU", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            index = Integer.parseInt(cursor.getString(0));
        }
        db.close();
        return index;
    }
}
