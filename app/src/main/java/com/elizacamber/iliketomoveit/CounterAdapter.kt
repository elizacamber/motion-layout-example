package com.elizacamber.iliketomoveit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.li_detail.view.*


class CounterAdapter(val context: Context, val items: List<Int>) : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.iv_added_item
        val container = itemView.added_item_container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.li_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CounterAdapter.ViewHolder, position: Int) {
        holder.avatar.let { Glide.with(context).load(R.drawable.avatar_1).into(it) }
        if (position == itemCount - 1) {
            val animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_from_left)
            holder.itemView.animation = animAnticipateOvershoot
            holder.container.transitionToEnd()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.container.transitionToStart()
        holder.container.clearAnimation()
    }
}
