package ru.geekbrains.android.level3.valeryvpetrov.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import ru.geekbrains.android.level3.valeryvpetrov.Application
import ru.geekbrains.android.level3.valeryvpetrov.R
import ru.geekbrains.android.level3.valeryvpetrov.databinding.ActivityMainBinding
import ru.geekbrains.android.level3.valeryvpetrov.di.component.UserRepoItemsScreenComponent
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.Event
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.MainViewModel
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.UseCaseResult
import ru.geekbrains.android.level3.valeryvpetrov.util.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        var leakReference: Context? = null
    }

    var screenComponent: UserRepoItemsScreenComponent? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var repoItemsAdapter: RepoItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenComponent = (application as Application).addUserRepoItemsScreenComponent()
        screenComponent?.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel

        configureActionBar()
        configureUserRepoItemsRecycler()
        setObservers()
    }

    override fun onDestroy() {
        (application as Application).removeUserRepoItemsScreenComponent()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_trigger_leak_memory) {
            leakReference = applicationContext
            finish()
            return true
        } else if (item.itemId == R.id.action_trigger_firebase_crashlytics) {
            Crashlytics.getInstance().crash()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun configureUserRepoItemsRecycler() {
        userRepoItemsRecycler.layoutManager = LinearLayoutManager(this)
        repoItemsAdapter = RepoItemsAdapter(listOf())
        userRepoItemsRecycler.adapter = repoItemsAdapter
        userRepoItemsRecycler.setHasFixedSize(true)
    }

    private fun setObservers() {
        val getUserResultObserver = Observer<UseCaseResult<User, Event<Throwable>>> { result ->
            when (result) {
                is UseCaseResult.Success<User, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.data?.repoItems?.let { repoItemsAdapter.setItems(it) }
                }
                is UseCaseResult.Error<User, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.error?.getContentIfNotHandled()?.let { throwable ->
                        throwable.message?.let {
                            toast(it)
                        }
                    }
                }
                is UseCaseResult.Loading<User, Event<Throwable>> -> {
                    showLoadProgress()
                }
            }
        }
        viewModel.getUserRemoteResult.observe(this, getUserResultObserver)
        viewModel.getUserLocalResult.observe(this, getUserResultObserver)
        viewModel.saveUserResult.observe(this, Observer { result ->
            when (result) {
                is UseCaseResult.Success<Event<Boolean>, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.data?.getContentIfNotHandled()?.let {
                        if (it) toast("User has been saved successfully")
                        else toast("User has not been saved")
                    }
                }
                is UseCaseResult.Error<Event<Boolean>, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.error?.getContentIfNotHandled()?.let {
                        toast(it.message ?: "Save user result error")
                    }
                }
                is UseCaseResult.Loading<Event<Boolean>, Event<Throwable>> -> {
                    showLoadProgress()
                }
            }
        })
        viewModel.deleteUserResult.observe(this, Observer { result ->
            when (result) {
                is UseCaseResult.Success<Event<Boolean>, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.data?.getContentIfNotHandled()?.let {
                        if (it) toast("User has been deleted successfully")
                        else toast("User has not been deleted")
                    }
                    repoItemsAdapter.clear()
                }
                is UseCaseResult.Error<Event<Boolean>, Event<Throwable>> -> {
                    hideLoadProgress()
                    result.error?.getContentIfNotHandled()?.let {
                        toast(it.message ?: "Delete user error")
                    }
                }
                is UseCaseResult.Loading<Event<Boolean>, Event<Throwable>> -> {
                    showLoadProgress()
                }
            }
        })
    }

    private fun showLoadProgress() {
        binding.activityMainContent.loadProgress.visibility = View.VISIBLE
        binding.activityMainContent.userRepoItemsRecycler.visibility = View.INVISIBLE
    }

    private fun hideLoadProgress() {
        binding.activityMainContent.loadProgress.visibility = View.INVISIBLE
        binding.activityMainContent.userRepoItemsRecycler.visibility = View.VISIBLE
    }
}

