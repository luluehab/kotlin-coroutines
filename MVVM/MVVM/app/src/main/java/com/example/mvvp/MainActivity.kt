package com.example.mvvp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvp.AllActivity.view.AllActivity
import com.example.mvvp.cartActivity.view.CartActivity

class MainActivity : AppCompatActivity() {

    private lateinit var all:Button
    private lateinit var fav:Button
    private lateinit var exit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        all = findViewById(R.id.allBtn)
        fav = findViewById(R.id.addBtn)
        exit = findViewById(R.id.exitBtn)

        all.setOnClickListener { view: View? ->
            val intent =
                Intent(
                    this@MainActivity,
                    AllActivity::class.java
                )
            startActivity(intent)
        }
       fav.setOnClickListener(View.OnClickListener { view: View? ->
            val intent =
                Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
            startActivity(intent)
        })
        exit.setOnClickListener { view: View? ->
            moveTaskToBack(
                true
            )
        }

    }
}