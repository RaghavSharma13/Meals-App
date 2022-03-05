package raghav.sharma.mealsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_list_item.view.*

private const val TAG = "RecipesRVAdapter"

class RecipesHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer{
    override val containerView: View
        get() = itemView

    private lateinit var recipeId: String

    fun bind(recipe: Dish, context: Context, listener: RecipesRVAdapter.OnRecipeClickListener){
        this.recipeId = recipe.id
        Picasso.get().load(recipe.imageUrl)
            .error(R.drawable.placeholder_image)
            .placeholder(R.drawable.placeholder_image)
            .into(itemView.recipe_image)
        itemView.recipe_title.text = recipe.title
        itemView.recipe_duration.text = context.getString(R.string.minuteSymbol, recipe.duration)
        itemView.recipe_affordability.text = recipe.affordability
        itemView.recipe_complexity.text = recipe.complexity

        itemView.fallback_text.visibility = View.INVISIBLE
        itemView.recipe_container.visibility = View.VISIBLE

        itemView.recipeCard.setOnClickListener {
            listener.onRecipeClicked(recipeId)
        }
    }

    fun bindFallback(showing: String){
        itemView.fallback_text.visibility = View.VISIBLE
        itemView.recipe_container.visibility = View.GONE

        val msgId = if(showing == "favorites") R.string.favorites_fallback_text else R.string.recipes_fallback_text
        itemView.fallback_text.setText(msgId)
    }
}
class RecipesRVAdapter(private var recipes: List<Dish>?, private val context: Context, private val listener: OnRecipeClickListener, val showing:String): RecyclerView.Adapter<RecipesHolder>() {

    interface OnRecipeClickListener{
        fun onRecipeClicked(dishId: String)
    }

    fun onUpdateFavorites(favorites:List<Dish>){
        recipes = favorites

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return RecipesHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
        val recipes = recipes
        if(recipes == null || recipes.isEmpty()){
            holder.bindFallback(showing)
        }else{
            holder.bind(recipes[position], context, listener)
        }
    }

    override fun getItemCount(): Int {
        val recipes = recipes
        return if(recipes == null || recipes.isEmpty()) 1
        else recipes.size
    }
}