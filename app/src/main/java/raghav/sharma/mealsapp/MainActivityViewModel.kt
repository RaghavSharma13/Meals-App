package raghav.sharma.mealsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


private const val TAG = "MainActivityVM"

class MainActivityViewModel : ViewModel() {

    private val data = DummyData()
    private val _cuisines = MutableLiveData(data.getCuisines())
    val cuisines: LiveData<Array<CuisinesClass>>
        get() = _cuisines

    private val _dishes = MutableLiveData(data.getDishes())
    val dishes: LiveData<Array<Dish>>
        get() = _dishes


    private val _favorites = MutableLiveData<ArrayList<String>>(arrayListOf())
    val favorites: LiveData<ArrayList<String>>
        get() = _favorites

    private val _filters = MutableLiveData(
        FilterClass(
            isGlutenFree = false,
            isLactoseFree = false,
            isVegetarian = false,
            isVegan = false
        )
    )
    val filters: LiveData<FilterClass>
        get() = _filters

    fun getRecipes(cuisineId: String): List<Dish>? {

        val cuisineDishes = _dishes.value?.filter { recipe ->
            recipe.categoryIds.contains(cuisineId)
        }

        return cuisineDishes?.filter { dish ->
            if (_filters.value?.isGlutenFree == true && !dish.isGlutenFree) false
            else if (_filters.value?.isLactoseFree == true && !dish.isLactoseFree) false
            else if (_filters.value?.isVegan == true && !dish.isVegan) false
            else !(_filters.value?.isVegetarian == true && !dish.isVegetarian)
        }
    }


    fun getFavoriteRecipes(): List<Dish>? {
        Log.d(TAG, "Getting Favorite Dishes")
        return _favorites.value?.map { id -> _dishes.value?.find { dish -> dish.id == id }!! }
    }


    fun handleFavorite(recipeId: String) {
        val currentFavorites = _favorites.value
        if (currentFavorites != null && currentFavorites.contains(recipeId)) {
            // remove this recipe from favorites
            Log.d(TAG, "Was a favorite therefore removing it")
            currentFavorites.remove(recipeId)
        } else {
            Log.d(TAG, "Adding to favorites")
            currentFavorites?.add(recipeId)
        }
        Log.d(TAG, currentFavorites.toString())
        _favorites.postValue(currentFavorites!!)
    }

    fun isFavorite(recipeId: String): Boolean {
        val currentFavorites = _favorites.value
        return currentFavorites?.contains(recipeId) ?: false
    }

    fun updateFiltersObject(filterObj: FilterClass) {
        _filters.postValue(filterObj)
    }
}