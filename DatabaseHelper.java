
package com.example.vehicle_insurance_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "insuranceDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE Insurance (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "vehicle TEXT NOT NULL, " +
                        "owner TEXT NOT NULL, " +
                        "company TEXT NOT NULL, " +
                        "policy TEXT NOT NULL, " +
                        "expiry TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Insurance");
        onCreate(db);
    }

    public boolean addInsurance(String vehicle, String owner,
                                String company, String policy,
                                String expiry) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("vehicle", vehicle);
        values.put("owner", owner);
        values.put("company", company);
        values.put("policy", policy);
        values.put("expiry", expiry);

        long result = db.insert("Insurance", null, values);

        return result != -1;
    }

    public String getAllInsurance() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Insurance ORDER BY id DESC",
                null
        );

        StringBuilder records = new StringBuilder();

        try {

            while (cursor.moveToNext()) {

                records.append("Vehicle Number: ")
                        .append(cursor.getString(1)).append("\n");

                records.append("Owner Name: ")
                        .append(cursor.getString(2)).append("\n");

                records.append("Insurance Company: ")
                        .append(cursor.getString(3)).append("\n");

                records.append("Policy Number: ")
                        .append(cursor.getString(4)).append("\n");

                records.append("Expiry Date: ")
                        .append(cursor.getString(5)).append("\n");

                records.append("--------------------------")
                        .append("\n\n");
            }

        } finally {
            cursor.close();
        }

        if (records.length() == 0) {
            return "No insurance records found";
        }

        return records.toString();
    }
}
