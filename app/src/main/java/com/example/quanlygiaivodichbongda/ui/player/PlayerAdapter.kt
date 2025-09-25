package com.example.quanlygiaivodichbongda.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlygiaivodichbongda.R
import com.example.quanlygiaivodichbongda.database.entity.Player

class PlayerAdapter : ListAdapter<Player, PlayerAdapter.PlayerVH>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerVH(view)
    }

    override fun onBindViewHolder(holder: PlayerVH, position: Int) {
        holder.bind(getItem(position))
    }

    class PlayerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvPlayerName)
        private val info: TextView = itemView.findViewById(R.id.tvPlayerInfo)

        fun bind(item: Player) {
            name.text = item.ten
            val number = item.soAo?.toString() ?: "—"
            val pos = item.viTri
            info.text = itemView.context.getString(R.string.player_info_format, number, pos)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem == newItem
        }
    }
}


