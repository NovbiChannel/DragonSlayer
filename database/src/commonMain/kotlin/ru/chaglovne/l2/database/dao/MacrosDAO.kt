package ru.chaglovne.l2.database.dao

import Macro

interface MacrosDAO {
    fun addMacro(macro: Macro): Int
    fun getMacros(): List<Macro>
    fun getMacro(id: Int): Macro?
}