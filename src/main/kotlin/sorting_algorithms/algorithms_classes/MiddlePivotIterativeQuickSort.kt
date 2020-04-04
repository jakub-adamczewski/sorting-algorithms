package sorting_algorithms.algorithms_classes

class MiddlePivotIterativeQuickSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "MiddlePivotIterativeQuick"
    override val complexity: String = "O(nlogn)"

    @ExperimentalStdlibApi
    override fun sort(list: MutableList<Int>): MutableList<Int> {
        iterativeQuickSort(list, 0, list.lastIndex)
        return list
    }

    @ExperimentalStdlibApi
    private fun iterativeQuickSort(list: MutableList<Int>, low: Int, high: Int) {
        val stack = mutableListOf<Int>()
        stack.add(low)
        stack.add(high)

        while (stack.isNotEmpty()) {

            val h = stack.removeLast()
            val l = stack.removeLast()

            val pivot = middlePivotPartition(list, l, h)

            if (pivot - 1 > l) {
                stack.add(l)
                stack.add(pivot - 1)
            }

            if (pivot + 1 < h) {
                stack.add(pivot + 1)
                stack.add(h)
            }
        }
    }

    private fun middlePivotPartition(list: MutableList<Int>, low: Int, high: Int): Int {
        val middleIndex = low + (high - low) / 2
        list[high] = list[middleIndex].also { list[middleIndex] = list[high] }

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
