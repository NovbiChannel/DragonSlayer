package ru.chaglovne.l2.components.dialog.ui_logic

interface DialogComponent {
    val title: String
    val message: String?

    fun onDismissClicked()
}