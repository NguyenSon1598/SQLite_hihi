package com.example.sqlite_hihi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    BDHelper(Context context){
        super(context,"book_list",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Book(id integer primary key,"+"title text,author text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Book");
        onCreate(db);
    }
    // chèn book vào cơ sở dữ liệu
    public boolean insertbook (Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",book.getId());
        contentValues.put("title",book.getTitle());
        contentValues.put("author",book.getAuthor());
        db.insert("Book",null,contentValues);
        return true;
    }
    //truy vấn dữ liệu
    public ArrayList<Book> getBook (int id){
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select* from Book where id =" + id,null);
        if(cursor != null)
            cursor.moveToFirst();
        Book book = new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        list.add(book);
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<Book> getAllBook(){
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select* from Book",null);
        if(cursor != null)
            cursor.moveToFirst();
        while(cursor.isAfterLast() == false)
        {
            list.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean deleteBook(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        int count = db.delete("Book","id"+ "=?",new String[]{String.valueOf(id)});
        db.close();
        if(count>0)
            return true;
        return false;
    }
    public boolean updateBook (Book book){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",book.getId());
        contentValues.put("title",book.getTitle());
        contentValues.put("author",book.getAuthor());
        int count = db.update("Book",contentValues,"id" + "=?",new String[]{String.valueOf(book.getId())});
        if(count>0)
            return true;
        return false;
    }
}
