package com.elizacamber.iliketomoveit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var isEditMode = false
    private lateinit var counterAdapter: CounterAdapter

    private var gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            FlingAnimation(rv_added_items, DynamicAnimation.SCROLL_X).apply {
                setStartVelocity(999f)
                setMinValue(0f)
                setMaxValue(9999f)
                friction = 50f
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
        tv_counter.text = "0"

        val gestureDetectorCompat = GestureDetectorCompat(this@DetailActivity, gestureListener)

        counterAdapter = CounterAdapter(this@DetailActivity)
        rv_added_items.apply {
            setHasFixedSize(false)
            adapter = counterAdapter
            setOnTouchListener { _, p1 -> gestureDetectorCompat.onTouchEvent(p1) }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        CounterAdapter.STOP_ANIMATION = true
                    } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        CounterAdapter.STOP_ANIMATION = false
                    }
                }
            })
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
            counterAdapter.addItem()
            tv_counter.text = "${counterAdapter.itemCount}"
        }
        bt_decrease.setOnClickListener {
            counterAdapter.removeItem()
            tv_counter.text = "${counterAdapter.itemCount}"
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
