package com.example.bottomnavyt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Random

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usage_data.db"
        private const val DATABASE_VERSION = 1

        // Table names and columns
        private const val TABLE_USAGE = "usage"
        private const val COLUMN_ID = "id"
        private const val COLUMN_WATER_USAGE = "water_usage"
        private const val COLUMN_ELECTRICITY_USAGE = "electricity_usage"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the usage table
        val createTable = "CREATE TABLE $TABLE_USAGE (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_WATER_USAGE REAL," +
                "$COLUMN_ELECTRICITY_USAGE REAL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USAGE")
        // Create tables again
        onCreate(db)
    }

    fun insertRandomData() {
        val db = this.writableDatabase
        val values = ContentValues()
        val random = Random()

        // Generate random usage data for water and electricity
        val waterUsage = 2 + random.nextDouble() * (5 - 2)
        val electricityUsage = 250 + random.nextDouble() * (550-250)

        values.put(COLUMN_WATER_USAGE, waterUsage)
        values.put(COLUMN_ELECTRICITY_USAGE, electricityUsage*0.000555556)

        // Inserting Row
        db.insert(TABLE_USAGE, null, values)
        db.close() // Closing database connection
    }

    @SuppressLint("Range")
    fun getCurrentWaterUsage(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT $COLUMN_WATER_USAGE FROM $TABLE_USAGE ORDER BY $COLUMN_ID DESC LIMIT 1", null)
        var currentWaterUsage = 0.0

        if (cursor.moveToFirst()) {
            currentWaterUsage = cursor.getDouble(cursor.getColumnIndex(COLUMN_WATER_USAGE))
        }

        cursor.close()
        db.close()

        return currentWaterUsage
    }

    @SuppressLint("Range")
    fun getCurrentElectricityUsage(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT $COLUMN_ELECTRICITY_USAGE FROM $TABLE_USAGE ORDER BY $COLUMN_ID DESC LIMIT 1", null)
        var currentElectricityUsage = 0.0

        if (cursor.moveToFirst()) {
            currentElectricityUsage = cursor.getDouble(cursor.getColumnIndex(
                COLUMN_ELECTRICITY_USAGE
            ))
        }

        cursor.close()
        db.close()

        return currentElectricityUsage
    }

    fun getTotalWaterUsage(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT SUM($COLUMN_WATER_USAGE) FROM $TABLE_USAGE", null)
        var totalWaterUsage = 0.0

        if (cursor.moveToFirst()) {
            totalWaterUsage = cursor.getDouble(0)
        }

        cursor.close()
        db.close()

        return totalWaterUsage
    }

    fun getTotalElectricityUsage(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT SUM($COLUMN_ELECTRICITY_USAGE) FROM $TABLE_USAGE", null)
        var totalElectricityUsage = 0.0

        if (cursor.moveToFirst()) {
            totalElectricityUsage = cursor.getDouble(0)
        }

        cursor.close()
        db.close()

        return totalElectricityUsage
    }
}

