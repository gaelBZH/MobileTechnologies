fun main()
{
    // 1. Lambdas
    val studentStatus = { name: String, auraColour: String -> "$name has a $auraColour face colour"}
    val res = studentStatus("Mike", "red");

    // 2. Classes
    class Student_2 {
        var name: String = "mike"
            get() = field.replaceFirstChar { it.uppercase() }
        // replace m by M
    }

    // 3. Constructors
    class Student_3(var name: String = "Mike", var university: String = "PK")
    {
        init
        {
            name = name.replaceFirstChar { it.uppercase() }
        }
    }

    // 4. Encapsulation & Class functions
    class Student_4(private var name: String = "Mike", private var university: String = "PK")
    {
        init
        {
            name = name.replaceFirstChar { it.uppercase() }
        }
        val showStudent = {println("Name: ${name}, University: $university")}
    }

    val student4 = Student_4("mike")
    student4.showStudent()

    // 5. Inheritance
    open class Person(val name: String)

    class Student(name: String = "Mike", private val university: String = "PK") : Person(name)
    {
        val showStudent = {println("Name: ${name.replaceFirstChar { it.uppercase() }}, University: $university")}
    }

    val student = Student("Mike", "Politechnika Krakowska")
    student.showStudent()

    val student2 = Student("Boris", "Cracow University of Technology")
    student2.showStudent()

    val student3 = Student()
    student3.showStudent()
}