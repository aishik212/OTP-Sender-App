package com.example.basiccontactappwithotpsender

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.databinding.ActivityMainBinding
import com.example.basiccontactappwithotpsender.fragments.HomeFragment
import com.example.basiccontactappwithotpsender.fragments.MessagesFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var inflate: ActivityMainBinding
    }

    @Inject
    lateinit var messageDao: MessageDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        val homeFragment = HomeFragment()
        val messagesFragment = MessagesFragment()

        inflate.tabLayout.setupWithViewPager(inflate.HomeViewPager);


        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(homeFragment, "Home")
        viewPagerAdapter.addFragment(messagesFragment, "Sent Messages")
        inflate.HomeViewPager.adapter = viewPagerAdapter

    }

    //Initiate Viewpager and fragments
    private class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fm, behavior) {
        private val fragments: MutableList<Fragment> = ArrayList()
        private val fragmentTitles: MutableList<String> = ArrayList()

        //add fragment to the viewpager
        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            fragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        //to setup title of the tab layout
        @Nullable
        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles[position]
        }
    }
}