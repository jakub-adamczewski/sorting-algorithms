package sorting_algorithms.algorithms_classes

class MergeSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Merge"
    override val complexity: String = "O(nlogn)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {
        if (list.size <= 1) return list

        val middle = list.size / 2
        val left = list.subList(0, middle);
        val right = list.subList(middle, list.size);

        return merge(sort(left), sort(right))
    }

    private fun merge(left: List<Int>, right: List<Int>): MutableList<Int>  {
        var indexLeft = 0
        var indexRight = 0
        val newList : MutableList<Int> = mutableListOf()

        while (indexLeft < left.size && indexRight < right.size) {
            if (left[indexLeft] <= right[indexRight]) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }

        return newList;
    }


}