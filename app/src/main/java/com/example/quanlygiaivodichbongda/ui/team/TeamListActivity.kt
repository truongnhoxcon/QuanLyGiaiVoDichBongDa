package com.example.quanlygiaivodichbongda.ui.team

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.quanlygiaivodichbongda.R
import com.example.quanlygiaivodichbongda.database.AppDatabase
import com.example.quanlygiaivodichbongda.database.entity.Team
import com.example.quanlygiaivodichbongda.repository.TeamRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import com.example.quanlygiaivodichbongda.ui.player.PlayerListActivity

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

        recyclerView = findViewById(R.id.rvTeams)
        adapter = TeamAdapter(
            onClick = { team -> openPlayers(team) },
            onLongClick = { team -> confirmDelete(team) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        attachSwipeToDelete()

        viewModel.teams.observe(this) { list -> adapter.submitList(list) }

        findViewById<android.view.View>(R.id.fabAdd).setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_team, null)
        val etName = view.findViewById<android.widget.EditText>(R.id.etName)
        val etCoach = view.findViewById<android.widget.EditText>(R.id.etCoach)
        val etStadium = view.findViewById<android.widget.EditText>(R.id.etStadium)

        AlertDialog.Builder(this)
            .setTitle(R.string.add_team_title)
            .setView(view)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                val name = etName.text.toString().trim()
                val coach = etCoach.text.toString().trim().ifEmpty { null }
                val stadium = etStadium.text.toString().trim().ifEmpty { null }
                if (name.isNotEmpty()) {
                    viewModel.addTeam(Team(ten = name, hlv = coach, sanNha = stadium))
                }
            }
            .show()
    }

    private fun showEditDialog(team: Team) {
        val view = layoutInflater.inflate(R.layout.dialog_add_team, null)
        val etName = view.findViewById<android.widget.EditText>(R.id.etName)
        val etCoach = view.findViewById<android.widget.EditText>(R.id.etCoach)
        val etStadium = view.findViewById<android.widget.EditText>(R.id.etStadium)

        etName.setText(team.ten)
        etCoach.setText(team.hlv ?: "")
        etStadium.setText(team.sanNha ?: "")

        AlertDialog.Builder(this)
            .setTitle(R.string.add_team_title)
            .setView(view)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                val name = etName.text.toString().trim()
                val coach = etCoach.text.toString().trim().ifEmpty { null }
                val stadium = etStadium.text.toString().trim().ifEmpty { null }
                if (name.isNotEmpty()) {
                    viewModel.updateTeam(team.copy(ten = name, hlv = coach, sanNha = stadium))
                }
            }
            .show()
    }

    private fun confirmDelete(team: Team) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.confirm_delete, team.ten))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ -> viewModel.deleteTeam(team) }
            .show()
    }

    private fun attachSwipeToDelete() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val item = adapter.currentList.getOrNull(position)
                if (item != null) {
                    viewModel.deleteTeam(item)
                    Snackbar.make(recyclerView, getString(R.string.deleted, item.ten), Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            // Re-insert the deleted item
                            viewModel.addTeam(item.copy(id = 0))
                        }
                        .addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                // If undo not clicked, nothing to do (Room already deleted)
                            }
                        })
                        .show()
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }

    private fun openPlayers(team: Team) {
        val intent = Intent(this, PlayerListActivity::class.java)
        intent.putExtra(PlayerListActivity.EXTRA_TEAM_ID, team.id)
        intent.putExtra(PlayerListActivity.EXTRA_TEAM_NAME, team.ten)
        startActivity(intent)
    }
}


