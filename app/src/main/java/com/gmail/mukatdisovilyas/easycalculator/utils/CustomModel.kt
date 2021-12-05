package com.gmail.mukatdisovilyas.easycalculator.utils

class CustomModel(
    var id: Int, var numberButtonsColor: String, var actionButtonsColor: String,
    var acButtonColor: String, var equalButtonColor: String, var textColor: String,
    var shape: String
)
{

    override fun toString(): String
    {
        return "CustomModel(id=$id, numberButtonsColor='$numberButtonsColor', actionButtonsColor='$actionButtonsColor', acButtonColor='$acButtonColor', equalButtonColor='$equalButtonColor', textColor='$textColor', shape='$shape')"
    }
}