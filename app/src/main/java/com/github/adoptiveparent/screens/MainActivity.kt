package com.github.adoptiveparent.screens

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.github.adoptiveparent.NIGHT_MODE
import com.github.adoptiveparent.NO_FIRST_RUN_APP
import com.github.adoptiveparent.R
import com.github.adoptiveparent.SharedPref
import com.github.adoptiveparent.databinding.ActivityMainBinding
import com.github.adoptiveparent.screens.info.InfoFragment
import com.github.adoptiveparent.screens.adoption_center.AdoptionCenterFragment
import com.github.adoptiveparent.screens.child_protection.ChildProtectionFragment
import com.github.adoptiveparent.screens.court.CourtFragment
import com.github.adoptiveparent.screens.help.HelpFragment
import com.github.adoptiveparent.screens.home.HomeFragment
import com.github.adoptiveparent.screens.orphanage.OrphanageFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.properties.Delegates

const val TAG_LOG = "Adoptive Parent Belarus"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var nightMode by Delegates.notNull<Boolean>()
    private var isNotFirstRunApp by Delegates.notNull<Boolean>()
    private var defaultTheme by Delegates.notNull<Boolean>()
    private val sharedPref by lazy { SharedPref(this@MainActivity) }

    private val fragmentsList: List<Fragment> = listOf<Fragment>(
        InfoFragment(),
        ChildProtectionFragment(),
        AdoptionCenterFragment(),
        OrphanageFragment(),
        CourtFragment(),
        HomeFragment(),
        HelpFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()

        val tableTitleAndIcon: MutableMap<String, Int> = mutableMapOf(
            getString(R.string.start_page) to R.drawable.ic_info_problem_solve,      // Начало - Info
            getString(R.string.child_protection_page) to R.drawable.ic_step_1_airport,           // Опека - ChildProtection
            getString(R.string.ncu_page) to R.drawable.ic_step_2_happy,             // НЦУ - AdoptionCenter
            getString(R.string.orphanage_page) to R.drawable.ic_step_3_house3d,           // Дом ребенка - Orphanage
            getString(R.string.court_page) to R.drawable.ic_step_4_courthouse,        // Суд - Court
            getString(R.string.home_page) to R.drawable.ic_step_5_team_work,         // Дом - Home
            getString(R.string.help_page) to R.drawable.ic_help_call                // Помощь - Help
        )
        val tabTitle: ArrayList<String> = ArrayList(tableTitleAndIcon.keys)
        val tabIcon: ArrayList<Int> = ArrayList(tableTitleAndIcon.values)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tabItem, position ->
            tabItem.text = tabTitle[position]
            tabItem.setIcon(tabIcon[position])
            tabItem.icon?.alpha = 80
            when (position) {
                0 -> {
                    tabItem.icon?.alpha = 250
                    supportActionBar?.title = getString(R.string.app_name)
                }

                1 -> {
                }

                2 -> {
                }

                3 -> {
                }

                4 -> {

                }

                5 -> {

                }

                6 -> {

                }
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    tab.icon?.alpha = 250
                    supportActionBar?.title =
                        "${getString(R.string.app_name)}: " + tabTitle[tab.position]
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    tab.icon?.alpha = 80
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        if (nightMode) {
            menu?.getItem(0)?.setIcon(R.drawable.ic_menu_night_moon)
        } else {
            menu?.getItem(0)?.setIcon(R.drawable.ic_menu_day_sun)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_night_mode -> {
                changeTheme(item = item)
            }

            R.id.button_debug -> {
                showSendDebug()
            }

            R.id.button_version -> {
                showAppVersion()
            }

            R.id.button_license -> {
                showLicense()
            }

            R.id.button_exit -> {
                showToastMessage(R.string.menu_exit_send)
                finish()
            }
        }
        return true
    }

    private fun changeTheme(item: MenuItem) {
        if (nightMode) {
            nightMode = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPref.setValue(NIGHT_MODE, nightMode)
        } else {
            nightMode = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPref.setValue(NIGHT_MODE, nightMode)
        }
    }

    private fun showSendDebug() {
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder
            .setTitle(getString(R.string.menu_debug))
            .setMessage(getString(R.string.menu_debug_send))
            .setCancelable(true)
            .setIcon(R.drawable.ic_menu_email_3d_mail)
            .setPositiveButton(R.string.menu_debug_button_send_email,
                DialogInterface.OnClickListener { dialog, i ->
                    sendMailDebug()
                })
            .setNegativeButton(R.string.menu_button_closed,
                DialogInterface.OnClickListener { dialog, i ->
                    dialog.cancel()
                })
            .create()
        dialogBuilder.show()
    }

    private fun showAppVersion() {
        val currentVersionApp = getAppVersion()
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder
            .setTitle(getString(R.string.app_name))
            .setMessage(
                String.format(
                    this.getString(R.string.menu_version_current),
                    currentVersionApp
                )
            )
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(
                getString(R.string.menu_button_closed),
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            .setNeutralButton(
                R.string.menu_version_button_github,
                DialogInterface.OnClickListener { dialog, i ->
                    gotoBrowser()
                    showToastMessage(R.string.menu_version_open_github)
                }
            )
            .create()
        dialogBuilder.show()
    }

    private fun sendMailDebug() {
        val sendEmailIntent = Intent(Intent.ACTION_SENDTO)
        sendEmailIntent.data = Uri.parse("mailto:")
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.menu_debug_send_email_url))
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.menu_debug_email_subject))
        if (sendEmailIntent.resolveActivity(packageManager) != null) startActivity(sendEmailIntent)
        else showToastMessage(R.string.send_no_app)
    }

    private fun gotoBrowser() {
        val openBrowserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.menu_debug_url_github)))
        startActivity(openBrowserIntent)
    }

    private fun showLicense() {
        val license =
            getLicense()?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder
            .setTitle(getString(R.string.menu_license))
            .setMessage(license)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(
                getString(R.string.menu_button_closed),
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            .create()
        dialogBuilder.show()
    }

    fun getLicense(): String? {
        return try {
            readTextFile(this, R.raw.license)
        } catch (e: IOException) {
            Log.w(TAG_LOG, "Error read file License", e)
            "Error ${e.message.toString()}"
        }
    }

    fun getAppVersion(): String {
        var version = "Null info"
        try {
            val pi = this.packageManager.getPackageInfo(
                this.packageName, 0
            )
            version = pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.w(TAG_LOG, "Package name not found", e)
        }
        return version
    }

    fun readTextFile(context: Context, @RawRes resourceId: Int): String {
        val inputResource = context.resources.openRawResource(resourceId)
        val readerResource =
            BufferedReader(InputStreamReader(inputResource, StandardCharsets.UTF_8))
        val resultResource = StringBuilder()
        while (true) {
            val nextLine = readerResource.readLine()
            if (nextLine == null) {
                readerResource.close()
                break
            }
            resultResource.append("\n")
            resultResource.append(nextLine)
        }
        return resultResource.toString()
    }

    private fun showToastMessage(messageRes: Int) {
        Toast.makeText(this@MainActivity, messageRes, Toast.LENGTH_SHORT).show()
    }

    private fun initial() {
        defaultTheme = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                || (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        val default2 = AppCompatDelegate.getDefaultNightMode()              // delete
        Log.w(TAG_LOG, "default = $default2")                           // delete
        isNotFirstRunApp = sharedPref.getValue(NO_FIRST_RUN_APP)
        if (!isNotFirstRunApp) {
            sharedPref.setValue(NIGHT_MODE, defaultTheme)
            sharedPref.setValue(NO_FIRST_RUN_APP, true)
        }
        nightMode = sharedPref.getValue(NIGHT_MODE)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val adapterViewPager =
            ViewPagerAdapter(fragmentActivity = this@MainActivity, fragmentsList = fragmentsList)
        binding.viewPager.adapter = adapterViewPager
        binding.tabLayout.tabIconTint = null
    }

}