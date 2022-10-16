package kopaczewski.jaroslaw.trasher.activity.ui.list

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kopaczewski.jaroslaw.nutone.ui.universal.adapter.ItemAdapter
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.currentItems
import kopaczewski.jaroslaw.trasher.activity.helper.AnimateView
import kopaczewski.jaroslaw.trasher.databinding.CategoryViewBinding
import kopaczewski.jaroslaw.trasher.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var categoryBinding: CategoryViewBinding
    private lateinit var categoryView: ConstraintLayout
    private lateinit var categoryButton: ImageView
    private lateinit var categoryGroup: ChipGroup
    private lateinit var exitButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var searchView: SearchView
    private var categoryNames: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        categoryBinding = binding.categoryViewList
        categoryButton = binding.categoryButton
        categoryView = categoryBinding.categoryView
        categoryGroup = categoryBinding.categoryGroup
        exitButton = categoryBinding.exitFabCategories
        recyclerView = binding.itemListRecycler
        searchView = binding.itemSearchView

        adapter = ItemAdapter(listOf())
        adapter.setData(currentItems)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), VERTICAL, false)
        recyclerView.adapter = adapter

        setCategories()

        categoryButton.setOnClickListener {
            if (categoryView.visibility == VISIBLE) {
                categoryView.visibility = View.INVISIBLE
            } else {
                categoryView.visibility = VISIBLE
                AnimateView.singleAnimation(categoryView, context, R.anim.zoomin)
            }
        }

        exitButton.setOnClickListener {
            if (categoryView.visibility == VISIBLE) {
                categoryView.visibility = View.INVISIBLE
            }
        }


        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                adapter.filter(msg)
                return false
            }
        })


        return binding.root
    }

    private fun setCategories() {
        currentItems.map { e -> e.category }.toSet().forEach {
            val chip = Chip(this.requireContext())
            chip.text = it
            chip.chipBackgroundColor  = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.foreground))
            categoryNames.add(it)
            chip.isCheckable = true
            chip.isChecked = true
            categoryGroup.addView(chip)
        }

        categoryGroup.children.forEach {
            it.setOnClickListener {
                val categories2 = categoryGroup.children.filter { e -> (e as Chip).isChecked }
                    .map { e -> (e as Chip).text.toString() }.toList()
                println(categories2)
                adapter.categoryFilter(categories2)
            }
        }

    }
}