public class Analysis {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Analysis <inputFile>");
            return;
        }

        String inputFile = args[0];
        SocialNetwork network = new SocialNetwork();
        FileReader fileReader = new FileReader(inputFile);
        fileReader.displayFileContents(network);

        String firstPerson = network.getFirstPerson();
        System.out.println("Density: " + network.calculateDensity());
        System.out.println("Person with most followers: " + network.getPersonWithMostFollowers());
        System.out.println("Person who follows the most: " + network.GetPersonFollowingMostPeople());
        System.out.println("Two degrees of separation from " + firstPerson + ": " + network.getTwoDegreesSeparation(firstPerson));
        System.out.println("The median value for the number of followers in the network: "  + network.getMedianFollowers());

        System.out.println("Most influentual person in the network: "+ network.findBestPersonForMessageSpread());

    }
}
