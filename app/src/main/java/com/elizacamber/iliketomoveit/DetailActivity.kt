package com.elizacamber.iliketomoveit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var isEditMode = false

    private lateinit var counterAdapter: CounterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Glide.with(this).load(intent.getIntExtra(INTENT_CONTACT_AVATAR, 0)).into(iv_avatar)
        tv_name.text = intent.getStringExtra(INTENT_CONTACT_NAME)
        tv_counter.text = "0"

        counterAdapter = CounterAdapter(this@DetailActivity)
        rv_added_items.apply {
            setHasFixedSize(false)
            adapter = counterAdapter
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

        val springForce = SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }

        val springAnimationX = SpringAnimation(iv_avatar, DynamicAnimation.TRANSLATION_X).apply {
            spring = springForce
        }
        val springAnimationY = SpringAnimation(iv_avatar, DynamicAnimation.TRANSLATION_Y).apply {
            spring = springForce
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

        var dX = iv_avatar.width / 2f
        var dY = iv_avatar.height / 2f

        iv_avatar.setOnTouchListener { view, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = motionEvent.rawX
                    dY = motionEvent.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = motionEvent.rawX - dX
                    val newY = motionEvent.rawY - dY
                    iv_avatar.animate().translationX(newX).translationY(newY).duration = 0
                    springAnimationX.animateToFinalPosition(0f)
                    springAnimationY.animateToFinalPosition(0f)
                }
            }
            true
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
