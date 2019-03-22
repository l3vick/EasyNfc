package com.vipera.onepay.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.easynfc.ui.main.menu.MenuFragment
import com.easynfc.ui.main.menu.mytags.MyTagsFragment
import com.easynfc.ui.main.read.ReadFragment
import com.easynfc.ui.main.tags.TagsFragment
import com.easynfc.ui.main.write.WriteFragment


class FragmentUtils {

    companion object {

        fun show(manager: FragmentManager, fragmentTag: String, container: Int, data: Any?): Companion {
            var fragment = manager.findFragmentByTag(fragmentTag)
            if (fragment != null) {
                manager.popBackStack(fragmentTag, 0)
            } else {
                addFragment(manager, getFragment(fragmentTag, manager, data)!!, container, fragmentTag)
            }
            return this
        }

        private fun addFragment(manager: FragmentManager, fragment: Fragment, container: Int, fragmentTag: String) {
            val transaction = manager.beginTransaction()
            transaction.add(container, fragment, fragmentTag)
            transaction.addToBackStack(fragmentTag)
            transaction.commit()
        }

        fun remove(manager: FragmentManager, fragmentTag: String) {
            var fragment = manager.findFragmentByTag(fragmentTag)
            if (fragment != null) {
                removeFragment(manager,
                        fragment)
            }
        }

        private fun removeFragment(manager: FragmentManager,
                                   fragment: Fragment) {
            val trans = manager.beginTransaction()
            trans.remove(fragment)
            trans.commit()
        }

        //-------------------------------------------------------------------------------------------------------------------------------------------

        private fun getFragment(fragmentTag: String, manager: FragmentManager, data: Any?): Fragment? {
            return manager.findFragmentByTag(fragmentTag) ?: when (fragmentTag) {
                MenuFragment.TAG -> MenuFragment.newInstance()
                TagsFragment.TAG -> TagsFragment.newInstance()
                ReadFragment.TAG -> ReadFragment.newInstance()
                MyTagsFragment.TAG -> MyTagsFragment.newInstance()
                WriteFragment.TAG -> WriteFragment.newInstance()
                else -> null
            }
        }

    }
}