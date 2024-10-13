package com.solodev.animeloom.domain.usecase.appentry

import com.solodev.animeloom.domain.manager.LocalUserManager


class SaveAppEntry(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}
