package sorting_algorithms.algorithms_classes

import kotlin.random.Random

class RandomPivotIterativeQuickSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "RandomPivotIterativeQuick"
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

            val pivot = randomPivotPartition(list, l, h)

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

    private fun randomPivotPartition(list: MutableList<Int>, low: Int, high: Int): Int {
        val randomIndex = Random.nextInt(low, high + 1)
        list[high] = list[randomIndex].also { list[randomIndex] = list[high] }

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
