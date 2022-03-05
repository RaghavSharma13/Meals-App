package raghav.sharma.mealsapp

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import raghav.sharma.mealsapp.databinding.FragmentFiltersBinding

class FiltersFragment : Fragment() {

    private lateinit var binding: FragmentFiltersBinding
    private val model: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filters_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filters_menu_save -> {
                var isGlutenFree = false
                var isLactoseFree = false
                var isVegan = false
                var isVegetarian = false
                val checkedChips = binding.filtersChipGroup.checkedChipIds
                checkedChips.forEach { checkedId ->
                    when (checkedId) {
                        R.id.chip_gluten_free -> isGlutenFree = true
                        R.id.chip_lactose_free -> isLactoseFree = true
                        R.id.chip_vegetarian -> isVegetarian = true
                        R.id.chip_vegan -> isVegan = true
                        else -> throw IllegalArgumentException("Unidentified Chip checked")
                    }
                }
                model.updateFiltersObject(FilterClass(isGlutenFree, isLactoseFree, isVegetarian, isVegan))
                Toast.makeText(requireContext(), R.string.saved, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = FiltersFragment()
    }
}