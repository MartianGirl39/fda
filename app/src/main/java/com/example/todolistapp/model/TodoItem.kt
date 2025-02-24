package com.example.todolistapp.model

import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Id
import com.example.todolistapp.data.JOIN
import com.example.todolistapp.data.Table

@Table("todo_item")
class TodoItem private constructor(
    private val name: String,
    private val description: String,
    private val urgency: Degree,
    private val importance: Degree,
    private var isFinished: Boolean,
    @JOIN(onModel = DueDate::class)private val due: DueDate,
    @JOIN(onModel = Note::class)private val notes: List<Note>,
    @JOIN(onModel = Tag::class)private val tags: List<Tag>,
    @Id()private val id: Long = -1
): DataModel {

    enum class Degree(val value: String) {
        HIGH("High"),
        MID("Mid"),
        LOW("Low")
    }

    companion object {
        class Builder(private val name: String, private val dueDate: DueDate) {
            private var description: String = ""
            private var urgency: Degree = Degree.MID
            private var importance: Degree = Degree.MID
            private var notes: List<Note> = emptyList()
            private var tags: List<Tag> = emptyList()
            private var id: Long = -1
            private var isFinished: Boolean = false

            fun description(description: String) = apply { this.description = description }
            fun urgency(urgency: Degree) = apply { this.urgency = urgency }
            fun importance(importance: Degree) = apply { this.importance = importance }
            fun notes(notes: List<Note>) = apply { this.notes = ArrayList(notes).sortedBy { it.getMessage() } }
            fun tags(tags: List<Tag>) = apply { this.tags = ArrayList<Tag>(tags).sortedBy { it.getName() } }
            fun id(id: Long) = apply { this.id = id }
            fun isFinished(isFinished: Boolean) = apply { this.isFinished = isFinished }

            fun build(): TodoItem {
                return TodoItem(name, description, urgency, importance, isFinished, dueDate, notes, tags)
            }
        }

        fun getBuilder(name: String,dueDate: DueDate) = Builder(name, dueDate)
    }

    fun getName(): String = name
    fun getDescription(): String = description
    fun getUrgency(): Degree = urgency
    fun getImportance(): Degree = importance
    fun getNoteIterator() = ListIterator<Note>(notes)
    fun getTagIterator() = ListIterator<Tag>(tags)
    fun getDueDate() = due
    fun isFinished() = isFinished
    fun toggleFinished() = apply { isFinished = !isFinished }
}
