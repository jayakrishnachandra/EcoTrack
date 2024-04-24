package com.example.bottomnavyt

import DashboardFragment
import ProfileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.bottomnavyt.fragments.RecordsFragment
import com.example.bottomnavyt.fragments.WalletFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the default fragment
        replaceFragment(DashboardFragment())

        // Set listener for bottom navigation view item selection
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_dashboard -> replaceFragment(DashboardFragment())
                R.id.action_records -> replaceFragment(RecordsFragment())
                R.id.action_wallet -> replaceFragment(WalletFragment())
                R.id.action_profile -> replaceFragment(ProfileFragment())
                else -> return@setOnItemSelectedListener false
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
