package sorting_algorithms.algorithms_classes

class BubbleSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Bubble"
    override val complexity: String = "O(n^2)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {

            for (j in list.indices) {
                for (i in 0 until list.size - j - 1) {
                    if (list[i] > list[i + 1]) list[i] = list[i + 1].also { list[i + 1] = list[i] }
                }
            }

        return list
    }

}