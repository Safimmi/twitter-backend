package com.safi;

// Write/Read Libraries
import java.io.File;
import java.io.FileWriter;

// Exception Libraries
import java.io.FileNotFoundException;
import java.io.IOException;

// User input Library
import  java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Read file with data input and format it --> file path by command line
        // ../Input/InputFile_01.txt
        String fileDir = getInputFileDir();
        int n = getInputFile_graphSize(fileDir);

        //Create arrays for room names (Node V) and Dirt Values (Edge/Cost E)
        //Create array and int for final maximum path and end node
        //Fill Data
        String roomNames[] = new String[n];
        int dirtValue[] = new int[n];
        getInputFile_graphData(fileDir, n, roomNames, dirtValue);

        /*
        Create Adjacency Matrix:
            It only focuses on the connections of the graph and their weights respectively.
            It does not include rooms constraints (Dirt value constraint)
            Upper Triangular Matrix (Room Order)
        */
        int graph[][] = new int [n][];
        int dirtValueOg[] = dirtValue.clone();

        for (int i = 0; i<n; i++){
            /*
            //Final Node Access
            if(i==0 || i==n-1){dirtValue[n-1]=0;}
            else{dirtValue[n-1]=1;}
            */
            graph[i] = dirtValue.clone();
            if(i<n-1) {dirtValue[i+1] = 0; }
        }

        // Find Max Path (Based on Dijkstra) from start node to every other node.
        dijkstra(graph, 0, roomNames, dirtValueOg);

    }

    public static void dijkstra(int[][] graph, int startNode, String[] roomNames, int[] dirtValue){

        int nVertex = graph.length; //Number of nodes in the graph
        boolean[] isVisited = new boolean[nVertex]; //Checks if the node is already visited
        int[] sumCost = new int[nVertex]; // Accumulated cost of maximum path (Dirt Level)
        int[] previous = new int[nVertex]; // Previous node to follow the maximum path

        //Set every list to initial state
        for (int i = 0; i < nVertex; i++){
            isVisited[i] = false; //All nodes are unvisited
            sumCost[i] = -1; //The maximum path is yet not defined
            previous[i] = 0; //The previous node for all nodes is the start node
        }

        //Start Node values
        int finalNode = 0; //Final node where the path should end (Most dirt quantity possible to clean)
        sumCost[startNode] = 0; // Cost of start node to itself is zero
        previous[startNode] = -1; // Previous of start node it's none

        for (int i = 0; i < nVertex; i++){

            // Find the connected node with the most cost possible (from the start node)
            // {u,v} = {row,colum}
            // Then mark that node as visited
            int u = findMaxCost(sumCost, isVisited);
            isVisited[u] = true;

            /*
            Update cost for every node connected to the previous node find
            Constraint:
                Follow dirt level constraint (previous room >= next room)
                The start node can always access to every other room
                First List of sumCost = [0,... dirt level of every room] --> sumCost[u]
            */

            for (int v =0 ; v < nVertex; v++){

                // Update path: total cost, final room index, previous room (path) index
                // is unvisited && is connected && new add path cost > previous one && current dirt level >=
                if(!isVisited[v] && graph[u][v] != 0 && (sumCost[u] + graph[u][v] > sumCost[v]) && graph[u][v] >= graph[0][v]){
                    sumCost[v] = sumCost[u] + graph[u][v];
                    previous[v] = u;
                }
                //Update final room
                if (sumCost[finalNode] < sumCost[v]){ finalNode = v; }

            }

        }


        //Print Solution
        for (int i = 0; i < sumCost.length; i++){
            System.out.println(String.format("%s. Distance from source vertex %s to vertex %s is %s, and the previous node is %s",i , startNode, i, sumCost[i], previous[i]));
        }
        System.out.println("Final Room is : " + finalNode);

        //Create a write file with output
        exportOutputFile(roomNames, dirtValue, previous, finalNode);

    }

    private static int findMaxCost(int[] sumCost, boolean[] isVisited) {

        int maxCost = -1;
        int maxCostIndex = -1;

        for (int i =0; i < sumCost.length; i++){
            // For all the unvisited nodes, acumulado >
            if(!isVisited[i] && sumCost[i] > maxCost){
                maxCost = sumCost[i];
                maxCostIndex = i;
            }
        }

        //Exception when the list have a room that is already clean (dirtValue = 0)
        if (sumCost[0] != 0 || maxCostIndex == -1) { maxCostIndex = 0;}

        return maxCostIndex;
    }

    public static String getInputFileDir(){

        // Get file name path by command line
        Scanner scanner = new Scanner(System.in);
        String fileDir = scanner.nextLine();

        try {
            File file = new File(fileDir);
            Scanner inputFile = new Scanner(file);
            inputFile.close();
            return fileDir;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
            return " ";
        }

    }

    public static int getInputFile_graphSize(String fileDir){

        try {
            File file = new File(fileDir);
            Scanner inputFile = new Scanner(file);

            // Size of graph (number of nodes V)
            String data = inputFile.nextLine();
            int n = Integer.parseInt(data) + 1;

            //Separate data into room names and dirt values (cost of edges E)
            String inputRoomNames[] = new String[n];
            int inputDirtValues[] = new int[n];
            //Add data to the extra node (start node)
            inputRoomNames[0] = "Start";
            inputDirtValues[0] = 0;

            //Add data of the other nodes
            int i = 1;
            while (inputFile.hasNextLine() && i < n) {

                //Obtain first line and separate in two strings by a comma
                //Delete spaces for second string (dirt value) and add info to the specified array
                data = inputFile.nextLine();
                String[] inputData = data.split(",");

                inputRoomNames[i] = inputData[0];
                inputDirtValues[i] = Integer.parseInt(inputData[1].replaceAll("\\s",""));

                i++;
            }

            inputFile.close();
            return n;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
            return 0;
        }

    }

    public static void getInputFile_graphData(String fileDir, int n, String[] inputRoomNames, int[] inputDirtValues){

        try {
            File file = new File(fileDir);
            Scanner inputFile = new Scanner(file);

            //Skip first Line
            String data = inputFile.nextLine();

            //Separate data into room names and dirt values (cost of edges E)
            //Add data to the extra node (start node)
            inputRoomNames[0] = "Start";
            inputDirtValues[0] = 0;

            //Add data of the other nodes
            int i = 1;
            while (inputFile.hasNextLine() && i < n) {

                //Obtain first line and separate in two strings by a comma
                //Delete spaces for second string (dirt value) and add info to the specified array
                data = inputFile.nextLine();
                String[] inputData = data.split(",");

                inputRoomNames[i] = inputData[0];
                inputDirtValues[i] = Integer.parseInt(inputData[1].replaceAll("\\s",""));

                i++;
            }

            inputFile.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
        }

    }

    public static void exportOutputFile(String[] roomNames, int[] dirtValue, int[] previous, int finalRoom){

        try {

            //Create File
            File file = new File("result.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            //Write Output
            FileWriter fileWriter = new FileWriter("result.txt");
            String outputString = "";
            int nRooms = 0;
            while (finalRoom > 0) {
                outputString = roomNames[finalRoom] + String.format(", %s \n", dirtValue[finalRoom]) + outputString;
                finalRoom = previous[finalRoom];
                nRooms++;
            }
            fileWriter.write(String.format("%s \n", nRooms));
            fileWriter.write(outputString);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred. The file couldn't be created.");
            e.printStackTrace();
        }

    }

}
