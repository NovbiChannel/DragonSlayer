package ru.chaglovne.l2.database.dao

import InputType
import Macro

interface MacrosDAO {
    fun addMacro(macro: Macro): Int
    fun updateMacro(macro: Macro): Int
    fun getMacros(): List<Macro>
    fun getMacro(id: Int): Macro?
    fun checkAvailabilityInput(inputType: InputType, macroId: Int? = null): Boolean
}