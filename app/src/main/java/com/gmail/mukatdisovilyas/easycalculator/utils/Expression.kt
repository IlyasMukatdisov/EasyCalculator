package com.gmail.mukatdisovilyas.easycalculator.utils

class Expression(var id: Int, var prevExp: String, var result: String, var date: String)
{


    override fun toString(): String
    {
        return "Expression(id=$id, prevExp='$prevExp', result='$result', date='$date')"
    }


}