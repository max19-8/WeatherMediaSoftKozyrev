package com.example.weathermediasoftkozyrev.domain.utils

data class Event<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Event<T> =
            Event(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Event<T> =
            Event(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Event<T> =
            Event(status = Status.LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}