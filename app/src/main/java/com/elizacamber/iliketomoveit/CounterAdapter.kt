package com.elizacamber.iliketomoveit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.li_detail.view.*


class CounterAdapter(val context: Context) : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    companion object {
        var FLAG_ANIMATE = false
        var STOP_ANIMATION = false
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.iv_added_item
        val container = itemView.added_item_container
    }

    val items = arrayListOf<Int>()

    fun addItem() {
        val count = itemCount
        items.add(count)
        notifyItemInserted(count)
        notifyItemRangeChanged(count - 1, count)
    }

    fun removeItem() {
        val count = itemCount
        if (count > 0) {
            items.remove(count - 1)
            notifyItemRemoved(count - 1)
            notifyItemRangeChanged(count - 1, count)
            FLAG_ANIMATE = false
        }
    }

    var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.li_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CounterAdapter.ViewHolder, position: Int) {
        holder.avatar.let { Glide.with(context).load(R.drawable.avatar_1).into(it) }
        if (!STOP_ANIMATION) {
            if (position > lastPosition && FLAG_ANIMATE || itemCount == 1) {
                val slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_from_left)
                holder.itemView.animation = slideInAnimation
                holder.itemView.setHasTransientState(true)
                holder.container.transitionToEnd()
                holder.container.transitionToStart()
                holder.itemView.setHasTransientState(true)
            }
        }
        FLAG_ANIMATE = lastPosition <= position
        lastPosition = position
    }
}
