package com.rysanek.fetchitemslist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.fetchitemslist.R
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.databinding.SingleItemBinding
import com.rysanek.fetchitemslist.presentation.utils.ItemListDiffUtil

class ItemsRecyclerViewAdapter: RecyclerView.Adapter<ItemsRecyclerViewAdapter.ItemsRecyclerViewHolder>() {
    
    private var currentArticleList = mutableListOf<ListItemEntity>()
    
    class ItemsRecyclerViewHolder(private val binding: SingleItemBinding): RecyclerView.ViewHolder(binding.root){
        
        companion object {
            fun from(parent: ViewGroup): ItemsRecyclerViewHolder {
                
                val inflater = LayoutInflater.from(parent.context)
                
                val singleLayout = SingleItemBinding.inflate(inflater, parent, false)
                
                return ItemsRecyclerViewHolder(singleLayout)
            }
        }
        
        fun bind(entity: ListItemEntity) {
    
            val colorId = when(entity.listId){
                1 -> R.color.highlight_blue
                2 -> R.color.highlight_orange
                3 -> R.color.highlight_pink
                4 -> R.color.highlight_yellow
                else -> R.color.highlight_aqua
            }
            
            with(binding){
    
                val color = binding.root.resources.getColor(colorId, binding.root.resources.newTheme())
                tvListId.text = entity.listId.toString()
                tvName.text = entity.name
                tvId.text = entity.itemId.toString()
               
                cvListId.setCardBackgroundColor(color)
                
                cvOuter.strokeColor = color
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsRecyclerViewHolder {
        return ItemsRecyclerViewHolder.from(parent)
    }
    
    override fun onBindViewHolder(holder: ItemsRecyclerViewHolder, position: Int) {
        val item = currentArticleList[position]
        holder.bind(item)
    }
    
    override fun getItemCount() = currentArticleList.size
    
    fun setData(list: List<ListItemEntity>) {
        ItemListDiffUtil(currentArticleList, list).calculateDiff(this)
        currentArticleList.clear()
        currentArticleList = list as MutableList
    }
}