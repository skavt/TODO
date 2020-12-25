package com.cst.todotasks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cst.todotasks.R
import com.cst.todotasks.extensions.replaceFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        replaceFragment(R.id.fragment_container, TaskListFragment.createInstance())
    }

}