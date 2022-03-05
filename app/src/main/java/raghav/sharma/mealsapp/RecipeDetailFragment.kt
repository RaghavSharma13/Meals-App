package raghav.sharma.mealsapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import raghav.sharma.mealsapp.databinding.FragmentRecipeDetailBinding

private const val TAG = "RecipeDetailFragment"

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding
    private lateinit var navController: NavController
    private lateinit var dish: Dish
    private val model: MainActivityViewModel by activityViewModels()
    private lateinit var mRVAdapterIngredients: RecipeDetailRVA
    private lateinit var mRVAdapterSteps: RecipeDetailRVA
    private var isFavorite: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dishId = arguments!!.getString(getString(R.string.arg_dishId))!!
        dish = model.dishes.value!!.find { d: Dish -> d.id === dishId }!!

        isFavorite = model.isFavorite(dish.id)

        mRVAdapterIngredients = RecipeDetailRVA(dish.ingredients)
        mRVAdapterSteps = RecipeDetailRVA(dish.steps)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Picasso.get().load(dish.imageUrl).error(R.drawable.placeholder_image)
//            .placeholder(R.drawable.placeholder_image).into(binding.recipeDetailImage)
//        binding.recipeDetailDuration.text = getString(R.string.minuteSymbol, dish.duration)
//        binding.recipeDetailAffordability.text = dish.affordability
//        binding.recipeDetailComplexity.text = dish.complexity

        navController = Navigation.findNavController(view)

        Picasso.get().load(dish.imageUrl).error(R.drawable.placeholder_image)
            .placeholder(R.drawable.placeholder_image).into(binding.recipeCard.recipeImage)

        binding.recipeCard.recipeDuration.text = getString(R.string.minuteSymbol, dish.duration)
        binding.recipeCard.recipeAffordability.text = dish.affordability
        binding.recipeCard.recipeComplexity.text = dish.complexity


        binding.recipeDetailIngredients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRVAdapterIngredients
        }
        binding.recipeDetailSteps.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRVAdapterSteps
        }
    }

    private fun toggleFavoriteIcon(item: MenuItem){
        if (isFavorite == true) item.setIcon(R.drawable.ic_baseline_star_24_checked)
        else {
            item.setIcon(R.drawable.ic_baseline_star_border_24)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val favoritesIcon = menu.findItem(R.id.star)
        toggleFavoriteIcon(favoritesIcon)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "Popping Stack")
                navController.navigateUp()
            }
            R.id.star -> {
                Log.d(TAG, "handling ${dish.title} for favorite")
                isFavorite = !isFavorite!!
                toggleFavoriteIcon(item)
                model.handleFavorite(dish.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment RecipeDetailFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            RecipeDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}