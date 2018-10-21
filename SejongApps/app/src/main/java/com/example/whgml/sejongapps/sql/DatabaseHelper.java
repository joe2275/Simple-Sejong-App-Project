package com.example.whgml.sejongapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.whgml.sejongapps.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";

    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_AGE = "user_age";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private Context activity;

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT, " +
            COLUMN_USER_EMAIL + " TEXT, " +
            COLUMN_USER_AGE + " INTEGER, " +
            COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        activity = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_AGE, user.getAge());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void selectAllUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c=db.rawQuery("Select * From " + TABLE_USER, null);
        if(c.getCount() == 0)
        {
            Toast.makeText(activity, "Database is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("Student Name : " + c.getString(1) + "\n");
            buffer.append("Student Email : " + c.getString(2) + "\n");
            buffer.append("Student Age : " + c.getString(3) + "\n");
        }

        Toast.makeText(activity, buffer.toString(), Toast.LENGTH_LONG).show();
    }

    public User selectUserUsingName(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * From " + TABLE_USER + " Where "+ COLUMN_USER_NAME + "='" + name + "'", null);
        if(c.moveToFirst())
        {
            User user = new User();
            user.setName(c.getString(1));
            user.setEmail(c.getString(2));
            user.setAge(c.getInt(3));
            user.setPassword(c.getString(4));
            return user;
        }
        else
        {
            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void updateUserUsingName(String searchName, User user)
    {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursorUpdate = db.rawQuery("Select * From " + TABLE_USER + " Where " + COLUMN_USER_NAME + "='" + searchName + "'", null);
        if(cursorUpdate.moveToFirst()) {
            db.execSQL("Update " + TABLE_USER + " Set " + COLUMN_USER_NAME + "='" + user.getName()
                    + "', " + COLUMN_USER_EMAIL + "='" + user.getEmail() + "', " + COLUMN_USER_AGE + "=" + user.getAge() + ", "
                    + COLUMN_USER_PASSWORD + "='" + user.getPassword() + "' Where " + COLUMN_USER_NAME + "='" + searchName + "'");

            Toast.makeText(activity, "Record Modified", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUserUsingName(String searchName)
    {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursorDelete = db.rawQuery("Select * From " + TABLE_USER + " Where " + COLUMN_USER_NAME + "='" + searchName + "'", null);
        if(cursorDelete.moveToFirst())
        {
            db.execSQL("Delete From " + TABLE_USER + " Where " + COLUMN_USER_NAME + "='" + searchName + "'");
            Toast.makeText(activity, "Record Deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkUser(String email)
    {
        String[] columns = {COLUMN_USER_ID };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs =  { email };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0)
        {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password)
    {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if(cursorCount > 0)
        {
            return true;
        }
        return false;
    }
}
