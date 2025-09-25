package com.example.quanlygiaivodichbongda.ui.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlygiaivodichbongda.R
import com.example.quanlygiaivodichbongda.database.entity.Team

class TeamAdapter(
    private val onClick: (Team) -> Unit,
    private val onDelete: (Team) -> Unit
) : ListAdapter<Team, TeamAdapter.TeamVH>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamVH(view)
    }

    override fun onBindViewHolder(holder: TeamVH, position: Int) {
        holder.bind(getItem(position), onClick, onDelete)
    }

    class TeamVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvTeamName)
        private val coach: TextView = itemView.findViewById(R.id.tvCoach)
        private val stadium: TextView = itemView.findViewById(R.id.tvStadium)
        private val btnPlayers: Button? = itemView.findViewById(R.id.btnPlayers)

        fun bind(item: Team, onClick: (Team) -> Unit, onDelete: (Team) -> Unit) {
            name.text = item.ten
            coach.text = item.hlv ?: "—"
            stadium.text = item.sanNha ?: "—"

            // Click vào item hoặc nút "Cầu thủ"
            itemView.setOnClickListener { onClick(item) }
            btnPlayers?.setOnClickListener { onClick(item) }

            // Long click để xoá
            itemView.setOnLongClickListener {
                onDelete(item)
                true
            }
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem == newItem
        }
    }
}
