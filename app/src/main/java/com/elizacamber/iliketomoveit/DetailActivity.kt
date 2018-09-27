package com.elizacamber.iliketomoveit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var fabFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Glide.with(this).load(intent.getIntExtra(INTENT_CONTACT_AVATAR, 0)).into(iv_avatar)
        tv_name.text = intent.getStringExtra(INTENT_CONTACT_NAME)

        val animDrawable = getDrawable(R.drawable.avd_edit_done) as AnimatedVectorDrawable
        val animDrawable2 = getDrawable(R.drawable.avd_done_edit) as AnimatedVectorDrawable

        fab.setImageDrawable(animDrawable)

        fab.setOnClickListener {
            if (fabFlag) {
                // turn into done
                (fab.drawable as Animatable).start()
                fab.setImageDrawable(animDrawable)
            } else {
                // turn into pencil
                (fab.drawable as Animatable).start()
                fab.setImageDrawable(animDrawable2)
            }
            fabFlag = !fabFlag
        }
    }

    companion object {

        private val INTENT_CONTACT_AVATAR = "contact_avatar"
        private val INTENT_CONTACT_NAME = "contact_name"
        private val INTENT_CONTACT_PHONE = "contact_phone"

        fun newIntent(context: Context, contact: Contact): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_CONTACT_AVATAR, contact.avatarId)
            intent.putExtra(INTENT_CONTACT_NAME, contact.name)
            intent.putExtra(INTENT_CONTACT_PHONE, contact.phone)
            return intent
        }
    }
}
