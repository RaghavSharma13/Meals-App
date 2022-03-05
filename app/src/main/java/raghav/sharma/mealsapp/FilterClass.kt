package raghav.sharma.mealsapp

class FilterClass(val isGlutenFree: Boolean, val isLactoseFree: Boolean, val isVegetarian: Boolean, val isVegan: Boolean) {
    override fun toString(): String {
        return "isGlutenFree: ${isGlutenFree}, isLactoseFree: ${isLactoseFree}, isVegetarian: ${isVegetarian}, isVegan: ${isVegan}"
    }
}