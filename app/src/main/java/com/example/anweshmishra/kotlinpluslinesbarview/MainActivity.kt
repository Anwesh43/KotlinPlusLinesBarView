package com.example.anweshmishra.kotlinpluslinesbarview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pluslinesbarview.PlusLinesBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlusLinesBarView.create(this)
    }
}
