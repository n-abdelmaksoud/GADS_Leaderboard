package com.nermin.gadsleaderboard.ui.leaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nermin.gadsleaderboard.R
import com.nermin.gadsleaderboard.model.Leader
import com.nermin.gadsleaderboard.model.LearningLeader
import com.nermin.gadsleaderboard.model.SkillIQLeader
import kotlinx.android.synthetic.main.list_item_leader.view.*

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    private var list = listOf<Leader>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return with(LayoutInflater.from(parent.context)) {
            LeaderboardViewHolder(inflate(R.layout.list_item_leader, parent, false))
        }
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        list[position].let { leader ->
            when (leader) {
                is LearningLeader -> holder.bindLearningLeaderView(leader)
                is SkillIQLeader -> holder.bindSkillIQLeader(leader)
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateList(list: List<Leader>) {
        this.list = list
        notifyDataSetChanged()
    }

    class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindSkillIQLeader(item: SkillIQLeader) {
            bindView(
                Triple(
                    item.name,
                    itemView.context.getString(
                        R.string.description_skill_iq,
                        item.score,
                        item.country
                    ),
                    item.badgeUrl
                )
            )
        }

        fun bindLearningLeaderView(item: LearningLeader) {
            bindView(
                Triple(
                    item.name,
                    itemView.context.getString(
                        R.string.description_learning_hours,
                        item.hours,
                        item.country
                    ),
                    item.badgeUrl
                )
            )
        }

        private fun bindView(triple: Triple<String, String, String>) {
            itemView.name.text = triple.first
            itemView.description.text = triple.second
            Glide.with(itemView.context)
                .load(triple.third)
                .into(itemView.badgeImageView)
        }

    }
}