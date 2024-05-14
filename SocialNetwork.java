import java.util.*;

public class SocialNetwork {
    private Map<String, Person> people = new HashMap<>();
    //adding a first person in the network to solve task 4 
    private String firstPersonName = null;

    public void addPerson(String name) {
        people.putIfAbsent(name, new Person(name));
        if (firstPersonName == null) {
            firstPersonName = name;  // Store the name of the first person added
        }
    }
    //adding 
    public String getFirstPerson() {
        return firstPersonName; 
    }

    public void addFollowing(String personName, String followName) {
        Person person = people.get(personName);
        Person followPerson = people.getOrDefault(followName, new Person(followName));
        /*if (!people.containsKey(followName)) {
            System.out.println("Adding new person to network: " + followName);
        }*/
        people.putIfAbsent(followName, followPerson);
        /*System.out.println(personName + " follows " + followName);*/
        person.follows(followPerson);
    }

    /*public void addFollowing(String personName, String followName) {
        //finds the person object by using the hashmap and looking for the key with the personname
        Person person = people.get(personName);
        //attempts to retrieve person object from hashmap with the name of the follower but if it fails itll use a new person object it doesnt create one just uses it for this example
        Person followPerson = people.getOrDefault(followName, new Person(followName));
        // if absent so if its followname is not in the network itll be added here
        people.putIfAbsent(followName, followPerson);
        person.follows(followPerson);
    }*/

    public double calculateDensity() {
        int totalEdges = 0;
        for (Person p : people.values()) {
            totalEdges += p.getFollowing().size();
        }
        int totalPeople = people.size();
        return totalPeople > 1 ? (double) totalEdges / (totalPeople * (totalPeople - 1)) : 0;
    }


    public String getPersonWithMostFollowers() {
        String nameWithMostFollowers = "No one";
        int maxFollowers = -1;

        for (Person person : people.values()) {
            int followersCount = person.getFollowers().size();
            if (followersCount > maxFollowers || (followersCount == maxFollowers && person.getName().compareTo(nameWithMostFollowers) < 0)) {
                maxFollowers = followersCount;
                nameWithMostFollowers = person.getName();
            }
        }
        return nameWithMostFollowers;
    }

    public String GetPersonFollowingMostPeople() {
        String nameWithMostFollowing = "No one";
        int maxFollowing = -1;
        // iterates through the values of the hashmap of the people in the network which contains objects of person
        for (Person person : people.values()) {
            // gets the the size of the following set for each person in the network
            int followingCount = person.getFollowing().size();
            // if the person is following more people than the max following person theyll become the max following
            //also if the follower counts equals up to the same as max itll check alphabetical order of the name
            if (followingCount > maxFollowing || (followingCount == maxFollowing && person.getName().compareTo(nameWithMostFollowing) < 0)) {
                maxFollowing = followingCount;
                nameWithMostFollowing = person.getName();
            }
        }
        return nameWithMostFollowing;
    }




    public void printNetwork() {
        System.out.println("Network Members:");
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            String name = entry.getKey();
            Person person = entry.getValue();
            System.out.println(name + " -> Followers: " + person.getFollowers().size() + ", Following: " + person.getFollowing().size());
        }
    }

    public void printFollowerCounts() {
        System.out.println("Follower Counts:");
        people.values().stream()
            .sorted(Comparator.comparing(Person::getName))  // Sort alphabetically for easier verification
            .forEach(p -> System.out.println(p.getName() + " has " + p.getFollowers().size() + " followers."));
    }


    public int getTwoDegreesSeparation(String personName) {
        Person person = people.get(personName);
        if (person == null) return 0;

        Set<Person> oneDegreeFollowers = new HashSet<>();
        // Identify all people who follow the person directly
        for (Person p : people.values()) {
            if (p.getFollowing().contains(person)) {
                oneDegreeFollowers.add(p);
            }
        }

        Set<Person> twoDegrees = new HashSet<>();
        // For each direct follower, get their followers (second degree)
        for (Person follower : oneDegreeFollowers) {
            for (Person secondDegree : people.values()) {
                if (secondDegree.getFollowing().contains(follower) && !secondDegree.equals(person) && !oneDegreeFollowers.contains(secondDegree)) {
                    twoDegrees.add(secondDegree);
                }
            }
        }

        return twoDegrees.size();
    }


    public int getMedianFollowers() {
        List<Integer> followerCounts = new ArrayList<>();
        for (Person person : people.values()) {
            followerCounts.add(person.getFollowers().size());
        }
        Collections.sort(followerCounts);

        if (followerCounts.isEmpty()) {
            return 0; //returns 0 if no follwers
        }

        int middle = followerCounts.size() / 2;
        if (followerCounts.size() % 2 == 1) {
            return followerCounts.get(middle);
        } else {
            return (followerCounts.get(middle - 1) + followerCounts.get(middle)) / 2;
        }
    }












}
