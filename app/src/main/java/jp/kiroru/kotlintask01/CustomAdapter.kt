package jp.kiroru.kotlintask01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_main.view.*

class CustomAdapter(
    private val context: Context,
    private val items: List<Item>,
    private val listener: ItemSelectionListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_main, parent, false)
        return ViewHolder(view).apply {
            view.setOnClickListener {
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.imageView
        val nameView = view.nameView
        val htmlUrlView = view.htmlUrlView
    }


    interface ItemSelectionListener {
        fun notifyItemSelected(item: Item)
    }

}