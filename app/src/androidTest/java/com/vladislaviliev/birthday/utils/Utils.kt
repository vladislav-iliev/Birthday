package com.vladislaviliev.birthday.utils

import com.vladislaviliev.birthday.kid.Repository
import com.vladislaviliev.birthday.test.DummyAvatarRepository
import com.vladislaviliev.birthday.test.DummyTextRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestScope

object Utils {
    fun TestScope.createRepos(): Repos {
        val textRepo = DummyTextRepository()
        val avatarRepo = DummyAvatarRepository()
        val kidRepo = Repository(this, this.coroutineContext[CoroutineDispatcher]!!, textRepo, avatarRepo)
        return Repos(textRepo, avatarRepo, kidRepo)
    }
}