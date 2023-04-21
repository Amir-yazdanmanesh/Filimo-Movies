package extension

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible

fun View.gone() {
    if (isVisible)
        visibility = View.GONE
}

fun View.inVisible() {
    if (isVisible)
        visibility = View.INVISIBLE
}

fun View.visible() {
    if (!isVisible)
        visibility = View.VISIBLE
}

inline fun SearchView.onQueryTextListener(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })

}

