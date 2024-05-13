import java.util.*;

public class Person {
    private String name;
    private Set<String> followers;
    private Set<String> following; 

    //constructor that assigns name to object and also initalizes the sets for followers and following
    public Person(String name) {
        this.name = name;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }


    public void follows(Person person) {
        this.following.add(person); // add person to following
        person.followers.add(this); // add follower to the person
    }


        public String getName() {
        return this.name;
    }

    public Set<Person> getFollowers() {
        return this.followers;
    }

    public Set<Person> getFollowing() {
        return this.following;
    }
}