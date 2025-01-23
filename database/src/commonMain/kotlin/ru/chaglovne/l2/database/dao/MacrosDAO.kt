package ru.chaglovne.l2.database.dao

import Macro

interface MacrosDAO {
    fun addMacro(macro: Macro)
    fun getMacros(): List<Macro>
}