package com.agh.abm.pps.util

class Benchmark {

    companion object{
        @JvmStatic
        fun measure(name: String, f:  () -> Any): Any {
            val start = System.currentTimeMillis()
            val res =  f()
            val end = System.currentTimeMillis()
            println("Action $name, TIME: ${end - start} ms")
            return res
        }
    }
}