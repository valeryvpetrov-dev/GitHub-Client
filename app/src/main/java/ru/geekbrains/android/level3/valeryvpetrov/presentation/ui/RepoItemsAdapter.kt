package ru.geekbrains.android.level3.valeryvpetrov.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.android.level3.valeryvpetrov.R
import ru.geekbrains.android.level3.valeryvpetrov.databinding.ItemRepoBinding
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.RepoItem

class RepoItemsAdapter(
    private var repoItems: List<RepoItem>
) : RecyclerView.Adapter<RepoItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_repo, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = repoItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.repoItem = repoItems[position]
    }

    fun setItems(repoItems: List<RepoItem>) {
        this.repoItems = repoItems
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}