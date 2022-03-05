package raghav.sharma.mealsapp

class Dish(
    val id: String,
    val categoryIds: Array<String>,
    val title: String,
    val affordability: String,
    val complexity: String,
    val imageUrl: String,
    val duration: Int,
    val ingredients: Array<String>,
    val steps: Array<String>,
    val isGlutenFree: Boolean,
    val isVegan: Boolean,
    val isVegetarian: Boolean,
    val isLactoseFree: Boolean
) {
}