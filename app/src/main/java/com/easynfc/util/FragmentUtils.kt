package com.vipera.onepay.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.easynfc.ui.menu.MenuFragment
import com.easynfc.ui.mytags.MyTagsFragment
import com.easynfc.ui.read.ReadFragment
import com.easynfc.ui.tags.TagsFragment
import com.easynfc.ui.write.WriteFragment


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
            when (fragmentTag) {
                MenuFragment.TAG -> return getMenuFragment(manager)
                TagsFragment.TAG -> return getTagsFragment(manager)
                ReadFragment.TAG -> return getReadFragment(manager)
                MyTagsFragment.TAG -> return getMyTagsFragment(manager)
                WriteFragment.TAG -> return getWriteFragment(manager)
            }
            return null
        }

        /*private fun getQRPaymentDeclineFragment(manager: FragmentManager, data: Any?): Fragment? {
               var fragment = manager.findFragmentByTag(AppConstants.QR_PAYMENT_DECLINE_FRAGMENT) as QRPaymentDeclineFragment?
               if (fragment == null && data is PaymentsRequestResponse) {
                   fragment = QRPaymentDeclineFragment.newInstance(data)
               }
               return fragment
           }*/

        private fun getMenuFragment(manager: FragmentManager): Fragment {
            var fragment = manager.findFragmentByTag(MenuFragment.TAG) as MenuFragment?
            if (fragment == null) {
                fragment = MenuFragment.newInstance()
            }
            return fragment
        }

        private fun getTagsFragment(manager: FragmentManager): Fragment {
            var fragment = manager.findFragmentByTag(TagsFragment.TAG) as TagsFragment?
            if (fragment == null) {
                fragment = TagsFragment.newInstance()
            }
            return fragment
        }

        private fun getReadFragment(manager: FragmentManager): Fragment {
            var fragment = manager.findFragmentByTag(ReadFragment.TAG) as ReadFragment?
            if (fragment == null) {
                fragment = ReadFragment.newInstance()
            }
            return fragment
        }

        private fun getMyTagsFragment(manager: FragmentManager): Fragment {
            var fragment = manager.findFragmentByTag(MyTagsFragment.TAG) as MyTagsFragment?
            if (fragment == null) {
                fragment = MyTagsFragment.newInstance()
            }
            return fragment
        }

        private fun getWriteFragment(manager: FragmentManager): Fragment {
            var fragment = manager.findFragmentByTag(WriteFragment.TAG) as WriteFragment?
            if (fragment == null) {
                fragment = WriteFragment.newInstance()
            }
            return fragment
        }
    }
}