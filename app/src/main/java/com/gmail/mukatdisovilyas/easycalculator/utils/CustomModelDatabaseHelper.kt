package com.gmail.mukatdisovilyas.easycalculator.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CustomModelDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, COLORS_TABLE_NAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        val creatingDbStatement =
            "CREATE TABLE $COLORS_TABLE_NAME( $COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NUMBERS_COLOR TEXT, " +
                    "$COLUMN_ACTIONS_COLOR TEXT, $COLUMN_AC_COLOR TEXT, $COLUMN_EQUAL_COLOR TEXT, " +
                    "$COLUMN_TEXT_COLOR TEXT, $COLUMN_SHAPE TEXT );"
        db?.execSQL(creatingDbStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Not yet implemented")
    }

    fun addOne(
        numbersColor: String, actionsColor: String, acColor: String,
        equalColor: String, textColor:String, shape:String): Boolean
    {
        if (!isCustomizationEmpty())
        {
            clear()
        }
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NUMBERS_COLOR, numbersColor)
        cv.put(COLUMN_ACTIONS_COLOR, actionsColor)
        cv.put(COLUMN_AC_COLOR, acColor)
        cv.put(COLUMN_EQUAL_COLOR, equalColor)
        cv.put(COLUMN_TEXT_COLOR, textColor)
        cv.put(COLUMN_SHAPE, shape)
        val result = db.insert(COLORS_TABLE_NAME, null, cv)
        db.close()
        return result != -1L
    }

    fun isCustomizationEmpty() : Boolean
    {
        var empty = false
        val db = this.readableDatabase
        val cur: Cursor? = db.rawQuery("SELECT COUNT(*) FROM $COLORS_TABLE_NAME", null)
        if (cur != null)
        {
            cur.moveToFirst() // Always one row returned.
            empty = cur.getInt(0) == 0
        }
        cur?.close()
        db.close()
        return empty
    }

    fun getAll(): MutableList<CustomModel>
    {

        val list: ArrayList<CustomModel> = ArrayList()

        val queryString = "SELECT * FROM $COLORS_TABLE_NAME;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(queryString, null)

        if (cursor.moveToFirst())
        {
            do
            {
                val id = cursor.getInt(0)
                val nColor = cursor.getString(1)
                val actColor = cursor.getString(2)
                val acColor = cursor.getString(3)
                val eqColor = cursor.getString(4)
                val textColor = cursor.getString(5)
                val shape = cursor.getString(6)
                val expression = CustomModel(id, nColor, actColor, acColor, eqColor, textColor, shape)
                list.add(expression)
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun clear() : Boolean
    {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $COLORS_TABLE_NAME")
        db.close()
        return true
    }
}