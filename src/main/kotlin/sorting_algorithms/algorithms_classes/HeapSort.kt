package sorting_algorithms.algorithms_classes

class HeapSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Heap"
    override val complexity: String = "O(nlogn)"

    private var heapSize = 0

    override fun sort(list: MutableList<Int>): MutableList<Int> {
        heapSort(list)
        return list
    }

    private fun heapSort(list: MutableList<Int>){
        val size = list.size

        for (i in size - 1 downTo 0){
            heapify(list, size, i)
        }

        for (i in size - 1 downTo 0){
            list[i] = list[0].also { list[0] = list[i] }
            heapify(list, i, 0)
        }
    }

    private fun heapify(list: MutableList<Int>, n: Int, i: Int) {
        var largest = i
        val l = 2 * i + 1
        val r = 2 * i + 2

        if(l < n && list[i] < list[l]) largest = l

        if(r < n && list[largest] < list[r]) largest = r

        if(largest != i) {
            list[i] = list[largest].also { list[largest] = list[i] }
            heapify(list, n, largest)
        }

    }
}