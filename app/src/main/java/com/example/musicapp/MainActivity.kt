package com.example.musicapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.example.musicapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var currentTab=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        MusicApp.musicComponent.inject(this)
        binding.tabBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (binding.tabBar.selectedTabPosition==0){
                    if (currentTab == 2) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_SecondFragment_to_FirstFragment)
                        currentTab = 1
                    }
                    if (currentTab == 3) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_ThirdFragment_to_FirstFragment)
                        currentTab = 1
                    }
                }


                if (binding.tabBar.selectedTabPosition==1){
                    if (currentTab == 1) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_FirstFragment_to_SecondFragment)
                        currentTab = 2
                    }
                    if (currentTab == 3) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_ThirdFragment_to_SecondFragment)
                        currentTab = 2
                    }
                }

                if (binding.tabBar.selectedTabPosition==2) {
                    if (currentTab == 1) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_FirstFragment_to_ThirdFragment)
                        currentTab = 3
                    }
                    if (currentTab == 2) {
                        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_SecondFragment_to_ThirdFragment)
                        currentTab = 3
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })



        }


}