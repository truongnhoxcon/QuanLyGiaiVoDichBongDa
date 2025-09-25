package com.example.quanlygiaivodichbongda.ui.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.quanlygiaivodichbongda.R
import com.example.quanlygiaivodichbongda.database.AppDatabase
import com.example.quanlygiaivodichbongda.database.entity.Player
import com.example.quanlygiaivodichbongda.repository.PlayerRepositoryImpl

class PlayerListActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TEAM_ID = "extra_team_id"
        const val EXTRA_TEAM_NAME = "extra_team_name"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlayerAdapter

    private val viewModel: PlayerViewModel by viewModels {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "football.db"
        ).fallbackToDestructiveMigration().build()
        val repo = PlayerRepositoryImpl(db.playerDao())
        val teamId = intent.getLongExtra(EXTRA_TEAM_ID, -1L)
        PlayerViewModel.Factory(repo, teamId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)

        title = intent.getStringExtra(EXTRA_TEAM_NAME) ?: getString(R.string.players)

        recyclerView = findViewById(R.id.rvPlayers)
        adapter = PlayerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.players.observe(this) { list -> adapter.submitList(list) }

        findViewById<android.view.View>(R.id.fabAddPlayer).setOnClickListener { showAddPlayerDialog() }
    }

    private fun showAddPlayerDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_player, null)
        val etName = view.findViewById<android.widget.EditText>(R.id.etPlayerName)
        val etNumber = view.findViewById<android.widget.EditText>(R.id.etNumber)
        val etPosition = view.findViewById<android.widget.EditText>(R.id.etPosition)
        val etDob = view.findViewById<android.widget.EditText>(R.id.etDob)

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.add_player_title)
            .setView(view)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                val name = etName.text.toString().trim()
                val number = etNumber.text.toString().trim().toIntOrNull()
                val position = etPosition.text.toString().trim().ifEmpty { "" }
                val dob = view.findViewById<android.widget.EditText>(R.id.etDob).text.toString().trim().ifEmpty { null }
                if (name.isNotEmpty() && position.isNotEmpty()) {
                    val teamId = intent.getLongExtra(EXTRA_TEAM_ID, -1L)
                    viewModel.addPlayer(
                        Player(
                            teamId = teamId,
                            ten = name,
                            soAo = number,
                            viTri = position,
                            ngaySinh = dob
                        )
                    )
                }
            }
            .show()
    }
}


