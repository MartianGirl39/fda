package com.example.todolistapp.model

class TodoItem private constructor(
    private val name: String,
    private val description: String,
    private val urgency: Degree,
    private val importance: Degree,
    private val due: DueDate,
    private val notes: List<String>,
    private val tags: List<Tag>
) {

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
            private var notes: List<String> = emptyList()
            private var tags: List<Tag> = emptyList()

            fun description(description: String) = apply { this.description = description }
            fun urgency(urgency: Degree) = apply { this.urgency = urgency }
            fun importance(importance: Degree) = apply { this.importance = importance }
            fun notes(notes: List<String>) = apply { this.notes = ArrayList(notes).sortedBy { it } }
            fun tags(tags: List<Tag>) = apply { this.tags = ArrayList<Tag>(tags).sortedBy { it.getName() } }

            fun build(): TodoItem {
                return TodoItem(name, description, urgency, importance, dueDate, notes, tags)
            }
        }
    }

    fun getName(): String = name
    fun getDescription(): String = description
    fun getUrgency(): Degree = urgency
    fun getImportance(): Degree = importance
    fun getNoteIterator() = ListIterator<String>(notes)
    fun getTagIterator() = ListIterator<Tag>(tags)
    fun getDueDate() = due
}
