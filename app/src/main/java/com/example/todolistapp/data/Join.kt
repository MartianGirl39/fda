package com.example.todolistapp.data

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class JOIN(val onModel: KClass<out DataModel>, val onValue: String = "id")

