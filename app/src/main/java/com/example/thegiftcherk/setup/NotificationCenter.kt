package com.example.thegiftcherk.setup

import androidx.lifecycle.MutableLiveData


object NotificationCenter {

    val state = MutableLiveData<String>()
    val body = MutableLiveData<String>()
//    val cancelReason = MutableLiveData<String>()
}

enum class ServiceState(val value: String) {
    CREATE("didCreateService"),
    CANCEL("didCancelService"),
    ACCEPTED("alreadyAccepted"),
    CANCELED("clientCanceled"),
    TIME("sortByTime"),
    DISTANCE("sortByDistance")

}

