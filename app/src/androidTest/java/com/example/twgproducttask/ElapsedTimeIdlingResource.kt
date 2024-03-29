package com.example.twgproducttask

import android.support.test.espresso.IdlingResource


class ElapsedTimeIdlingResource(durationMillis:Long): IdlingResource {
    private var   startTime:Long? = null
    private var waitingTime:Long?= null
    private var resourceCallback: IdlingResource.ResourceCallback?= null
    init{
        startTime = System.currentTimeMillis()
        waitingTime = durationMillis
    }

    override fun getName(): String {
        return ElapsedTimeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime!!
        val idle = elapsed >= waitingTime!!
        if (idle) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
    companion object {


    /*fun waitAndRun(millis: Long, runnable: Runnable) {
        val idlingResource = ElapsedTimeIdlingResource(millis)
        registerIdlingResources(idlingResource)

        runnable.run()

        unregisterIdlingResources(idlingResource)
    }*/
    }
}