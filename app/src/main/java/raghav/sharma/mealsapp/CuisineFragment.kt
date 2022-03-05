package raghav.sharma.mealsapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import raghav.sharma.mealsapp.databinding.FragmentCuisineBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CuisineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "CuisineFragment"
class CuisineFragment : Fragment(), CuisineRVAdapter.OnCuisineItemClickListener {

    private lateinit var binding: FragmentCuisineBinding
    private val model: MainActivityViewModel by activityViewModels()
    private var mRVAdapter = CuisineRVAdapter(null, this)
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRVAdapter = CuisineRVAdapter(model.cuisines.value, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentCuisineBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cuisineListView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mRVAdapter
        }
        navController = Navigation.findNavController(view)
    }

    override fun onClickListener(cuisineId: String) {
        Log.d(TAG, "CuisineId clicked was $cuisineId")
        val bundle = bundleOf(getString(R.string.arg_cuisineId) to cuisineId)
        navController.navigate(R.id.action_cuisineFragment_to_recipesFragment, bundle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CuisineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CuisineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}