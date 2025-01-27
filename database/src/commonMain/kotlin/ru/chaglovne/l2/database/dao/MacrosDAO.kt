package ru.chaglovne.l2.database.dao

import InputType
import Macro

interface MacrosDAO {
    fun addMacro(macro: Macro): Int
    fun updateMacro(macro: Macro): Boolean
    fun getAllMacros(): List<Macro>
    fun getMacro(id: Int): Macro?
    fun deleteMacro(id: Int): Boolean
    fun checkAvailabilityInput(inputType: InputType, macroId: Int? = null): Boolean
}