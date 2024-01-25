import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object WrappedArray {
    private val localList by lazy { CurrentFile.getListFromFile() }

    fun add(element: NoteDto) = localList.add(element).apply { CurrentFile.updateFile(localList) }
    fun remove(element: NoteDto) = localList.remove(element).apply { CurrentFile.updateFile(localList) }
    fun search(title: String) = linearSearch(localList, title)

    fun changeTitleInNote(noteDto: NoteDto, newTitle: String): NoteDto = noteDto.copy(title = newTitle).apply {
        localList[localList.indexOf(noteDto)] = this
        CurrentFile.updateFile(localList)
    }

    fun clearAllNotes() = localList.clear().apply { CurrentFile.updateFile(localList) }

    fun addTextInNote(noteDto: NoteDto, additionalText: String): NoteDto =
        noteDto.copy(noteText = noteDto.noteText + additionalText).apply {
            localList[localList.indexOf(noteDto)] = this
            CurrentFile.updateFile(localList)
        }

    fun clearTextInNote(noteDto: NoteDto): NoteDto = noteDto.copy(noteText = "").apply {
        localList[localList.indexOf(noteDto)] = this
        CurrentFile.updateFile(localList)
    }

    fun sortByTitle(): String {
        val flowList = mutableListOf<NoteDto>().apply { addAll(localList) }
        mergeSort(flowList, 0, localList.size - 1)
        return toString(flowList)
    }


    override fun toString() = toString(localList)
    fun toString(list: MutableList<NoteDto>) = list.joinToString(separator = "\n\n", postfix = "\n\n") { it.toString() }
}

private object CurrentFile {
    private val gson by lazy { Gson() }
    private val file by lazy { File("output.json") }

    fun updateFile(list: MutableList<NoteDto>) {
        FileWriter(file).use { writer ->
            writer.write(gson.toJson(list))
        }
    }

    fun getListFromFile(): MutableList<NoteDto> {
        val jsonString = FileReader(file).use { it.readText() }
        val typeToken = object : TypeToken<MutableList<NoteDto>>() {}.type
        return if (jsonString.isNotBlank()) gson.fromJson(jsonString, typeToken) else mutableListOf()
    }
}