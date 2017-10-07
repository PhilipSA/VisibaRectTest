package com.example.visiba.visibarectest.Fragments.Abstractions

interface IResultReturning<in T> {
    fun finish(result: T?)
}
