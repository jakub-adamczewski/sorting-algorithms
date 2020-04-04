package sorting_algorithms.algorithms_classes


class LeftPivotIterativeQuickSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "LeftPivotIterativeQuick"
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

            val pivot = leftPivotPartition(list, l, h)

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

    private fun leftPivotPartition(list: MutableList<Int>, low: Int, high: Int): Int {
        list[high] = list[low].also { list[low] = list[high] }

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
