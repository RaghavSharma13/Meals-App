package raghav.sharma.mealsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.detail_list_item.view.*

class ListItem(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer{
    override val containerView: View
        get() = itemView

    fun bind(itemText: String){
        itemView.recipe_detail_list_item_text.text = itemText
    }

}

class RecipeDetailRVA(private val items: Array<String>): RecyclerView.Adapter<ListItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_list_item, parent, false)
        return ListItem(view)
    }

    override fun onBindViewHolder(holder: ListItem, position: Int) {
        val items = items
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        val items = items
        return items.size
    }
}