package com.vladislaviliev.birthday.dependencies

import android.content.Context
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import com.vladislaviliev.birthday.kids.KidsApi
import com.vladislaviliev.birthday.kids.KidsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelContainer {

    @Provides
    @ViewModelScoped
    fun provideAvatarSaver(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
    ) = AvatarRepository(context, scope, dispatcher)

    @Provides
    @ViewModelScoped
    fun provideKidsRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher, kidsApi: KidsApi) =
        KidsRepository(scope, dispatcher, kidsApi)
}