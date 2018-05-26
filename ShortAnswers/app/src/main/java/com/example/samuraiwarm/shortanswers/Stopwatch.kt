package com.example.samuraiwarm.shortanswers

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*


class StopWatch(val currentTime: () -> Long = { System.nanoTime() }) {
    private var start: Long = 0
    private var elapsedNanoseconds: Long = 0
    private var running: Boolean = false

    fun start(): StopWatch {
        check(!running) { "The stop watch is already started" }
        start = currentTime()
        running = true
        return this
    }

    inline fun <R> time(f: () -> R): R {
        start()
        try {
            return f()
        } finally {
            stop()
        }
    }

    fun stop(): StopWatch {
        check(running) { "The stop watch is already stopped" }
        running = false
        elapsedNanoseconds += currentTime() - start
        return this
    }

    fun reset(): StopWatch {
        elapsedNanoseconds = 0
        running = false
        return this
    }

    fun elapsed(unit: TimeUnit): Long {
        val elapsed = if (running) elapsedNanoseconds + currentTime() - start else elapsedNanoseconds
        return unit.convert(elapsed, NANOSECONDS)
    }

    override fun toString() = toString(MILLISECONDS)

    fun toString(unit: TimeUnit, precision: Int = 3): String {
        val value = elapsed(NANOSECONDS).toDouble() / NANOSECONDS.convert(1, unit)

        return "%.${precision}f %s".format(value, unit.abbreviation())
    }
}

fun TimeUnit.abbreviation(): String = when (this) {
    NANOSECONDS -> "ns"
    MICROSECONDS -> "\u03bcs"
    MILLISECONDS -> "ms"
    SECONDS -> "s"
    MINUTES -> "m"
    HOURS -> "h"
    DAYS -> "d"
    else -> name
}