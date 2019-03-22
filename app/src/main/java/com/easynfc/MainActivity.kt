package com.easynfc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.easynfc.ui.main.menu.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //show(supportFragmentManager, supportFragmentManager.getFragment<MenuFragment>(), MenuFragment.TAG , R.id.container)
        supportFragmentManager.showFragment<MenuFragment>(supportFragmentManager, MenuFragment.TAG, R.id.container)
        //FragmentUtils.show(supportFragmentManager, MenuFragment.TAG , R.id.container,null)
    }
}


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

inline fun <reified T : Fragment> FragmentManager.showFragment(manager: FragmentManager, tag: String, container: Int, args: Bundle? = null) {
    val clazz = T::class.java
    var fragment = findFragmentByTag(clazz.name)
    if (fragment != null) {
        manager.popBackStack(tag, 0)
    } else {
        fragment = (clazz.getConstructor().newInstance() as T).apply {
            args?.let {
                it.classLoader = javaClass.classLoader
                arguments = args
            }
        }
        val transaction = manager.beginTransaction()
        transaction.add(container, fragment as Fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
}