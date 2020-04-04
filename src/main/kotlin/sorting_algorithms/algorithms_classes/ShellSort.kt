package sorting_algorithms.algorithms_classes

class ShellSort(listToSort: MutableList<Int>) : SortingAlgorithm(listToSort) {

    override lateinit var sortedList: MutableList<Int>
    override val title: String = "Shell"
    override val complexity: String = "O(n^2)"

    override fun sort(list: MutableList<Int>): MutableList<Int> {
        var gap = list.size / 2
        while (gap > 0) {
            for (i in gap until list.size) {

                if (list[i] < list[i - gap]) {
                    list[i] = list[i - gap].also { list[i - gap] = list[i] }

                    var currentIndex = i - 1
                    while (currentIndex >= gap){
                        if(list[currentIndex] < list[currentIndex - gap]){
                            list[currentIndex] = list[currentIndex - gap].also { list[currentIndex - gap] = list[currentIndex] }
                        }
                        currentIndex--
                    }
                }
            }
            gap /= 2
        }

        return list
    }

}