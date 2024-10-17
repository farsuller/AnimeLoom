package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.appentry.ReadAppEntry
import com.solodev.animeloom.domain.usecase.appentry.SaveAppEntry

class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
)
