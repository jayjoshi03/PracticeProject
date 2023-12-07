package com.example.uimagic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uimagic.databinding.ListViewBinding

class ListAdapter(private var listAdd : ArrayList<Model>) : RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder>() {
    private lateinit var binding : ListViewBinding
    private var list = ArrayList<Model>()

    @SuppressLint("NotifyDataSetChanged") fun setItem(list : ArrayList<Model>) {
        this.list = listAdd
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ListAdapterViewHolder {
        binding = ListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdapterViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    override fun onBindViewHolder(holder : ListAdapterViewHolder, position : Int) {
        holder.bind(listAdd[position], position)
    }

    inner class ListAdapterViewHolder(private val bind : ListViewBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(model : Model, position : Int) {
            bind.apply {
                name.text = model.name
            }
        }
    }
}