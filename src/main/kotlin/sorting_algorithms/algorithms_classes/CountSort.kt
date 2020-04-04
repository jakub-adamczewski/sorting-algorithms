package sorting_algorithms.algorithms_classes

class CountSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {
    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Count"
    override val complexity: String = "O(n)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {

        if(list.size < 2) return list

        val countSize = list.max()!! + 1

        val count = MutableList(countSize, init = { index -> index - index })
        val output = MutableList(list.size, init = { index -> index - index })

        for (elem in list) ++count[elem]

        for (i in 1 until countSize) count[i] += count[i - 1]

        for (elem in list) output[--count[elem]] = elem

        return output
    }


}