package com.passengers.anroidapp.navigation

import androidx.fragment.app.Fragment
import com.passengers.anroidapp.feature.chat.ChatFragment
import com.passengers.anroidapp.feature.news.NewsFragment
import com.passengers.anroidapp.feature.notification.NotificationsFragment
import com.passengers.anroidapp.feature.settings.SettingsFragment
import com.passengers.anroidapp.feature.special.SpecialsFragment
import com.passengers.anroidapp.feature.special_details.SpecialDetailsFragment
import com.passengers.anroidapp.network.model.FeedItem
import ru.terrakok.cicerone.android.support.SupportAppScreen

class NewsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return NewsFragment()
    }
}

class NotificationsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return NotificationsFragment()
    }
}

class SpecialsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return SpecialsFragment()
    }
}

class ChatScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return ChatFragment()
    }
}

class SettingsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return SettingsFragment()
    }
}


class SpecialDetailsScreen(private val feedItem: FeedItem) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return SpecialDetailsFragment.newInstance(feedItem)
    }
}