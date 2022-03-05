package raghav.sharma.mealsapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import raghav.sharma.mealsapp.databinding.ActivityMainBinding


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var burgerMenu: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        burgerMenu = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(burgerMenu)
        burgerMenu.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawer_menu_home_item -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, ViewPagerFragment(), null).commit()
                    true
                }
                R.id.drawer_menu_filter_item ->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, FiltersFragment(), null).commit()
                    true
                }
                else -> {
                    throw IllegalStateException("$it was not defined in the layout.")
                }
            }
        }

        Log.d(TAG, "onCreate called")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(burgerMenu.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
