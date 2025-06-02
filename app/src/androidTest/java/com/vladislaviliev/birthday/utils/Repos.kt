package com.vladislaviliev.birthday.utils

import com.vladislaviliev.birthday.kid.Repository as KidRepository
import com.vladislaviliev.birthday.kid.avatar.Repository as AvatarRepository
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository

data class Repos(val textRepo: TextRepository, val avatarRepo: AvatarRepository, val kidRepo: KidRepository)