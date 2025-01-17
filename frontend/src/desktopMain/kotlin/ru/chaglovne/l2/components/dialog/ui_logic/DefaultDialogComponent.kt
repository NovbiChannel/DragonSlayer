package ru.chaglovne.l2.components.dialog.ui_logic

class DefaultDialogComponent(
    override val title: String,
    override val message: String? = null,
    private val onDismissed: () -> Unit
): DialogComponent {

    override fun onDismissClicked() { onDismissed() }
}