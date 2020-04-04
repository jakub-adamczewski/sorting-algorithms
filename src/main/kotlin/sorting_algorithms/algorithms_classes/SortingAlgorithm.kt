package sorting_algorithms.algorithms_classes

import kotlin.system.measureNanoTime

abstract class SortingAlgorithm(listToSort: MutableList<Int>) {

    private val nanoToMillisDivider = 1_000_000

    abstract var sortedList: MutableList<Int>

    abstract val title: String

    abstract val complexity: String

    var timeNanos: Long = 0

    var timeMillis: Long = 0

    abstract fun sort(list: MutableList<Int>): MutableList<Int>

    init {
        timeNanos = measureNanoTime { sortedList = sort(listToSort) }
        timeMillis = timeNanos / nanoToMillisDivider

        print("*")
    }
}