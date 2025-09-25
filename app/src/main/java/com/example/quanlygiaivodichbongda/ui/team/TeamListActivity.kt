package com.example.quanlygiaivodichbongda.ui.team

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.quanlygiaivodichbongda.R
import com.example.quanlygiaivodichbongda.database.AppDatabase
import com.example.quanlygiaivodichbongda.database.entity.Team
import com.example.quanlygiaivodichbongda.repository.TeamRepositoryImpl
import com.example.quanlygiaivodichbongda.ui.player.PlayerListActivity
import com.google.android.material.snackbar.Snackbar

class TeamListActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeamAdapter

    private val viewModel: TeamViewModel by viewModels {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "football.db"
        ).fallbackToDestructiveMigration().build()
        val repo = TeamRepositoryImpl(db.teamDao())
        TeamViewModel.Factory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_list)

        title = getString(R.string.teams)

        recyclerView = findViewById(R.id.rvTeams)
        adapter = TeamAdapter(
            onClick = { team -> openPlayerList(team) },
            onDelete = { team -> confirmDelete(team) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.teams.observe(this) { list -> adapter.submitList(list) }

        findViewById<View>(R.id.fabAdd).setOnClickListener { showAddTeamDialog() }

        // Xử lý nút back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun showAddTeamDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_team, null)
        val etName = view.findViewById<EditText>(R.id.etTeamName)
        val etCoach = view.findViewById<EditText>(R.id.etCoach)
        val etStadium = view.findViewById<EditText>(R.id.etStadium) // <--- thêm dòng này

        AlertDialog.Builder(this)
            .setTitle(R.string.add_team_title)
            .setView(view)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.ok) { _, _ ->
                val name = etName.text.toString().trim()
                val coach = etCoach.text.toString().trim()
                val stadium = etStadium.text.toString().trim() // <--- lấy giá trị sân nhà
                if (name.isNotEmpty()) {
                    viewModel.addTeam(
                        Team(
                            ten = name,
                            hlv = coach,
                            sanNha = stadium // <--- truyền vào
                        )
                    )
                }
            }
            .show()
    }

    private fun confirmDelete(team: Team) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete)
            .setMessage(getString(R.string.deleted, team.ten))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.deleteTeam(team)
                Snackbar.make(
                    recyclerView,
                    getString(R.string.deleted, team.ten),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.undo) {
                        viewModel.addTeam(team)
                    }.show()
            }
            .show()
    }

    private fun openPlayerList(team: Team) {
        val intent = Intent(this, PlayerListActivity::class.java).apply {
            putExtra(PlayerListActivity.EXTRA_TEAM_ID, team.id)
            putExtra(PlayerListActivity.EXTRA_TEAM_NAME, team.ten)
        }
        startActivity(intent)
    }
}
