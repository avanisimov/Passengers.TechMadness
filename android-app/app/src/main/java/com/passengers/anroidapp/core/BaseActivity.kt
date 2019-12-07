package com.passengers.anroidapp.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.passengers.anroidapp.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Toothpick
import toothpick.config.Module
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    protected var router: Router? = null
    var activityScopeKey: String? = null

    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposables: Disposable) {
        for (d in disposables) {
            compositeDisposable.add(d)
        }
    }


    private val viewModels: MutableSet<BaseViewModel> = HashSet()

    protected fun addViewModels(vararg viewModels: BaseViewModel) {
        this.viewModels.addAll(viewModels)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = RosbankApplication.INSTANCE.getRouter()
        activityScopeKey = if (savedInstanceState != null) {
            savedInstanceState.getString(RosbankApplication.SCOPE_KEY)
        } else {
            this.toString()
        }
        val scope = Toothpick.openScopes(
                RosbankApplication.APP_SCOPE_KEY,
                activityScopeKey
        )
        scope.installModules(onCreateModule())
        Toothpick.inject(this, scope)
    }

    override fun onResume() {
        super.onResume()
        RosbankApplication.INSTANCE.getNavigatorHolder()!!.setNavigator(SupportAppNavigator(this, R.id.container))
    }

    override fun onPause() {
        super.onPause()
        RosbankApplication.INSTANCE.getNavigatorHolder()!!.removeNavigator()
    }

    protected fun onCreateModule(): Module {
        return Module()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            compositeDisposable.clear()
            for (viewModel in viewModels) {
                viewModel.onCleared()
            }
            compositeDisposable.clear()
            Toothpick.closeScope(activityScopeKey)
        }
    }
}