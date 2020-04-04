package sorting_algorithms.run

import scientifik.plotly.Plotly
import scientifik.plotly.makeFile
import scientifik.plotly.trace
import sorting_algorithms.algorithms_classes.*
import sorting_algorithms.algorithms_classes.SortingAlgorithm
import kotlin.random.Random

private var count = 0
private var listsSet: MutableSet<List<Int>> = mutableSetOf()
private var stats: MutableList<SortingAlgorithm> = mutableListOf()
private const val tLine = "______________________________________________________________________________________________"

val plotSizes = listOf(
    10,
    100,
    1_000,
    5_000,
    10_000,
    15_000,
    20_000,
    25_000,
    30_000,
    35_000,
    40_000,
    45_000,
    50_000,
    55_000,
    60_000
)

val quickSortPlotSizes = listOf(
    10,
    100,
    1_000,
    2_500,
    5_000,
    7_500,
    10_000
)

fun main() {

    println("Do You want to see comparison of algorithms (1) or generate plots (2)? (plots can take lot of time)")
    when(readLine()!!.toInt()){
        1 -> inputUserPreferencesForStats()
        2 -> generatePlots()
        else -> {
            println("You have chosen nothing so I picked comparison.")
            inputUserPreferencesForStats()
        }
    }
}

fun inputUserPreferencesForStats() {
    println("How long list do You want to sort?")
    val size = readLine()!!.toInt()

    println("Should I print lists for You? (y/n)")
    val withListPrint = when(readLine()!!.toLowerCase()){
        "y" -> true
        "n" -> false
        else -> false
    }

    println("Please, pick list type: \n" +
            "1 - ${ListType.RANDOM.toString().toLowerCase()}\n" +
            "2 - ${ListType.ASCENDING.toString().toLowerCase()}\n" +
            "3 - ${ListType.DESCENDING.toString().toLowerCase()}\n" +
            "4 - ${ListType.CONSTANT.toString().toLowerCase()}\n" +
            "5 - ${ListType.V_SHAPED.toString().toLowerCase()}\n" +
            "6 - ${ListType.A_SHAPED.toString().toLowerCase()}\n"
    )
    val listType = when(readLine()!!.toInt()) {
        1 -> ListType.RANDOM
        2 -> ListType.ASCENDING
        3 -> ListType.DESCENDING
        4 -> ListType.CONSTANT
        5 -> ListType.V_SHAPED
        6 -> ListType.A_SHAPED
        else -> ListType.RANDOM
    }

    val listToSort = generateList(listType, size)
    print("Loading: ")
    runAllAlgorithms(listToSort)
    stats.sortBy { t -> t.timeNanos }
    printStats(withListPrint, listToSort, listType)
}

fun generatePlots() {
    generateInsertionSortPlot()
    generateSelectionSortPlot()
    generateHeapSortPlot()
    generateRecursiveQuickSortPlot()
    generateIterativeQuickSortPlot()

    generateVariousPivotPositionIterativeQuickSortPlot()
}

fun generateList(listType: ListType, size: Int, from: Int = 1, until: Int = size): List<Int> {
    return when (listType) {
        ListType.RANDOM -> List(size) { Random.nextInt(from, until) }
        ListType.ASCENDING -> List(size) { element -> element }
        ListType.DESCENDING -> List(size) { element -> size - element - 1 }
        ListType.CONSTANT -> List(size) { element -> element - element + 1 }
        ListType.V_SHAPED -> {
            val addOne = size % 2 == 1
            val leftSize = if (addOne) size / 2 + 1 else size / 2
            val rightSize = size / 2

            val left = List(leftSize) { element -> leftSize - element - 1 }
            val right = List(rightSize) { element -> if (addOne) element + 1 else element }
            left + right
        }
        ListType.A_SHAPED -> {
            val addOne = size % 2 == 1
            val leftSize = if (addOne) size / 2 + 1 else size / 2
            val rightSize = size / 2

            val left = List(leftSize) { element -> element }
            val right = List(rightSize) { element -> rightSize - element - 1 }
            left + right
        }
    }
}

