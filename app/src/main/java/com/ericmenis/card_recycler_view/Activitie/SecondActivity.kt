package com.ericmenis.card_recycler_view.Activitie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.ericmenis.card_recycler_view.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        var intent = intent
        val sName = intent.getStringExtra("name")
        val sImage = intent.getIntExtra("image", 0)
        val sDesc = intent.getStringExtra("description")

        actionBar?.title = "$sName  DRAGON BALL Z"
        nameDBZ.text = sName
        descriptionDBZ.text = sDesc
        imageView.setImageResource(sImage)

    }
}
