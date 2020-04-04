package sorting_algorithms.algorithms_classes

class InsertionSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Insertion"
    override val complexity: String = "O(n^2)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {

        for (i in 1 until list.size) {

            val currentItem = list[i]
            var currentIndex = i

            while (currentIndex > 0 && currentItem < list[currentIndex - 1]) {
                list[currentIndex] = list[currentIndex - 1]
                currentIndex -= 1
            }

            list[currentIndex] = currentItem
        }

        return list
    }

}