fun runAllAlgorithms(randomList: List<Int>) {

    val bubbleSort = BubbleSort(randomList.toMutableList())
    stats.add(bubbleSort)

    val insertionSort = InsertionSort(randomList.toMutableList())
    stats.add(insertionSort)

    val selectionSort = SelectionSort(randomList.toMutableList())
    stats.add(selectionSort)

    val mergeSort = MergeSort(randomList.toMutableList())
    stats.add(mergeSort)

    val shellSort = ShellSort(randomList.toMutableList())
    stats.add(shellSort)

    val quickSort = QuickSort(randomList.toMutableList())
    stats.add(quickSort)

    val countSort = CountSort(randomList.toMutableList())
    stats.add(countSort)

    val heapSort = HeapSort(randomList.toMutableList())
    stats.add(heapSort)

    val leftIterativeQuickSort = LeftPivotIterativeQuickSort(randomList.toMutableList())
    stats.add(leftIterativeQuickSort)

    val rightIterativeQuickSort = RightPivotIterativeQuickSort(randomList.toMutableList())
    stats.add(rightIterativeQuickSort)

    val middleIterativeQuickSort = MiddlePivotIterativeQuickSort(randomList.toMutableList())
    stats.add(middleIterativeQuickSort)

    val randomIterativeQuickSort = RandomPivotIterativeQuickSort(randomList.toMutableList())
    stats.add(randomIterativeQuickSort)
}

fun printStats(withListPrint: Boolean, listToSort: List<Int>, listType: ListType) {

    println("\n\nGenerated $listType list with ${String.format(
        "%,d",
        listToSort.size
    )} elements.")
    if (withListPrint) println("List to sort: $listToSort")

    val newLineFormat = "| %8s | %25s | %10s | %18s | %18s|%n"
    val oneLineFormat = "| %8s | %25s | %10s | %18s | %18s|"
    println(tLine)
    System.out.format(newLineFormat, "Number", "Name", "Millis", "Nanos", "Time complexity")

    for (elem in stats) {

        listsSet.add(elem.sortedList)

        System.out.format(
            if(withListPrint) oneLineFormat else newLineFormat,
            count++,
            elem.title,
            String.format("%,d", elem.timeMillis),
            String.format("%,d", elem.timeNanos),
            elem.complexity
        )

        if (withListPrint) println("    Sorted list: ${elem.sortedList} ")
    }

    println("\n$count. Are all sorted lists equal? ${listsSet.size == 1}. \n\n")

}

