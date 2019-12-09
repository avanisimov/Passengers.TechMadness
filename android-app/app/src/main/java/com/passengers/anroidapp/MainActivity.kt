package com.passengers.anroidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.passengers.anroidapp.core.BaseActivity
import com.passengers.anroidapp.core.RosbankApplication.Companion.ARG_ITEM
import com.passengers.anroidapp.navigation.SettingsScreen
import com.passengers.anroidapp.navigation.SpecialDetailsScreen
import com.passengers.anroidapp.navigation.SpecialsScreen
import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.model.PushToken
import com.passengers.anroidapp.network.model.PushTokenPlatform
import com.passengers.anroidapp.network.repo.MockCollection
import com.passengers.anroidapp.network.repo.push.PushRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : BaseActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar
    val pushRepository: PushRepository by inject()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) handleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onFindViews()
        onBindViews()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult: InstanceIdResult ->
            val newToken = instanceIdResult.token
            Timber.i(" token = %s", newToken)

            pushRepository
                    .pushToken(PushToken(MockCollection.userID, newToken, PushTokenPlatform.ANDROID))
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        Timber.i("PushToken sended, token = %s", newToken)
                    }
                    .subscribe()
            this.getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply()
        }

        bottomNavigationView.selectedItemId = R.id.navigation_specials

        val intent: Intent? = intent
        if (intent != null) {
            handleIntent(intent)
        }

    }

    fun onFindViews() {
        toolbar = findViewById(R.id.toolbar)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }

    fun onBindViews() {
        setSupportActionBar(toolbar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                /* R.id.navigation_news -> router?.replaceScreen(NewsScreen()).apply {
                     supportActionBar?.setTitle(R.string.news)
                 }
                 R.id.navigation_notifications -> router?.replaceScreen(NotificationsScreen()).apply {
                     supportActionBar?.setTitle(R.string.notifications)
                 }*/
                R.id.navigation_specials -> router?.replaceScreen(SpecialsScreen()).apply {
                    supportActionBar?.setTitle(R.string.specials)
                }
                /*  R.id.navigation_chat -> router?.replaceScreen(ChatScreen()).apply {
                      supportActionBar?.setTitle(R.string.chat)
                  }*/
                R.id.navigation_settings -> router?.replaceScreen(SettingsScreen()).apply {
                    supportActionBar?.setTitle(R.string.settings)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.hasExtra(ARG_ITEM)) {
            val feedItem: FeedItem = intent.getSerializableExtra(ARG_ITEM) as FeedItem
            router?.navigateTo(SpecialDetailsScreen(feedItem))
        }
    }

}