package com.example.viewmodel11

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.Navigation
import com.example.viewmodel11.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private  val PREFS_NAME = "MyPrefsFile"
    private  val IS_FIRST_TIME = "isFirstTime"
    lateinit var toogle : ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    //private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstTime = sharedPrefs.getBoolean(IS_FIRST_TIME, true)
        setContentView(binding.root)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout= findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setBackgroundColor(getResources().getColor(R.color.gray))
        navView.itemIconTintList=null


        toogle= ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        if (isFirstTime) {
            // Es la primera vez, navegar a TutorialFragment
            supportFragmentManager.beginTransaction().replace(R.id.FragmentContainer, Tutorial())
                .commit()
            navView.setCheckedItem(R.id.tutorial_nav)
            // Actualizar el valor de isFirstTime en SharedPreferences
            with(sharedPrefs.edit()) {
                putBoolean(IS_FIRST_TIME, false)
                apply()
            }
        } else {
            // No es la primera vez, mostrar el fragmento CreatePdf
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, CreatePdf())
                .commit()
            navView.setCheckedItem(R.id.createPdf_nav)
        }

   /*    if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.FragmentContainer, CreatePdf())
                .commit()
            navView.setCheckedItem(R.id.createPdf_nav)
        }*/

        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when (it.itemId) {


                R.id.nav_1 -> replaceFragment(GeneralesFragment1())
                R.id.nav_2 -> replaceFragment(InicioFragment2())
                R.id.nav_3 -> replaceFragment(Identidad3())
                R.id.nav_4 -> replaceFragment(Documentacion4())
                R.id.nav_5 -> replaceFragment(Recoleccion5())
                R.id.nav_6 -> replaceFragment(Embalaje6())
                R.id.nav_7 -> replaceFragment(ServPub7())
                R.id.nav_8 -> replaceFragment(Traslado8())
                R.id.createPdf_nav->replaceFragment(CreatePdf())

                R.id.acerca-> {
                val url = "https://cadenadecustodia-movil.blogspot.com/2024/04/cadena-de-custodia-movil.html"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
                R.id.politica->{
                    val url = "https://www.termsfeed.com/live/a352a5e4-81e2-4afc-bf1c-d603c1760c17"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                R.id.nav_face->{
                    val url = "https://www.facebook.com/profile.php?id=61558247800618"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                R.id.nav_mess->{
                    val url = "https://m.me/310917218762714"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                R.id.tutorial_nav->replaceFragment(Tutorial())

            }

            true
        }


    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentContainer,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }

}