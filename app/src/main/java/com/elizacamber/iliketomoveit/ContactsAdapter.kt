package com.elizacamber.iliketomoveit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.li_main.view.*

class ContactsAdapter(val context: Context, val items:List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val avatar = view.iv_avatar!!
        val tvName = view.tv_name!!
        val tvPhone = view.tv_phone!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.li_main, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(items[position].avatarId).into(holder.avatar)
        holder.tvName.text = items[position].name
        holder.tvPhone.text = items[position].phone
    }
}
