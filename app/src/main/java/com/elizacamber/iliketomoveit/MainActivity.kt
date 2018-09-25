package com.elizacamber.iliketomoveit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main_start.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        getData()

//        rv_contacts.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(context)
//            adapter = ContactsAdapter(context, contacts)
//        }
    }

    private fun getData(){
        var count =0
        while (count < NUMBER_OF_ITEMS) {
            contacts.add(Contact(getDrawableForPosition(count), "XYZ", "645654654"))
            count ++
        }
    }

    fun getDrawableForPosition(position:Int): Int? {
        val name = "avatar_$position"
        return resources.getIdentifier(name, "drawable",packageName)
    }

    companion object {
        val contacts = arrayListOf<Contact>()
        const val NUMBER_OF_ITEMS = 8
    }
}
