package com.liyak28.siswabaru.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.createAlertDialog(
    title: String,
    message: String,
    positiveLabel: String,
    negativeLabel: String,
    positiveButton: ((DialogInterface) -> Unit)? = {},
    negativeButton: ((DialogInterface) -> Unit)? = {}
): AlertDialog.Builder = AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(message)
    .setPositiveButton(positiveLabel) { dialog, _ -> positiveButton?.invoke(dialog) }
    .setNegativeButton(negativeLabel) { dialog, _ -> negativeButton?.invoke(dialog) }