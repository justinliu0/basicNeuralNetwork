import java.util.ArrayList;
import java.util.List;

public class Person {
    private int height;
    private int weight;
    private int gender;
    private static List<Person> people = new ArrayList<>();

    public Person(int weight, int height, int gender) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        people.add(this);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getGender() {
        return this.gender;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public static List<Person> getPeople() {
        return people;
    }

    public static int shiftHeight() {
        int sum = 0;
        for (Person person : people) {
            sum += person.getHeight();
        }
        return sum / people.size();
    }

    public static int shiftWeight() {
        int sum = 0;
        for (Person person : people) {
            sum += person.getWeight();
        }
        return sum / people.size();
    }
}
