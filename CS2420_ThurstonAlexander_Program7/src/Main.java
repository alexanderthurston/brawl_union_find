import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static final int ATTACKERS = 2; //Variable indicating there must be two attackers

    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"case1.txt", "case2.txt", "case3.txt", "case4.txt", "case5.txt", "case6.txt", "case7.txt" };

        for(String fileName : files) {
            Scanner politicScanner = new Scanner(new File(fileName));
            System.out.println(fileName + "\n");
            UnionFind politicUnionFind = null;
            int[] opponents = null;

            while (politicScanner.hasNextLine()) {

                String line = politicScanner.nextLine();
                line = line.toLowerCase().replaceAll("\\p{Punct}", "").replace("\t", " ").strip();
                String[] attackInfo = line.split(" ");

                //If input is not attackers, guidelines indicate input must be number of individual attackers
                if (attackInfo.length < ATTACKERS) {
                    int size = stringToInt(attackInfo[0]);
                    politicUnionFind = new UnionFind(size); //Set up Union find
                    opponents = new int[size];              //Set up opponent tracking
                    for (int i = 0; i < size; i++) {
                        opponents[i] = -1;
                    }
                    System.out.println(attackInfo[0]);
                    continue;
                }

                //Begin attack phase
                if (politicUnionFind != null) {
                    int attackOneIndex = stringToInt(attackInfo[0]) - 1;
                    int attackTwoIndex = stringToInt(attackInfo[1]) - 1;
                    int root1 = politicUnionFind.find(attackOneIndex);
                    int root2 = politicUnionFind.find(attackTwoIndex);

                    System.out.println("Attack " + (attackOneIndex + 1) + " " + (attackTwoIndex + 1));

                    //Determine if attackers are in the same group. If so, ignore.
                    if(root1 == root2) {
                        System.out.println("Ignored Attack between " + (attackOneIndex + 1) + " & " + (attackTwoIndex + 1));
                        continue;
                    }

                    //Check if there are previous opponents to union with
                    if(opponents[attackOneIndex] != -1) {
                        politicUnionFind.union(politicUnionFind.find(opponents[attackOneIndex]), root2);
                    }
                    if(opponents[attackTwoIndex] != -1){
                        politicUnionFind.union(politicUnionFind.find(opponents[attackTwoIndex]), root1);
                    }

                    //Update opponents
                    opponents[attackOneIndex] = attackTwoIndex;
                    opponents[attackTwoIndex] = attackOneIndex;

                }
            }
            System.out.println(politicUnionFind.toString() + "\n");
        }
    }


    /**
     * Internal method to verify if string contains an int
     * @param s String to be processed
     * @return Integer value of string or -1 if String does not contain an Integer
     */
    public static int stringToInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException ex){
            return -1;
        }
    }
}


