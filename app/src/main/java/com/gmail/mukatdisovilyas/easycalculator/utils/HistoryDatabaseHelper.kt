package com.gmail.mukatdisovilyas.easycalculator.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context?) : SQLiteOpenHelper(context, "historyDB", null, 1)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        val creatingDBStatement =
            "CREATE TABLE $HISTORY_TABLE_NAME( $COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_PREV_EXP TEXT, " +
                    "$COLUMN_RESULT TEXT, $COLUMN_DATE TEXT );"
        db?.execSQL(creatingDBStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Not yet implemented")
    }

    fun addOne(prevExp: String, result: String, date: String): Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_PREV_EXP, prevExp)
        cv.put(COLUMN_RESULT, result)
        cv.put(COLUMN_DATE, date)
        val insert = db.insert(HISTORY_TABLE_NAME, null, cv)
        db.close()
        return insert != -1L
    }

    fun getAll(): MutableList<Expression>
    {
        val list: ArrayList<Expression> = ArrayList()

        val queryString = "SELECT * FROM $HISTORY_TABLE_NAME ORDER BY $COLUMN_ID ASC, $COLUMN_DATE ASC;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(queryString, null)

        if (cursor.moveToFirst())
        {
            do
            {
                val id = cursor.getInt(0)
                val prevExp = cursor.getString(1)
                val result = cursor.getString(2)
                val date = cursor.getString(3)
                val expression = Expression(id, prevExp, result, date)
                list.add(expression)
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun clear() : Boolean{
        val db = this.writableDatabase
        val clearDBStatement="DELETE FROM $HISTORY_TABLE_NAME;"
        db.execSQL(clearDBStatement)
        db.close()
        return true
    }

}