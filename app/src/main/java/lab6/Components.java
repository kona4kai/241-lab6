/*
 * Kai Wallis
 * Prof. Akter
 * 3 Jun 2024
 */
package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that represents (and creates) graphs based off of text files with edges.
 */
public class Components {
    private boolean[][] adjM; //adjacency matrix of the graph, col index is equivent to number.

    /**
     * Takes a file name from the terminal, converts it into a graph, and prints the number of 
     * @param args
     */
    public static void main(String[] args) {
        try { //attempts to:
            File f = new File(args[0]); //creates file object from input file name
            Scanner sc = new Scanner(f); //adds scanner of file

            Components graph = new Components(sc); //creates new graph
            System.out.println(graph.groupNum()); //prints groups in graph

            sc.close(); //closes scanner
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + args[0]);
        }
    }

    /**
     * Creates a new graph from a text file.
     * @param sc    a text file. The first line of the file contains a single integer, specifying the number of |V |, and each
            remaining line in the file contains two integers indicating that an edge exists between the two nodes.
     */
    private Components(Scanner sc) {
        int i = sc.nextInt(); //finds size of adjacency matrix
        adjM = new boolean[i][i]; //initializes adjacency matrix
        fillAdjM(sc); //fills adjacency matrix

        //used to test AdjM creation:
        // for (boolean[] bs : adjM) {
        //     for (boolean b : bs) {
        //         System.out.print(b + " ");
        //     }
        //     System.out.println();
        // }
    }

    /**
     * Fills the adjacency matrix using remaining lines of the scanner
     * @param args
     */
    private void fillAdjM(Scanner sc) {
        while (sc.hasNext()) {
            int a = sc.nextInt(); //first end of edge
            int b = sc.nextInt(); //second end of edge

            adjM[a][b] = true; //maps b to a
            adjM[b][a] = true; //maps a to b
        }
    }

    /**
     * Returns the number of groups in the graph.
     */
    private int groupNum() {
        boolean[] s = new boolean[adjM.length]; //boolean array representing if nodes have been placed in a group
        int groups = 0;

        for (int i = 0; i < s.length; i++) {
            if(!s[i]) { //for every node not already placed in a group:
                group(i, s);
                groups++;
            }
        }
        return groups;
    }

    /**
     * sets values to true in s for nodes reachable by node n (including n).
     * @param n     int of node to search from
     * @param s     boolean array representing if nodes have been placed in a group
     */
    private void group(int n, boolean[] s) {
        s[n] = true;
        for (int i = 0; i < s.length; i++) {
            if(!s[i] && adjM[n][i]) { //recursively calls method for all neighboring nodes not already toggled
                group(i, s);
            }
        }
    }
}
