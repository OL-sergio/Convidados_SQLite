package mvvm.roomdatabase.aplicacaoconvidados

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import mvvm.roomdatabase.aplicacaoconvidados.databinding.ActivityMainBinding
import mvvm.roomdatabase.aplicacaoconvidados.view.GuestFormActivity


class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding!!.appBarMain.toolbar)

        binding!!.appBarMain.fab.setOnClickListener { _ ->

            val intent = Intent(applicationContext, GuestFormActivity::class.java)
            startActivity(intent)
        }

        setupNavigation()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_close_app -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = binding!!.drawerLayout
        val navView: NavigationView = binding!!.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_todos, R.id.nav_presentes, R.id.nav_ausentes), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration!!)
        navView.setupWithNavController(navController)
    }
}