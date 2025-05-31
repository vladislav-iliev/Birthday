package com.vladislaviliev.birthday.dependencies

import com.vladislaviliev.birthday.kid.KidApi
import com.vladislaviliev.birthday.kid.KidRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelContainer {

    @Provides
    @ViewModelScoped
    fun provideKidsRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher, kidApi: KidApi) =
        KidRepository(scope, dispatcher, kidApi)
}