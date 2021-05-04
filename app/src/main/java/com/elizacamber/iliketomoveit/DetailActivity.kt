package com.elizacamber.iliketomoveit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GestureDetectorCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {

    private var isEditMode = false
    private var counter = 0
    private lateinit var list: List<Int>
    private lateinit var counterAdapter: CounterAdapter

    private var gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            FlingAnimation(rv_added_items, DynamicAnimation.SCROLL_X).apply {
                setStartVelocity(-velocityX)
                setMinValue(0f)
                setMaxValue(Float.POSITIVE_INFINITY)
                friction = 1.1f
                start()
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Glide.with(this).load(intent.getIntExtra(INTENT_CONTACT_AVATAR, 0)).into(iv_avatar)
        tv_name.text = intent.getStringExtra(INTENT_CONTACT_NAME)
        tv_counter.text = counter.toString()

        list = arrayListOf()
        counterAdapter = CounterAdapter(this@DetailActivity, list)
        val gestureDetectorCompat = GestureDetectorCompat(this@DetailActivity, gestureListener)
        rv_added_items.apply {
            setHasFixedSize(false)
            adapter = counterAdapter
            setOnTouchListener { _, p1 -> gestureDetectorCompat.onTouchEvent(p1) }
        }


        val animDrawable = getDrawable(R.drawable.avd_edit_done) as AnimatedVectorDrawable
        val animDrawable2 = getDrawable(R.drawable.avd_done_edit) as AnimatedVectorDrawable

        fab.setImageDrawable(animDrawable2)
        (fab.drawable as Animatable).start()

        fab.setOnClickListener {
            if (!isEditMode) {
                fab.setImageDrawable(animDrawable)
                (fab.drawable as Animatable).start()
                bt_decrease.visibility = View.VISIBLE
                bt_increase.visibility = View.VISIBLE
            } else {
                fab.setImageDrawable(animDrawable2)
                (fab.drawable as Animatable).start()
                bt_decrease.visibility = View.GONE
                bt_increase.visibility = View.GONE
            }
            isEditMode = !isEditMode
        }

        bt_increase.setOnClickListener {
            tv_counter.text = "${++counter}"
            (list as ArrayList<Int>).add(counter)
            Log.d("Details", list.toString())
            counterAdapter.notifyItemInserted(counter)
            counterAdapter.notifyItemRangeChanged(list.size - 1, list.size)
        }
        bt_decrease.setOnClickListener {
            when {
                counter > 0 -> {
                    tv_counter.text = "${--counter}"
                    (list as ArrayList<Int>).remove(list.size)
                    counterAdapter.notifyItemRemoved(list.size)
                    counterAdapter.notifyItemRangeChanged(list.size - 1, list.size)
                }
                else -> tv_counter.text = "$counter"
            }
        }
    }

    companion object {

        private const val INTENT_CONTACT_AVATAR = "contact_avatar"
        private const val INTENT_CONTACT_NAME = "contact_name"
        private const val INTENT_CONTACT_PHONE = "contact_phone"

        fun newIntent(context: Context, contact: Contact): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_CONTACT_AVATAR, contact.avatarId)
            intent.putExtra(INTENT_CONTACT_NAME, contact.name)
            intent.putExtra(INTENT_CONTACT_PHONE, contact.phone)
            return intent
        }
    }
}
