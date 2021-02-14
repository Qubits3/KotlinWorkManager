package com.example.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intKey", 1).build()

        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()
/*
        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
//            .setInitialDelay(5, TimeUnit.SECONDS)
//            .addTag("myTag")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

 */

        val myWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<RefreshDatabase>(15, TimeUnit.MINUTES)
                .setInputData(data)
                .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, {
            if (it.state == WorkInfo.State.RUNNING) {
                println("Running")
            } else if (it.state == WorkInfo.State.FAILED) {
                println("Failed")
            }
        })

//        WorkManager.getInstance(this).cancelWorkById(myWorkRequest.id)

        /*
        //Chaining

        val oneTimeRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(this)
            .beginWith(oneTimeRequest)
            .then(oneTimeRequest)
            .then(oneTimeRequest)
            .enqueue()

         */

        
    }
}