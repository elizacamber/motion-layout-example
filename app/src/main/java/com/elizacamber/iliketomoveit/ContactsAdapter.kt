package com.elizacamber.iliketomoveit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.li_main.view.*

class ContactsAdapter(val context: Context, val items:List<Contact>, val listener: (Contact) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder (itemView: View?) : RecyclerView.ViewHolder(itemView) {
        // Holds the TextView that will add each animal to
        val avatar = itemView?.iv_avatar
        val tvName = itemView?.tv_name
        val tvPhone = itemView?.tv_phone

        fun bind (item: Contact, listener: (Contact) -> Unit) = with(itemView) {
            avatar?.let { Glide.with(context).load(item.avatarId).into(it) }
            tvName?.text = item.name
            tvPhone?.text = item.phone
            setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.li_main, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }
}
