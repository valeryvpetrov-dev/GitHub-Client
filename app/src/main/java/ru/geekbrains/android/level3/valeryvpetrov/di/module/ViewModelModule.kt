package ru.geekbrains.android.level3.valeryvpetrov.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.geekbrains.android.level3.valeryvpetrov.di.ViewModelKey
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.DeleteUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserLocalUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserRemoteUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.SaveUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.MainViewModel
import ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter.ViewModelFactory
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import javax.inject.Provider

@Module
class ViewModelModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
//
//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(
        connectivityManager: ConnectivityManager,
        getUserRemoteUseCase: GetUserRemoteUseCase,
        getUserLocalUseCase: GetUserLocalUseCase,
        saveUserUseCase: SaveUserUseCase,
        deleteUserUseCase: DeleteUserUseCase
    ): ViewModel =
        MainViewModel(
            connectivityManager,
            getUserRemoteUseCase,
            getUserLocalUseCase,
            saveUserUseCase,
            deleteUserUseCase
        )

    @Provides
    fun viewModelFactory(map: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory =
        ViewModelFactory(map)

}