package com.elizacamber.iliketomoveit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.li_detail.view.*


class CounterAdapter(val context: Context) : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    companion object {
        var STOP_ANIMATION = false
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.iv_added_item
        val container = itemView.added_item_container
    }

    val items = arrayListOf<Int>()

    fun addItem() {
        val count = itemCount + 1
        items.add(count)
        notifyItemInserted(count)
        notifyItemRangeChanged(count - 1, count)
    }

    fun removeItem() {
        val count = itemCount
        if (count > 0) {
            items.remove(count)
            notifyItemRemoved(count)
            notifyItemRangeChanged(count - 1, count)
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
            if (holder.adapterPosition > lastPosition || lastPosition == 0 && holder.adapterPosition == 0) {
                Log.d("CounterAdapter", "Animate ${holder.adapterPosition} - $position - $lastPosition")
                Log.d("CounterAdapter", "entrance state: ${holder.container.currentState}")
                val slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_from_left)
                holder.itemView.animation = slideInAnimation
                holder.container.transitionToEnd()
                Log.d("CounterAdapter", "final state: ${holder.container.currentState}")
                holder.container.transitionToStart()
                // reset the state once the animation is done
//            holder.container.transitionToState(entranceState!!)
            } else {
                Log.d("CounterAdapter", "Didn't animate ${holder.adapterPosition} - $position - $lastPosition")
            }
        }
        lastPosition = holder.adapterPosition
    }
}
