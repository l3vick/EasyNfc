package com.easynfc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.easynfc.ui.main.menu.MenuFragment
import org.jetbrains.anko.bundleOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.showFragment<MenuFragment>(MenuFragment.TAG, R.id.container)//1
        //show(supportFragmentManager, supportFragmentManager.getFragment<MenuFragment>(), MenuFragment.TAG , R.id.container)//2
        //FragmentUtils.show(supportFragmentManager, MenuFragment.TAG , R.id.container,null)//3
    }
}

//1
inline fun <reified T : Fragment> FragmentManager.showFragment(tag: String, container: Int, args: Bundle? = null) {
    val clazz = T::class.java
    var fragment = findFragmentByTag(clazz.name)
    if (fragment != null) {
        this.popBackStack(tag, 0)
    } else {
        fragment = (clazz.getConstructor().newInstance() as T).apply {
            args?.let {
                it.classLoader = javaClass.classLoader
                arguments = args
            }
        }
        val transaction = this.beginTransaction()
        transaction.add(container, fragment as Fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
}

//2
inline fun <reified T : Fragment> FragmentManager.getFragment(args: Bundle? = null): Fragment {
    val clazz = T::class.java
    return findFragmentByTag(clazz.name) ?: return (clazz.getConstructor().newInstance() as T).apply {
        args?.let {
            it.classLoader = javaClass.classLoader
            arguments = args
        }
    }
}

inline fun <T>show(manager: FragmentManager, fragment : T, fragmentTag: String, container: Int){
    val transaction = manager.beginTransaction()
    transaction.add(container, fragment as Fragment, fragmentTag)
    transaction.addToBackStack(fragmentTag)
    transaction.commit()
}