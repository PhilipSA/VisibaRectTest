package com.example.visiba.visibarectest.Fragments.Abstractions

interface IResultReturning<in T> {
    fun Finish(result: T?)
}
