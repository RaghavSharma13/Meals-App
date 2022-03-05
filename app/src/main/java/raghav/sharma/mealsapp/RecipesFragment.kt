package raghav.sharma.mealsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import raghav.sharma.mealsapp.databinding.FragmentRecipesBinding

private const val TAG = "RecipesFragment"

class RecipesFragment : Fragment(), RecipesRVAdapter.OnRecipeClickListener {

    private lateinit var binding: FragmentRecipesBinding
    private val model: MainActivityViewModel by activityViewModels()
    private lateinit var mRVAdapter: RecipesRVAdapter
    private lateinit var cuisineId: String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cuisineId = requireArguments().getString(getString(R.string.arg_cuisineId))!!

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigateUp()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        model.filters.observe(viewLifecycleOwner) {
            mRVAdapter.notifyDataSetChanged()
        }

        mRVAdapter = RecipesRVAdapter(model.getRecipes(cuisineId), requireContext(), this, "recipes")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recipesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRVAdapter
        }

        navController = Navigation.findNavController(view)
    }

    override fun onRecipeClicked(dishId: String) {
        val bundle = bundleOf(getString(R.string.arg_dishId) to dishId)
        navController.navigate(R.id.action_recipesFragment_to_recipeDetailFragment, bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "Popping Stack")
                navController.navigateUp()
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
////         * @param param1 Parameter 1.
////         * @param param2 Parameter 2.
//         * @return A new instance of fragment RecipesFragment.
//         */
//        @JvmStatic
//        fun newInstance() =
//            RecipesFragment()
//    }
}