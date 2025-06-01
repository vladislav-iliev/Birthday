package com.vladislaviliev.birthday.dependencies

import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.kid.Repository
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
    fun provideRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher, api: Api) =
        Repository(scope, dispatcher, api)
}