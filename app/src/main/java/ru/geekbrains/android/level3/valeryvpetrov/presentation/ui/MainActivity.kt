package ru.geekbrains.android.level3.valeryvpetrov.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.geekbrains.android.level3.valeryvpetrov.Application
import ru.geekbrains.android.level3.valeryvpetrov.R
import ru.geekbrains.android.level3.valeryvpetrov.databinding.ActivityMainBinding
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.MainViewModel
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.ViewModelFactory
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

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

        viewModel.loadError.observe(this, Observer {
            binding.responseText.text = it.message
        })
        viewModel.user.observe(this, Observer {
            binding.responseText.text = it.toString()
        })
        viewModel.userRepos.observe(this, Observer {
            binding.responseText.text = "${binding.responseText.text}" +
                    "\n\nUser repositories: \n$it"
        })
    }
}

