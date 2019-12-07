package com.passengers.anroidapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.passengers.anroidapp.core.BaseActivity
import com.passengers.anroidapp.feature.chat.ChatViewModel
import com.passengers.anroidapp.feature.news.NewsViewModel
import com.passengers.anroidapp.feature.notification.NotificationsViewModel
import com.passengers.anroidapp.feature.settings.SettingsViewModel
import com.passengers.anroidapp.feature.special.SpecialsViewModel
import com.passengers.anroidapp.navigation.*


class MainActivity : BaseActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var newsViewModel = ViewModelProviders.of(this)[NewsViewModel::class.java]
        var notificationsViewModel = ViewModelProviders.of(this)[NotificationsViewModel::class.java]
        var specialsViewModel = ViewModelProviders.of(this)[SpecialsViewModel::class.java]
        var chatViewModel = ViewModelProviders.of(this)[ChatViewModel::class.java]
        var settingsViewModel = ViewModelProviders.of(this)[SettingsViewModel::class.java]

        onFindViews()
        onBindViews()

        bottomNavigationView.selectedItemId = R.id.navigation_news
    }

    fun onFindViews() {
        toolbar = findViewById(R.id.toolbar)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }

    fun onBindViews() {
        setSupportActionBar(toolbar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_news -> router?.navigateTo(NewsScreen()).apply {
                    supportActionBar?.setTitle(R.string.news)
                }
                R.id.navigation_notifications -> router?.navigateTo(NotificationsScreen()).apply {
                    supportActionBar?.setTitle(R.string.notifications)
                }
                R.id.navigation_specials -> router?.navigateTo(SpecialsScreen()).apply {
                    supportActionBar?.setTitle(R.string.specials)
                }
                R.id.navigation_chat -> router?.navigateTo(ChatScreen()).apply {
                    supportActionBar?.setTitle(R.string.chat)
                }
                R.id.navigation_settings -> router?.navigateTo(SettingsScreen()).apply {
                    supportActionBar?.setTitle(R.string.settings)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
