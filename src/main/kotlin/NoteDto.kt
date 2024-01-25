import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class NoteDto(
    @SerializedName("title") val title: String,
    @SerializedName("noteText") val noteText: String,
    @SerializedName("date") val date: String = getLocalTime(),
) : Serializable {

    override fun toString(): String {
        return "Назва: \u001B[4m\u001B[32m$title\u001B[0m\nДата створення: $date\n$noteText"
    }
}

private fun getLocalTime() = LocalDateTime.now().format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
)
