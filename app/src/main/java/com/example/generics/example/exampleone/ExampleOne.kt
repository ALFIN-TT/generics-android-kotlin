package com.example.generics.example.exampleone


fun index() {
    //Imagine you are managing a zoo or an exotic pet shop so that you have to organize and keep a reference of all animal species available.
    // You will most likely put each animal in a cage and declare several attributes and actions associated.
    CreateCageForSpecificAnimal()
    CreateCageForAll()
    CreateCageForAnimal()
    GenericInheritance()
    Covariance()
    Contravariance()
    TypeProjection()
    Usage()
    UsageInList()
}

fun CreateCageForSpecificAnimal() {
    // For instance, for a dog
    //define a cage
    class Cage(var dog: Dog, val size: Double)
    //add a dog.
    val dog = Dog(id = 0, name = "Doglin", furColor = FurColor.BLACK)
    //create a cage for dog.
    val cage = Cage(dog = dog, size = 6.0)
    //In this case cage can be use only for dog.
    //means this cage cannot use for cat.
}


fun CreateCageForAll() {
    //define a new cage
    class Cage<T>(var animal: T, val size: Double)
    //add a dog.
    val dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    //add a cat
    val cat: Cat = Cat(id = 4, name = "Peter", eyesColor = EyesColor.GREEN)
    //create a cage for dog.
    val cageDog: Cage<Dog> = Cage(animal = dog, size = 6.0)
    //create a cage for dog.
    val cageCat: Cage<Cat> = Cage(animal = cat, size = 3.0)
    //In this case cage can be use only for all animal.

    //But...
    val cageString: Cage<String> = Cage(animal = "This cage hosts a String?", size = 0.1)
    //The above is syntactically valid, though ridiculous, Because String not a Animal
}

fun CreateCageForAnimal() {
    //Class Hierarchy
    //__Animal
    //     |_____Dog
    //     |_____Cat
    //     |_____Bird
    //            |____Eagle
    //            |____Canary
    //            |____Penguin

    //define a new cage with generic animal type
    class Cage<T : Animal>(var animal: T, val size: Double)
    //add a dog.
    val dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    //add a cat
    val cat: Cat = Cat(id = 4, name = "Peter", eyesColor = EyesColor.GREEN)
    //create a cage for dog.
    val cageDog: Cage<Dog> = Cage(animal = dog, size = 6.0)
    //create a cage for dog.
    val cageCat: Cage<Cat> = Cage(animal = cat, size = 3.0)

    //TODO Uncomment code below...
    /*val cageString: Cage<String> = Cage(animal = "This cage hosts a String?", size = 0.1)*///shows compile time error
    //Now, Cage only permits data types inheriting from Animal.
}

fun GenericInheritance() {
    class Cage<T : Animal>(var animal: T, val size: Double)

    //example of inheritance and polymorphism,
    var animal: Animal = Dog(id = 0, name = "Doglin", furColor = FurColor.BLACK)
    var dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    animal = dog
    dog = animal
    //it works.


    //But here,
    var cageAnimal: Cage<Animal>
    var cageDog: Cage<Dog> = Cage(animal = dog, size = 3.2)
    // TODO: Uncomment code
    /*cageAnimal = cageDog*/   // error condition

    //and here
    var _cageAnimal: Cage<Animal>
    var _cageDog: Cage<Dog> = Cage(animal = dog, size = 3.2)
    // TODO: Uncomment code
    /*cageAnimal = _cageDog*/   // assume no error this time
    // if allowed, the following could apply
    val _cat: Cat = Cat(id = 2, name = "Tula", eyesColor = EyesColor.YELLOWISH)
    // TODO: Uncomment code
    /*cageAnimal.animal = _cat   // assigning a Cat to Dog type!
    val _dog: Dog = cageAnimal.animal*/   // ClassCastException: a Cat is not a Dog
    //it not work with generics?

    // And this is why Kotlin forbids this sort of relationships.
    // It is a way of guaranteeing stability at runtime.
    // If this wasn’t the case, a Cage<Animal> object would be holding a reference to a Cage<Dog>.
    // Then, a careless developer could try including a Cat into cageAnimal, dismissing the fact that it actually refers to cageDog.


    // Generally speaking, Variance defines Inheritance relationships of parameterized types.
    // Variance is all about sub-typing. Thus, we can say that Cage is invariant in the parameter T.
}

