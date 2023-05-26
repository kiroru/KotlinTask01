package jp.kiroru.kotlintask01

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.kiroru.kotlintask01.databinding.CellMainBinding

class CustomAdapter(
    private val context: Context,
    private val items: List<Item>,
    private val listener: ItemSelectionListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CellMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                listener.notifyItemSelected(items[adapterPosition])
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(context)
            .load(item.imageUrl)
            .into(holder.imageView)
        holder.nameView.text = item.name
        holder.htmlUrlView.text = item.htmlUrl
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(context).clear(holder.imageView)
    }

    class ViewHolder(binding: CellMainBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageView
        val nameView = binding.nameView
        val htmlUrlView = binding.htmlUrlView
    }


    interface ItemSelectionListener {
        fun notifyItemSelected(item: Item)
    }

}