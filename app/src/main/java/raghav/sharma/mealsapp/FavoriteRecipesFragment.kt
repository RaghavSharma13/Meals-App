package raghav.sharma.mealsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import raghav.sharma.mealsapp.databinding.FragmentRecipesBinding

private const val TAG = "FavRecipesFrag"

class FavoriteRecipesFragment : Fragment(), RecipesRVAdapter.OnRecipeClickListener {

    private lateinit var binding: FragmentRecipesBinding
    private lateinit var navController: NavController
    private val model: MainActivityViewModel by activityViewModels()
    private lateinit var mRVAdapter: RecipesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mRVAdapter = RecipesRVAdapter(model.getFavoriteRecipes(), requireContext(), this, "favorites")
        model.favorites.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Handling Change in Favorites")
            mRVAdapter.onUpdateFavorites(model.getFavoriteRecipes()!!)
            mRVAdapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recipesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRVAdapter
        }
    }

    override fun onRecipeClicked(dishId: String) {
        val bundle = bundleOf(getString(R.string.arg_dishId) to dishId)
        navController.navigate(R.id.action_favoriteRecipes_to_recipeDetailFragment2, bundle)
    }
}