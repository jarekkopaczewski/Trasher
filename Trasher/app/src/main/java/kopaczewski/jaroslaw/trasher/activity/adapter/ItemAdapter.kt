package kopaczewski.jaroslaw.nutone.ui.universal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.adapter.ListItemViewHolder

class ItemAdapter(private var dataSet: List<Item>) :
    RecyclerView.Adapter<ListItemViewHolder>() {
    private var filterSet: List<Item> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListItemViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.setEverything(filterSet[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(databaseSet: List<Item>) {
        dataSet = databaseSet
        filterSet = dataSet
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(text: String) {
        filterSet = if (text.isEmpty()) dataSet
        else dataSet.filter { it.name.toLowerCase().contains(text.toLowerCase()) }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun categoryFilter(categories: List<String>) {
        filterSet =  dataSet.filter { item -> checkContains(categories, item.category) }.map { e -> e }
        notifyDataSetChanged()
    }

    private fun checkContains(categories: List<String>, category: String): Boolean {
        categories.forEach { if (category.contains(it)) return true }
        return false
    }

    override fun getItemCount(): Int = filterSet.size

    fun getItem(position: Int) = dataSet[position]
}