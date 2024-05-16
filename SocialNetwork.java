import java.util.*;

public class SocialNetwork {
    private Map<String, Person> people = new HashMap<>();
    //adding a first person in the network to solve task 4 
    private String firstPersonName = null;

    public void addPerson(String name) {
        // Check if the map does not already contain the person
        if (people.containsKey(name) == false) {  
            // If the person is not in the map, add them
            Person newPerson = new Person(name);
            //adding them to the network
            people.put(name, newPerson);
        }
        
        // Check if this is the first person being added
        if (firstPersonName == null) {
            // Store the name of the first person added
            firstPersonName = name;
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
    // used the formula to calculate density
    // got edges by counting the amount of following each person has in the network
    public double calculateDensity() {
        int totalEdges = 0;
        for (Person p : people.values()) {
            totalEdges += p.getFollowing().size();
        }
        
        int totalPeople = people.size();
        return totalPeople > 1 ? (double) totalEdges / (totalPeople * (totalPeople - 1)) : 0;
    }

    // goes through the network and compares person followers with next person followers and replace name with most followers if the next person has more followers
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



//used for debugging 

    // used for debugging
    public void printFollowerCounts() {
        System.out.println("Follower Counts:");
    }

    // makes 2 sets 1 for 1st degree and goes through people network and checks if they are following the specified person if they are they are added to 1st degree then it goes through the first degree follower's followers to add them to 2nd degree but it also makes sure they are not part of 1st degree or the person themself
    public int getTwoDegreesSeparation(String personName) {
        Person person = people.get(personName);
        if (person == null) return 0;

        Set<Person> oneDegreeFollowers = new HashSet<>();
        // Identify all people who follow the person directly
        for (Person p : people.values()) {
            //checks for everyone following the person and adding them to one degree followers
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

    // makes a list for all the follower counts and sorts it then finds the median from that
    public int getMedianFollowers() {
        // makes a list for all the follower counts 
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




    public String findBestPersonForMessageSpread() {
        Map<Person, Integer> reachMap = new HashMap<>();
        int maxValue= -1;
        Person maxKey = null;

        for (Person starter : people.values()) {
            Set<Person> seen = new HashSet<>();
            List<Person> theFollowersToGoThrough = new ArrayList<>();
            theFollowersToGoThrough.add(starter);
            seen.add(starter);  // Adding the starter to the seen set at the beginning

            while (theFollowersToGoThrough.size() != 0) {
                //adds the person to current then removes them from the people to go through
                Person current = theFollowersToGoThrough.get(0);  
                theFollowersToGoThrough.remove(0);  
                for (Person follower : current.getFollowers()) {
                    if (!seen.contains(follower)) {
                        seen.add(follower);  // Add new follower to the seen set
                        theFollowersToGoThrough.add(follower);  // Add to list to process their followers in the next iterations
                    }
                }
            }

            // Store the reach size, excluding the person themselves
            reachMap.put(starter, seen.size() - 1);
        }

        // Find the person with the maximum reach
        for (Map.Entry<Person, Integer> entry : reachMap.entrySet()) {
            if (entry.getValue() > maxValue || (entry.getValue() == maxValue && entry.getKey().getName().compareTo(maxKey.getName()) < 0)) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        return maxKey.getName();
    }





}
