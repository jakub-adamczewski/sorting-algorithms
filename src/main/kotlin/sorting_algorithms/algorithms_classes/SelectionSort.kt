package sorting_algorithms.algorithms_classes

class SelectionSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Selection"
    override val complexity: String = "O(n^2)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {

        for (i in 0 until list.size - 1) {
            var currentMinimumIndex = i

            for (j in i + 1 until list.size) {
                if (list[j] < list[currentMinimumIndex]) currentMinimumIndex = j
            }

            if (currentMinimumIndex != i) list[i] = list[currentMinimumIndex].also { list[currentMinimumIndex] = list[i] }
        }

        return list
    }


}



