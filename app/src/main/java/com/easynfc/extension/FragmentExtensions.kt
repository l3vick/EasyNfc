import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

inline fun <reified T : Fragment> instanceOf(vararg params: Pair<String, Any?>): T =
    T::class.java.newInstance().apply { arguments = bundleOf(*params) }

fun Fragment.showServerNotReachableDialog(onDismissListener: (() -> Unit)) {
    context?.let {
        AlertDialog.Builder(it).apply {
            setTitle("title error")
            setMessage("message error")
            setNegativeButton("negative btn", null)
            setOnDismissListener { onDismissListener() }
            show()
        }
    }
}

fun Fragment.showSomethingWentWrongDialog(onDismissListener: (() -> Unit)) {
    context?.let {
        AlertDialog.Builder(it).apply {
            setMessage("title error")
            setNegativeButton("message error", null)
            setOnDismissListener { onDismissListener() }
            show()
        }
    }
}
