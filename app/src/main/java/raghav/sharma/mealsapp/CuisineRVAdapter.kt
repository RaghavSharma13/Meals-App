package raghav.sharma.mealsapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cuisine_list_item.view.*


private const val TAG = "CuisineRVAdapter"

class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View
        get() = itemView

    private lateinit var cuisineId: String
    fun bind(cuisine: CuisinesClass, listener: CuisineRVAdapter.OnCuisineItemClickListener) {
        this.cuisineId = cuisine.id
        itemView.cuisineText.text = cuisine.title
        itemView.cuisine_card.setCardBackgroundColor(Color.parseColor(cuisine.color))

        itemView.cuisine_card.setOnClickListener{
            listener.onClickListener(cuisineId)
        }
    }
}

class CuisineRVAdapter(private val categories: Array<CuisinesClass>?, private val listener: OnCuisineItemClickListener) :
    RecyclerView.Adapter<CardHolder>() {

    interface OnCuisineItemClickListener{
        fun onClickListener(cuisineId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        Log.d(TAG, "onCreateViewHolder called")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cuisine_list_item, parent, false)
        return CardHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")
        val categories = categories
        if (categories != null) {
            holder.bind(categories[position], listener)
        }
    }

    override fun getItemCount(): Int {
        val categories = categories
        return categories?.size ?: 0
    }
}