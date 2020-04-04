package sorting_algorithms.algorithms_classes


class QuickSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Quick"
    override val complexity: String = "O(nlogn)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {
        quickSort(list, 0, list.lastIndex)
        return list
    }

    private fun quickSort(list: MutableList<Int>, low: Int, high: Int) {
        if (low < high) {
            val pivotLocation = partition(list, low, high)
            quickSort(list, low, pivotLocation - 1)
            quickSort(list, pivotLocation + 1, high)
        }
    }

    private fun partition(list: MutableList<Int>, low: Int, high: Int): Int {
        val pivot = list[high]
        var i = low - 1

        for (j in low until high) {
            if (list[j] <= pivot) {
                i++
                list[i] = list[j].also { list[j] = list[i] }
            }
        }
        list[i + 1] = list[high].also { list[high] = list[i + 1] }
        return i + 1

    }
}