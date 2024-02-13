package com.finance.hodl.notifications

import javax.inject.Inject

class NoOpNotifier @Inject constructor() : Notifier {
    override fun notify(msg: String) = Unit
}