package com.alash.beautybox;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alash.beautybox.Fragments.Product;
import com.alash.beautybox.Sign.User;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_PRODUCT_ID = "product_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_PHOTO = "photo";
    //private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PRICE = "price";
    private static final String COLUMN_USER_AMOUNT = "amount";
    Product product;

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_PRODUCT_ID + " TEXT,"+ COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PHOTO + " TEXT,"+ COLUMN_USER_PRICE + " TEXT," + COLUMN_USER_AMOUNT + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param product
     */
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PRODUCT_ID, product.getProduct_id());
        values.put(COLUMN_USER_NAME, product.getName());
        values.put(COLUMN_USER_PHOTO, product.getPhoto());
        //values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PRICE, product.getPrice());
        values.put(COLUMN_USER_AMOUNT, "1");

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Product> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_PHOTO,
                COLUMN_USER_PRICE,
                COLUMN_USER_PRODUCT_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_AMOUNT
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_PRODUCT_ID + " ASC";
        List<Product> productList = new ArrayList<Product>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                product.setProduct_id(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                product.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHOTO)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PRICE)));
                product.setAmount(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AMOUNT)));
                // Adding user record to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return productList;
    }

    /**
     * This method to update user record
     *
     * @param product
     */
    public void updateUser(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_PRODUCT_ID, product.getProduct_id());
//        values.put(COLUMN_USER_NAME, product.getName());
//        values.put(COLUMN_USER_PHOTO, product.getPhoto());
//        values.put(COLUMN_USER_PRICE, product.getPrice());
        values.put(COLUMN_USER_AMOUNT, product.getAmount());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_PRODUCT_ID +  "="  + String.valueOf(product.getProduct_id()), null);
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser1(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(product.getProduct_id())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param phone
     * @return true/false
     */
    public boolean checkUser(String phone) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHOTO ;
        //+ " = ?"
        // selection argument
        String[] selectionArgs = {phone};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_PHOTO + " = ?" + " AND " + COLUMN_USER_AMOUNT + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUser1() {
        String countQuery = "SELECT * FROM" + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt > 0;
    }


}











