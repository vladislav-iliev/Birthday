package com.vladislaviliev.birthday.dependencies

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.vladislaviliev.birthday.kid.Repository as KidRepository
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelContainer {

    @Provides
    @ViewModelScoped
    fun provideKidRepository(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        avatarRepository: AvatarRepository,
        textRepository: TextRepository,
    ):KidRepository = KidRepository(scope, dispatcher, textRepository, avatarRepository)
}