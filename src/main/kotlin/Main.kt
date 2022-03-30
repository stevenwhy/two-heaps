import java.util.*

fun main() {
    val stream = NumberStream()
    stream.insertNum(3)
    stream.insertNum(1)
    println("expected median=2 actual=${stream.findMedian()}")
    stream.insertNum(5)
    println("expected median=3 actual=${stream.findMedian()}")
    stream.insertNum(4)
    println("expected median=3.5 actual=${stream.findMedian()}")

}

class NumberStream() {
    // max heap
    private val firstHalf: PriorityQueue<Int> = PriorityQueue(compareByDescending { it })
    // min heap
    private val secondHalf: PriorityQueue<Int> = PriorityQueue()

    // O(log(n)) insertion in priority queues
    fun insertNum(num: Int) {
        if(firstHalf.isEmpty()) firstHalf.add(num)
        else if(secondHalf.isEmpty()) {
            if(num < firstHalf.peek()) {
                secondHalf.add(firstHalf.poll())
                firstHalf.add(num)
            }
        } else if(num < firstHalf.peek()) {
            // keep balance
            if(firstHalf.size > secondHalf.size) secondHalf.add(firstHalf.poll())
            firstHalf.add(num)
        }
        else if(num > secondHalf.peek()) {
            // keep balance
            if(secondHalf.size > firstHalf.size) firstHalf.add(secondHalf.poll())
            secondHalf.add(num)
        }
        else {
            if(secondHalf.size > firstHalf.size) firstHalf.add(num)
            else secondHalf.add(num)
        }
        println("First half: $firstHalf")
        println("Second half: $secondHalf")
    }

    fun findMedian(): Double {
        if(secondHalf.size == firstHalf.size) return (secondHalf.peek() + firstHalf.peek()) / 2.0
        else if(secondHalf.size > firstHalf.size) return secondHalf.peek().toDouble()
        return firstHalf.peek().toDouble()
    }
}