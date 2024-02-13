package com.finance.hodl.notifications


/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun notify(msg: String)
}