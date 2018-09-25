package com.elizacamber.iliketomoveit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main_start.*

class MainActivity : AppCompatActivity() {

    private val contacts = arrayListOf<Contact>()
    private val NUMBER_OF_ITEMS = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        getData()

        rv_contacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContactsAdapter(context, contacts) {
                startActivity(DetailActivity.newIntent(this@MainActivity, it))
            }
        }
    }

    private fun getData(){
        var count =0
        while (count < NUMBER_OF_ITEMS) {
            contacts.add(Contact(getDrawableForPosition(count+1), "XYZ", "645654654"))
            count ++
        }
    }

    private fun getDrawableForPosition(position:Int): Int? {
        val name = "avatar_$position"
        return resources.getIdentifier(name, "drawable",packageName)
    }
}
