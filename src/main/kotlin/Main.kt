fun main() {
    while (true) {
        println(
            "$line\n\u001B[36m" +
                    "1 -> додати нову нотатку\n" +
                    "2 -> знайти нотаку за назвою\n" +
                    "3 -> сортувати за заголовком\n" +
                    "4 -> показати у хронологічному порядку\n" +
                    "5 -> очистити нотатник\n" +
                    "end -> завершити роботу програми\n" +
                    "\u001B[0m" + line
        )
        when (readln().apply { println(line) }) {
            "1" -> {
                print("Додати нотатку:\n\tНазва: ")
                val title = readln()
                println("\tЗміст нотатки:")
                val noteText = readln()
                val newNote = NoteDto(title = title, noteText = noteText)
                WrappedArray.add(newNote)
                println("$completeSign Нотатку успішно додано")
            }

            "2" -> {
                println("Уведіть назву шуканої нотатки:")
                val flowList = WrappedArray.search(readln())
                println(line)
                when (flowList.size) {
                    0 -> println("$mistakeSign Даної нотатки не існує")
                    1 -> {
                        val currentNote = flowList[0]
                        println("$completeSign Нотатку: \u001B[4m\u001B[32m${currentNote.title}\u001B[0m знайдено")
                        noteOperation(currentNote)
                    }

                    else -> {
                        WrappedArray.toString(flowList)
                        flowList.forEachIndexed { index, noteDto ->
                            println("Порядковий номер: \u001B[35m ${index + 1}\u001B[0m\n$noteDto\n")
                        }
                        println("$line\nУведіть\u001B[35m порядковий номер\u001B[0m нотатки, яку хочете обрати:")
                        while (true) {
                            val value = readln()
                            println(line)
                            if (flowList.size >= value.toInt() && 0 < value.toInt()) {
                                val currentNote = flowList[value.toInt() - 1]
                                println("Ваша нотатка:\n$currentNote")
                                noteOperation(currentNote)
                                break
                            } else println(mistake)
                        }
                    }
                }
            }

            "3" -> WrappedArray.printListByArray(WrappedArray::sortByTitle)
            "4" -> WrappedArray.printListByArray(WrappedArray::toString)
            "5" -> {
                println("Щоб видалити усі нотатки введіть ОК")
                val localEnd = readln()
                if (localEnd != "ОК" && localEnd != "ок" && localEnd != "ok" && localEnd != "OK") {
                    println("$mistakeSign Помилка видалення")
                } else {
                    WrappedArray.clearAllNotes()
                    println("$completeSign Усі нотатки видалено")
                }
            }

            "end" -> break
            else -> println(mistake)
        }
    }
}

private fun WrappedArray.printListByArray(customArrayPrintMethod: () -> String) {
    if (toString().isBlank()) println("Немає нотаток")
    print(customArrayPrintMethod.invoke())
    println("$line\nВведіть ОК щоб завершити перегляд")
    var localEnd = readln()
    while (localEnd !in setOf("ОК", "ок", "ok", "OK")) {
        println(mistake)
        localEnd = readln()
    }
    println(line)
}

private fun noteOperation(currentNote: NoteDto) {
    var flowNote = currentNote
    while (true) {
        println(
            "$line\n\u001B[36m" +
                    "1 -> змінити заговолок\n" +
                    "2 -> додати текст нотатки\n" +
                    "3 -> видалити текст нотатки\n" +
                    "4 -> видалити нотатку\n" +
                    "5 -> показати нотатку\n" +
                    "6 -> вийти у головне меню\n" +
                    "\u001B[0m" + line
        )
        when (readln().apply { println(line) }) {
            "1" -> {
                println("Уведіть новий заголовок:")
                val newTitle = readln()
                flowNote = WrappedArray.changeTitleInNote(flowNote, newTitle)
                println("$completeSign Нотатку оновлено")
            }

            "2" -> {
                println("Уведіть додатковий текст:")
                print("\u001B[32m" + flowNote.noteText + "\u001B[0m")
                val additionalText = readln()
                flowNote = WrappedArray.addTextInNote(flowNote, additionalText)
                println("$completeSign нотатку оновлено")
            }

            "3" -> {
                flowNote = WrappedArray.clearTextInNote(flowNote)
                println("$completeSign Текст нотатки видалено, нотатку оновлено")
            }

            "4" -> {
                WrappedArray.remove(flowNote)
                println("$completeSign нотатку видалено")
                break
            }

            "5" -> println(flowNote)
            "6" -> break
            else -> println(mistake)
        }

    }
}

const val line = "\u001B[36m-----------------------------------------------------\u001B[0m"
const val mistake = "\u001B[41m\u001B[30m ✘ \u001B[0m Хиба. Спробуйте ще раз!"
const val completeSign = "\u001B[42m\u001B[30m\u001B[1m ✓ \u001B[0m"
const val mistakeSign = "\u001B[41m\u001B[30m ✘ \u001B[0m"