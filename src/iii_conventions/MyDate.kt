package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(n: Int) = RepeatedTimeInterval(this, n)

class DataIterator(var current: MyDate, val end: MyDate): Iterator<MyDate> {
    override operator fun next(): MyDate {
        val rv = current
        current = current.nextDay()
        return rv
    }

    override operator fun hasNext(): Boolean = current <= end
}

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }

    override operator fun iterator(): Iterator<MyDate> {
        return DataIterator(start, endInclusive)
    }
}

operator fun MyDate.plus(t: TimeInterval): MyDate = addTimeIntervals(t, 1)
operator fun MyDate.plus(t: RepeatedTimeInterval): MyDate = addTimeIntervals(t.timeInterval, t.number)
