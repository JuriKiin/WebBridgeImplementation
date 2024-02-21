package com.jurikiin.simplewebviewbridge

import de.andycandy.android.bridge.CallType
import de.andycandy.android.bridge.DefaultJSInterface
import de.andycandy.android.bridge.NativeCall
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ExampleInterface: DefaultJSInterface("Android") {

    @NativeCall(CallType.FULL_PROMISE)
    fun relayMessage(message: String) = doInBackground {
        //Do stuff that's async here
        Thread.sleep(1000)
        it.resolve(message)
    }

    @NativeCall(CallType.FULL_PROMISE)
    fun respondWithDelay(delay: Long) = doInBackground {
        runBlocking {
            it.resolve(createDelay(delay))
        }
    }

    private suspend fun createDelay(delay: Long) = suspendCoroutine {
        Thread.sleep(delay)
        it.resume("Delayed for ${delay.toDouble() / 1000} seconds.")
    }

}