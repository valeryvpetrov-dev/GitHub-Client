package ru.geekbrains.android.level3.valeryvpetrov.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import ru.geekbrains.android.level3.valeryvpetrov.Application
import ru.geekbrains.android.level3.valeryvpetrov.R
import ru.geekbrains.android.level3.valeryvpetrov.databinding.ActivityMainBinding
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.MainViewModel
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.ViewModelFactory
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var repoItemsAdapter: RepoItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance(
                ConnectivityManager(application),
                (application as Application).appExecutors,
                (application as Application).retrofitGithub
            )
        )
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        userRepoItemsRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        repoItemsAdapter = RepoItemsAdapter(listOf())
        userRepoItemsRecycler.adapter = repoItemsAdapter
        userRepoItemsRecycler.setHasFixedSize(true)

        viewModel.loadError.observe(this, Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                .show()
        })
        viewModel.user.observe(this, Observer { user ->
            user.repoItems?.let { it -> repoItemsAdapter.setItems(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_logs -> TODO()
        }
        return super.onOptionsItemSelected(item)
    }
}

