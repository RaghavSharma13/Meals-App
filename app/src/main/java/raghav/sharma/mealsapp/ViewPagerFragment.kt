package raghav.sharma.mealsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import raghav.sharma.mealsapp.databinding.FragmentViewPagerBinding

private const val TAG = "ViewPagerFrag"

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val fragments = arrayListOf(HomeCollectionFragment(), FavoritesFragment())
        val adapter = ViewPagerAdapter(fragments, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.homeTab_title)
                    tab.setIcon(R.drawable.ic_baseline_restaurant_menu_24)
                }
                1 -> {
                    tab.text = getString(R.string.favoritesTab_title)
                    tab.setIcon(R.drawable.ic_baseline_star_24)
                }
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    1 -> {
                        binding.tabLayout.setBackgroundColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.accent,
                                null
                            )
                        )
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    1 -> {
                        binding.tabLayout.setBackgroundColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.primary,
                                null
                            )
                        )
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}