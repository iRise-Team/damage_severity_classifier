package com.irise.damagedetection.ui.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irise.damagedetection.R
import com.irise.damagedetection.databinding.ItemRowBinding
import com.irise.damagedetection.dummy.Dummy

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {
    private var list = ArrayList<Dummy>()

    fun setList(dummy: List<Dummy>) {
        list.clear()
        list.addAll(dummy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val itemExploreBinding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(itemExploreBinding)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ExploreViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(data: Dummy) {
            binding.apply {
                val user = itemView.context.getDrawable(R.drawable.user)
                val setResult: String = when (data.result) {
                    "0" -> {
                        itemView.context.resources.getString(R.string.none)
                    }
                    "1" -> {
                        itemView.context.resources.getString(R.string.mild)
                    }
                    else ->
                        itemView.context.resources.getString(R.string.severe)
                }
                tvUsername.text = data.username
                tvLocation.text = data.location
                tvTime.text = data.time
                tvResult.text = setResult
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgDisplay)
                Glide.with(itemView.context)
                    .load(user)
                    .into(imgUser)
            }

        }
    }
}