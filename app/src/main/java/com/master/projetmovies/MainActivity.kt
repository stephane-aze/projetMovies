package com.master.projetmovies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.master.projetmovies.fragment.FavoritesFragment
import com.master.projetmovies.fragment.MoviesFragment
import com.master.projetmovies.fragment.ProfilesFragment
import com.master.projetmovies.fragment.SearchFragment

class MainActivity :AppCompatActivity(){
    private lateinit var bottomNav: BottomNavigationView
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.item_bande_film -> {
                replaceFragment(MoviesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorites -> {
                replaceFragment(FavoritesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.app_bar_search -> {
                replaceFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profiles -> {
                replaceFragment(ProfilesFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(MoviesFragment())
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }

}