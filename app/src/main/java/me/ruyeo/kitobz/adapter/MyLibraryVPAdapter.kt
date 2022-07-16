package me.ruyeo.kitobz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyLibraryVPAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var fragments: ArrayList<Fragment> = ArrayList()
    private var titles: ArrayList<String> = ArrayList()

    fun addFragmentAndTitle(fragment: Fragment, title: String){
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}