fun Covariance() {

    // Here allowing relationships like Cage<Dog> being a sub-type of Cage<T: Animal>
    // The main idea is that language limitations on this topic arise when trying to read and modify generic data defined in a type.
    // The solution proposed is constraining this read/write access to only allow one of them.
    // In other words, the only reason why the compiler does not permit the assignation cageAnimal = cageDog,
    // is to avoid a situation where the developer decides to modify (write) the value cageAnimal.animal.
    // What if we could forbid this operation so that this generic class would be read-only?

    class CovariantCage<out T : Animal>(private val t: T?) {
        fun getId(): Int? = t?.id
        fun getName(): String? = t?.name
        fun getContentType(): T? = t?.let { t } ?: run { null }
        fun printAnimalInfo(): String = "Animal ${t?.id} is called ${t?.name}"
    }

    // T is only going to be produced by the methods of this class.
    // Thus, it must only appear in out positions. For this reason, T is the return type of the function getContentType(),
    // for example. None of the other methods include T as an input argument either.
    // This makes CovariantCage covariant in the parameter T

    val dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    var cage1: CovariantCage<Dog> = CovariantCage(dog)
    var cage2: CovariantCage<Animal> = cage1   // thanks to being 'out'

    // By making CovariantCage<Dog> extend from CovariantCage<Animal>, in run-time, any valid type (Animal, Dog, Bird, etc.) will replace T, so type-safety is guaranteed.
}

fun Contravariance() {
    class ContravariantCage<in T : Bird>(private var t: T?) {
        fun getId(): Int? = t?.id
        fun getName(): String? = t?.name
        fun setContentType(t: T) {
            this.t = t
        }

        fun printAnimalInfo(): String = "Animal ${t?.id} is called ${t?.name}"
    }

    //  Here, setContentType replaces getContentType from the previous snippet.
    // Thus, this class always consumes T. Therefore, the parameter T takes only in positions in the class methods.
    // This constraint leads to state, for example, that ContravarianceCage<Animal> is a sub-type of ContravarianceCage<Bird>

}

fun TypeProjection() {
    // The idea behind this is indicating a variance constraint at the precise moment in which you use a parameterized class, not when you declare i
    class Cage<T : Animal>(val animal: T, val size: Double)

    fun examine(cageItem: Cage<out Animal>) {
        val animal: Animal = cageItem.animal
        println(animal)
    }

    val bird: Bird =
        Eagle(id = 7, name = "Piti", featherColor = FeatherColor.YELLOW, maxSpeed = 75.0f)
    val animal: Animal = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    val cage01: Cage<Animal> = Cage(animal = animal, size = 3.1)
    val cage02: Cage<Bird> = Cage(animal = bird, size = 0.9)
    examine(cage01)
    examine(cage02)   // 'out' provides type-safety so that this statement is valid
}

// class Cage<out T : Animal>(val animal: T, val size: Double)
class Cage<out T : Animal>(val animal: T, val size: Double)

//Create the generic interface declaration
interface Repository<S : Cage<Animal>> {
    fun registerCage(cage: S): Unit
}

fun Usage() {

    class AnimalRepository : Repository<Cage<Animal>> {
        override fun registerCage(cage: Cage<Animal>) {
            println("registering cage for: ${cage.animal.name}")
        }
    }
    //this is valid

    class BirdRepository : Repository<Cage<Bird>> {
        override fun registerCage(cage: Cage<Bird>) {
            println("registering cage for: ${cage.animal.name}")
        }
    }
    //error.

    // The reason is that Repository expects an argument T of type Cage<Animal> or a child of it. By default, the latter does not apply to Cage<Bird>. Fortunately, there is an easy solution which consists of using declaration-site variance on Cage

    //, so that:
    // class Cage<out T : Animal>(val animal: T, val size: Double)
    //replace cage class with above code

    //This new condition also brings a limitation to Cage, since it will never include a function having T as an input argument

    // TODO: Uncomment code
    /* fun sampleFun(t: T) {
         println("dummy behavior")
     }*/

    //As a rule of thumb, you should use out T in classes and methods that will not modify T, but produce or use it as an output type. Contrary, you should use in T in classes and methods that will consume T,
    // i.e. using it as an input type. Following this rule will buy you type-safety when establishing class hierarchy sub-typing.
}

fun UsageInList() {
    var list0: MutableList<Animal>
    val dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    val list1: MutableList<Dog> = mutableListOf(dog)
    // TODO: Uncomment code
    /*list0 = list1*/   // the IDE reports an error

    //The error reported, as you can imagine, relates to MutableList<Dog> not extending from MutableList<Animal>. However, this assignation is OK if you ensure modifications won’t happen in this collections,

    var _list0: MutableList<out Animal>
    val _dog: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    val _list1: MutableList<Dog> = mutableListOf(dog)
    _list0 = _list1   // the IDE reports an error
}
