package com.mudassir.mydonations.UI.Main.HomeFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.mudassir.mydonations.R
import com.mudassir.mydonations.UI.Auth.LoginActivity
import com.mudassir.mydonations.UI.Main.DonationFragment.AddRequestActivity
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.UI.NavDrawer.ContactUsActivity
import com.mudassir.mydonations.UI.NavDrawer.FeedbackActivity
import com.mudassir.mydonations.UI.NavDrawer.HelpAndSupportActivity
import com.mudassir.mydonations.UI.NavDrawer.ProfileActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Floating Action Button
        val floatingBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // Check if user is admin and set up button accordingly
        if (isUserAdmin()) {
            // If the user is an admin, show the button and set the click listener
            floatingBtn.visibility = View.VISIBLE
            floatingBtn.setOnClickListener {
                val intent = Intent(this, AddRequestActivity::class.java)
                startActivity(intent)
            }
        } else {
            // If not an admin, hide the button
            floatingBtn.visibility = View.GONE
        }

        // Toolbar setup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up Bottom Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        // Set up Drawer
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val imageView = findViewById<ImageView>(R.id.drawer_icon)
        imageView.setOnClickListener { view: View? ->
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        // ViewModel setup
        val viewModel = MainViewModel()

        lifecycleScope.launch {
            viewModel.isSucessfullySaved.collect {
                it?.let {
                    if (it == true)
                        Toast.makeText(this@MainActivity, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isFailure.collect {
                it?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Check if the current user is an admin
    private fun isUserAdmin(): Boolean {
        val user: FirebaseUser? = AuthRepository().getCurrentUser()
        return user?.email.equals("ranamudassirali9@gmail.com", ignoreCase = true)
    }

    // Handle Navigation item selection
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val viewModel = MainViewModel()

        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))

            }
            R.id.feedback -> {
                startActivity(Intent(this, FeedbackActivity::class.java))
            }
            R.id.help -> {
                startActivity(Intent(this, HelpAndSupportActivity::class.java))
            }
            R.id.about -> {
                startActivity(Intent(this, ContactUsActivity::class.java))

            }
            R.id.logout -> {
                lifecycleScope.launch {
                    viewModel.currentUser.collect {
                        if (it == null) {
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            finish()
                        }
                        // TODO: Display user data in a nav drawer
                    }
                }
            }
        }
        return true
    }
}