fun generateInsertionSortPlot() {

    val x = plotSizes

    val randomY = x.map { InsertionSort(generateList(ListType.RANDOM, it).toMutableList()).timeMillis }
    val ascendingY = x.map { InsertionSort(generateList(ListType.ASCENDING, it).toMutableList()).timeMillis }
    val descendingY = x.map { InsertionSort(generateList(ListType.DESCENDING, it).toMutableList()).timeMillis }
    val constantY = x.map { InsertionSort(generateList(ListType.CONSTANT, it).toMutableList()).timeMillis }
    val vShapedY = x.map { InsertionSort(generateList(ListType.V_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, randomY) {
            name = ListType.RANDOM.toString().toLowerCase()
        }
        trace(x, ascendingY) {
            name = ListType.ASCENDING.toString().toLowerCase()
        }
        trace(x, descendingY) {
            name = ListType.DESCENDING.toString().toLowerCase()
        }
        trace(x, constantY) {
            name = ListType.CONSTANT.toString().toLowerCase()
        }
        trace(x, vShapedY) {
            name = ListType.V_SHAPED.toString().toLowerCase()
        }

        layout {
            title = "Insertion Sort"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()
}

fun generateSelectionSortPlot() {

    val x = plotSizes

    val randomY = x.map { SelectionSort(generateList(ListType.RANDOM, it).toMutableList()).timeMillis }
    val ascendingY = x.map { SelectionSort(generateList(ListType.ASCENDING, it).toMutableList()).timeMillis }
    val descendingY = x.map { SelectionSort(generateList(ListType.DESCENDING, it).toMutableList()).timeMillis }
    val constantY = x.map { SelectionSort(generateList(ListType.CONSTANT, it).toMutableList()).timeMillis }
    val vShapedY = x.map { SelectionSort(generateList(ListType.V_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, randomY) {
            name = ListType.RANDOM.toString().toLowerCase()
        }
        trace(x, ascendingY) {
            name = ListType.ASCENDING.toString().toLowerCase()
        }
        trace(x, descendingY) {
            name = ListType.DESCENDING.toString().toLowerCase()
        }
        trace(x, constantY) {
            name = ListType.CONSTANT.toString().toLowerCase()
        }
        trace(x, vShapedY) {
            name = ListType.V_SHAPED.toString().toLowerCase()
        }

        layout {
            title = "Selection Sort"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()
}

fun generateHeapSortPlot() {

    val x = plotSizes

    val randomY = x.map { HeapSort(generateList(ListType.RANDOM, it).toMutableList()).timeMillis }
    val ascendingY = x.map { HeapSort(generateList(ListType.ASCENDING, it).toMutableList()).timeMillis }
    val descendingY = x.map { HeapSort(generateList(ListType.DESCENDING, it).toMutableList()).timeMillis }
    val constantY = x.map { HeapSort(generateList(ListType.CONSTANT, it).toMutableList()).timeMillis }
    val vShapedY = x.map { HeapSort(generateList(ListType.V_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, randomY) {
            name = ListType.RANDOM.toString().toLowerCase()
        }
        trace(x, ascendingY) {
            name = ListType.ASCENDING.toString().toLowerCase()
        }
        trace(x, descendingY) {
            name = ListType.DESCENDING.toString().toLowerCase()
        }
        trace(x, constantY) {
            name = ListType.CONSTANT.toString().toLowerCase()
        }
        trace(x, vShapedY) {
            name = ListType.V_SHAPED.toString().toLowerCase()
        }

        layout {
            title = "Heap Sort"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()
}

fun generateRecursiveQuickSortPlot() {

    val x = quickSortPlotSizes

    val randomY = x.map { QuickSort(generateList(ListType.RANDOM, it).toMutableList()).timeMillis }
    val ascendingY = x.map { QuickSort(generateList(ListType.ASCENDING, it).toMutableList()).timeMillis }
    val descendingY = x.map { QuickSort(generateList(ListType.DESCENDING, it).toMutableList()).timeMillis }
    val constantY = x.map { QuickSort(generateList(ListType.CONSTANT, it).toMutableList()).timeMillis }
    val vShapedY = x.map { QuickSort(generateList(ListType.V_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, randomY) {
            name = ListType.RANDOM.toString().toLowerCase()
        }
        trace(x, ascendingY) {
            name = ListType.ASCENDING.toString().toLowerCase()
        }
        trace(x, descendingY) {
            name = ListType.DESCENDING.toString().toLowerCase()
        }
        trace(x, constantY) {
            name = ListType.CONSTANT.toString().toLowerCase()
        }
        trace(x, vShapedY) {
            name = ListType.V_SHAPED.toString().toLowerCase()
        }

        layout {
            title = "Quick Sort"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()
}

fun generateIterativeQuickSortPlot() {

    val x = plotSizes

    val randomY = x.map { LeftPivotIterativeQuickSort(generateList(ListType.RANDOM, it).toMutableList()).timeMillis }
    val ascendingY = x.map { LeftPivotIterativeQuickSort(generateList(ListType.ASCENDING, it).toMutableList()).timeMillis }
    val descendingY = x.map { LeftPivotIterativeQuickSort(generateList(ListType.DESCENDING, it).toMutableList()).timeMillis }
    val constantY = x.map { LeftPivotIterativeQuickSort(generateList(ListType.CONSTANT, it).toMutableList()).timeMillis }
    val vShapedY = x.map { LeftPivotIterativeQuickSort(generateList(ListType.V_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, randomY) {
            name = ListType.RANDOM.toString().toLowerCase()
        }
        trace(x, ascendingY) {
            name = ListType.ASCENDING.toString().toLowerCase()
        }
        trace(x, descendingY) {
            name = ListType.DESCENDING.toString().toLowerCase()
        }
        trace(x, constantY) {
            name = ListType.CONSTANT.toString().toLowerCase()
        }
        trace(x, vShapedY) {
            name = ListType.V_SHAPED.toString().toLowerCase()
        }

        layout {
            title = "Iterative Quick Sort"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()
}

fun generateVariousPivotPositionIterativeQuickSortPlot() {

    val x = plotSizes

    val leftPivotQuickSort = x.map { LeftPivotIterativeQuickSort(generateList(ListType.A_SHAPED, it).toMutableList()).timeMillis }
    val middlePivotQuickSort = x.map { MiddlePivotIterativeQuickSort(generateList(ListType.A_SHAPED, it).toMutableList()).timeMillis }
    val rightPivotQuickSort = x.map { RightPivotIterativeQuickSort(generateList(ListType.A_SHAPED, it).toMutableList()).timeMillis }
    val randomPivotQuickSort = x.map { RandomPivotIterativeQuickSort(generateList(ListType.A_SHAPED, it).toMutableList()).timeMillis }

    val plot = Plotly.plot2D {
        trace(x, leftPivotQuickSort) {
            name = "left"
        }
        trace(x, middlePivotQuickSort) {
            name = "middle"
        }
        trace(x, rightPivotQuickSort) {
            name = "right"
        }
        trace(x, randomPivotQuickSort) {
            name = "random"
        }

        layout {
            title = "Iterative Quick Sort - a-shaped list sorting times for various pivot positions"
            xaxis {
                title = "number of elements in list"
            }
            yaxis {
                title = "time of sorting (ms)"
            }
        }
    }

    plot.makeFile()

}

enum class ListType {
    RANDOM,
    ASCENDING,
    DESCENDING,
    CONSTANT,
    V_SHAPED,
    A_SHAPED
}


