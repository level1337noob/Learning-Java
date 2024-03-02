import java.util.List;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Function;

class Person {

    public enum Sex {
        MALE ("Male"), FEMALE ("Female");
        
        final String sex;
        
        Sex(String sex) {
            this.sex = sex;
        }
        
        String getSex() {
            return sex;
        }
    }

    String name;
    int age;
    Sex gender;

    Person(String name, int age, Sex gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    String getName() {
        return name;
    }

    Sex getGender() {
        return gender;
    }

    public void printPerson() {
        System.out.printf("Person Name %s Age %d Gender %s\n", name, age, gender.getSex());
    }
}

public class LambdaExpressions {

    interface CheckPerson {
        boolean test(Person p);
    }

    // Approach 1: Create Methods that Search for Persons that Match One
    // Characteristic

    public static void printPersonsOlderThan(List<Person> roster, int age) {
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

    // Approach 2: Create More Generalized Search Methods

    public static void printPersonsWithinAgeRange(
        List<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (p.getAge() >= low && p.getAge() <= high) {
                p.printPerson();
            }
        }
    }

    // Approach 3: Specify Search Criteria Code in a Local Class
    // Approach 4: Specify Search Criteria Code in an Anonymous Class
    // Approach 5: Specify Search Criteria Code with a Lambda Expression

    public static void printPersons(
        List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    // Approach 6: Use Standard Functional Interfaces with Lambda Expressions

    public static void printPersonsWithPredicate(
        List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    // Approach 7: Use Lambda Expressions Throughout Your Application

    public static void processPersons(
        List<Person> roster,
        Predicate<Person> tester,
        Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    // Approach 7, second example

    public static void processPersonsWithFunction(
        List<Person> roster,
        Predicate<Person> tester,
        Function<Person, String> mapper,
        Consumer<String> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }
    
    public static void main(String... args) {
        LinkedList<Person> persons = new LinkedList<Person>();
    
        persons.add(new Person("John", 20, Person.Sex.MALE));
        persons.add(new Person("Alice", 48, Person.Sex.FEMALE));
        persons.add(new Person("Bob", 50, Person.Sex.MALE));

        System.out.println("Approach age greater than x");
        printPersonsOlderThan(persons, 20);

        System.out.println("Approach age with range");
        printPersonsWithinAgeRange(persons, 40, 50);

        System.out.println("Anonymous classes approach");
        printPersons(persons, new CheckPerson() {
            public boolean test(Person p) {
                return p.getGender() == Person.Sex.FEMALE && p.age >= 40 && p.age <= 50;
            }
        });
        
        System.out.println("Lambda expression approach");
        printPersons(persons, (Person p) -> p.getGender() == Person.Sex.MALE && p.age >= 20 && p.age <= 50);


        System.out.println("Predicate lambda expression approach");
        printPersonsWithPredicate(persons, (Person p) -> p.getGender() == Person.Sex.MALE && p.age >= 20 && p.age <= 50);

        System.out.println("Predicate and consumer lambda approach");
        processPersons(persons,
            p -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25,
            p -> p.printPerson());

        System.out.println("Predicate and consumer and function lambda approach");
        processPersonsWithFunction(persons,
            p -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25,
            p -> p.getName(),
            e -> System.out.println(e));
    }
}