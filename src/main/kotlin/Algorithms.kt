fun linearSearch(list: MutableList<NoteDto>, title: String): MutableList<NoteDto> {
    val resultList = mutableListOf<NoteDto>()
    list.forEach {
        if (it.title == title) resultList.add(it)
    }
    return resultList
}

fun mergeSort(array: MutableList<NoteDto>, left: Int, right: Int) {
    if (left < right) {
        val mid = (left + right) / 2
        mergeSort(array, left, mid)
        mergeSort(array, mid + 1, right)
        merge(array, left, mid, right)
    }
}

private fun merge(array: MutableList<NoteDto>, left: Int, mid: Int, right: Int) {
    val n1 = mid - left + 1
    val n2 = right - mid
    val leftList = MutableList(n1) { NoteDto("", "") }
    val rightList = MutableList(n2) { NoteDto("", "") }

    for (i in 0 until n1) leftList[i] = array[left + i]
    for (j in 0 until n2) rightList[j] = array[mid + 1 + j]

    var i = 0
    var j = 0
    var k = left

    while (i < n1 && j < n2) {
        if (leftList[i].title <= rightList[j].title) array[k] = leftList[i].also { i++ }
        else array[k] = rightList[j].also { j++ }
        k++
    }
    while (i < n1) array[k] = leftList[i].also { i++ }.also { k++ }
    while (j < n2) array[k] = rightList[j].also { j++ }.also { k++ }
}