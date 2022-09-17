package com.liyak28.siswabaru.common.extension

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import javax.inject.Qualifier

fun View.visible() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}