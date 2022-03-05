package raghav.sharma.mealsapp

class CuisinesClass(val id: String, val title: String, val color: String) {
    override fun toString(): String {
        return "$id -- $title"
    }
}