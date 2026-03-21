// Task 4
fun auraColor(studentPoints: Int = 2): Unit
{
    val satisfactionLevel = (Math.pow(Math.random(), (110 -studentPoints) / 100.0) * 20).toInt()
    
    val aura = when (satisfactionLevel)
    {
        in 0..5 -> "red"
        in 6..10 -> "orange"
        in 11..15 -> "purple"
        else -> "yellow"
    }
    println(aura)
}

fun printStudentStatus(name: String, auraColor: String)
{
    println("$name has a $auraColor face color")
}

fun main()
{
    // Task 1
    var hasCredit = false
    


    // Task 2
    val universityName = "Politechnika Krakowska"
    val studentName = "Gael"
    val studentId = 182129
    println("Task 1 :")
    println(hasCredit)
    println("\nTask 2 :")
    println(universityName)
    println(studentName)
    println(studentId)

    // Task 3
    val studentPoints = 10
    val satisfactionLevel = (Math.pow(Math.random(), (110 -studentPoints) / 100.0) * 20).toInt()

    val aura = when (satisfactionLevel)
    {
        in 0..5 -> "red"
        in 6..10 -> "orange"
        in 11..15 -> "purple"
        else -> "yellow"
    }
    println("\nTask 3 :")
    println(aura)
    
    // Task 4
    println("\nTask 4 :")
    auraColor(2)
    
    // Task 5
    println("\nTask 5 :")
    printStudentStatus(auraColor = aura, name = studentName)
}