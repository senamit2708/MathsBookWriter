package com.example.mathsbookwriter.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mathsbookwriter.R
import com.google.android.material.navigation.NavigationView

class SchoolDiaryMainActivity : AppCompatActivity() {

    private val TAG = SchoolDiaryMainActivity::class.java.simpleName

    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_diary_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment =
                supportFragmentManager
                        .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupActionBar(navController)
        setupNavigationMenu(navController)


    }

    private fun setupNavigationMenu(navController: NavController) {
        findViewById<NavigationView>(R.id.nav_view)?.let { navigationView ->
            NavigationUI.setupWithNavController(navigationView, navController)
        }
    }

    private fun setupActionBar(navController: NavController) {
        drawerLayout = findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,
                Navigation.findNavController(this, R.id.my_nav_host_fragment))

        onBackPressed()
        return true
    }
}
