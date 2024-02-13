package com.finance.hodl.notifications

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidSystemNotifier @Inject constructor() : Notifier {

    override fun notify(msg: String) {
        // TODO, create notification and display to the user
    }
}