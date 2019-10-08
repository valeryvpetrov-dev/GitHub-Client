package ru.geekbrains.android.level3.valeryvpetrov.di.component

import dagger.Component
import ru.geekbrains.android.level3.valeryvpetrov.di.ScreenScope
import ru.geekbrains.android.level3.valeryvpetrov.di.module.ViewModelModule
import ru.geekbrains.android.level3.valeryvpetrov.presentation.ui.MainActivity

@Component(
    dependencies = [
        UserComponent::class
    ],
    modules = [
        ViewModelModule::class
    ]
)
@ScreenScope
interface UserRepoItemsScreenComponent {

    fun inject(mainActivity: MainActivity)
}