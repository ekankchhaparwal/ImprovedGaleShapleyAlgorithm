package com.company.DSA_Project_GaleShapley;

import java.util.*;

public class MatchMakingBetterAlgo {
    static class Pairs{
        int data;
        int female;
        int male;
        Pairs(int data,int female,int male) {
            this.data = data;
            this.female = female;
            this.male = male;
        }
    }
    static class pair{
        int score;
        int personNumber;
        pair(int score,int personNumber) {
            this.score = score;
            this.personNumber = personNumber;
        }
    }
    static class pairsComparator implements  Comparator<Pairs>{
        @Override
        public int compare(Pairs o1, Pairs o2) {
            if(o1.data<o2.data){
                return 1;
            } else if (o1.data>o2.data){
                return -1;
            }
            return 0;
        }
    }

    public static void insertNames(int n,String []males,String [] females) {
        for (int i=1;i<=n;i++) {
            males[i] = "Male-"+i;
            females[i] = "Female-"+i;
        }
    }

    public static void createPriorityForMales(int n, List<HashMap<Integer,Integer>> malesList,int [][] malesPriority) {
        Random rd = new Random();
        for (int i=0;i<n;i++) {
            int count = 0;
            while (count<n){
                int x = rd.nextInt()%n;
                x = Math.abs(x)+1;
                if (malesList.get(i).containsKey(x)) {
                    continue;
                }
                malesList.get(i).put(x,count+1);
                malesPriority[i][count] = x;
                count++;
            }
        }
    }

    public static void createPriorityForFemales(int n, List<HashMap<Integer,Integer>> femalesList,int [][] femalesPriority) {
        Random rd = new Random();

        for (int i=0;i<n;i++) {
            int count = 0;
            while (count<n){
                int x = rd.nextInt()%n;
                x = Math.abs(x)+1;
                if (femalesList.get(i).containsKey(x)) {
                    continue;
                }
                femalesList.get(i).put(x,count+1);
                femalesPriority[i][count] = x;
                count++;
            }
        }
    }

    public static void printPriorityofMen(int n,int [][]maleList,String []males) {

        for (int i = 0; i <n ; i++) {
            System.out.print(males[i+1]+"| ");
            for (int j = 0; j <n ; j++) {
                System.out.print(maleList[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void printPriorityofWomen(int n , int [][]femalesPriority,String []females) {
        for (int i = 0; i <n ; i++) {
            System.out.print(females[i+1]+"| ");
            for (int j = 0; j <n ; j++) {
                System.out.print(femalesPriority[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void calculateWeights(int n, List<HashMap<Integer, Integer>> femalesList, List<HashMap<Integer, Integer>> malesList, int[][] finalWeight, PriorityQueue<Pairs> priorityQueue) {
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                finalWeight[i][j] = (n+1-femalesList.get(j).get(i+1))*(n+1-malesList.get(i).get(j+1));
                priorityQueue.add(new Pairs(finalWeight[i][j],j+1,i+1));
            }
        }

    }
    public static void printWeightMatrix(int finalWeight[][] ,int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(finalWeight[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int matchPairsWithCollisionResolution(int n,PriorityQueue<Pairs> priorityQueue, List<HashMap<Integer, Integer>> malesList, List<HashMap<Integer, Integer>> femaleList) {
        int pairsMatched = 0;
        HashMap<Integer,pair> maleDone = new HashMap<>();
        HashMap<Integer,pair> femaleDone = new HashMap<>();
        Queue<Pairs> queue = new LinkedList<>();
        while (pairsMatched < n) {
            if (priorityQueue.isEmpty()){
                break;
            }
            if (maleDone.containsKey(priorityQueue.peek().male)||femaleDone.containsKey(priorityQueue.peek().female)){
                priorityQueue.remove();
                continue;
            }
            int max = priorityQueue.peek().data;
            while (!priorityQueue.isEmpty() && max==priorityQueue.peek().data){
                queue.add(priorityQueue.remove());
            }
            while (!queue.isEmpty()){
                Pairs pair = queue.remove();
                int score = pair.data;
                int male = pair.male;
                int female = pair.female;
                if (!maleDone.containsKey(male) && !femaleDone.containsKey(female)){
                    maleDone.put(male,new pair(score,female));
                    femaleDone.put(female,new pair(score,male));
                    pairsMatched++;
                } else if (maleDone.containsKey(male) && !femaleDone.containsKey(female)){
                    int oldFemalePaired = maleDone.get(male).personNumber;
                    if (malesList.get(male-1).get(oldFemalePaired)>malesList.get(male-1).get(female)){
                        femaleDone.remove(oldFemalePaired);
                        maleDone.get(male).personNumber = female;
                        femaleDone.put(female,new pair(score,female));
                    }
                } else if (femaleDone.containsKey(female) && !maleDone.containsKey(male)){
                    int oldMalePaired = femaleDone.get(female).personNumber;
                    if (femaleList.get(female-1).get(oldMalePaired)>femaleList.get(female-1).get(male)){
                        maleDone.remove(oldMalePaired);
                        femaleDone.get(female).personNumber = male;
                        maleDone.put(male,new pair(score,female));
                    }
                }
            }
        }
        int totalWeight = 0;
        for(int male:maleDone.keySet()){
            System.out.println(male+" -- "+maleDone.get(male).personNumber);
            totalWeight += maleDone.get(male).score;
        }
        System.out.println("Total Weight is :");
        return totalWeight;
    }
        public static void main(String[]args){
        int tt = 0;
                Scanner sc = new Scanner(System.in);
         tt = sc.nextInt();
         int t = 0;
         float avg= 0.0f;
            while (t<tt){
                System.out.print("Enter the Number of Pairs to be Matched : ");
                int n = 50;

                String males[] = new String[n + 1];
                String females[] = new String[n + 1];

                insertNames(n, males, females);
                List<HashMap<Integer, Integer>> malesList = new ArrayList<HashMap<Integer, Integer>>();
                List<HashMap<Integer, Integer>> femaleList = new ArrayList<HashMap<Integer, Integer>>();
                for (int i = 0; i < n; i++) {
                    HashMap<Integer, Integer> malePriority = new HashMap<>();
                    HashMap<Integer, Integer> femalePriority = new HashMap<>();
                    malesList.add(malePriority);
                    femaleList.add(femalePriority);
                }
                int malesPriority[][] = new int[n][n];
                int femalesPriority[][] = new int[n][n];

                createPriorityForMales(n, malesList, malesPriority);
                createPriorityForFemales(n, femaleList, femalesPriority);

                printPriorityofMen(n, malesPriority, males);
                printPriorityofWomen(n, femalesPriority, females);


                int finalWeights[][] = new int[n][n];
                PriorityQueue<Pairs> priorityQueue = new PriorityQueue<>(new pairsComparator());

                calculateWeights(n, femaleList, malesList, finalWeights, priorityQueue);
                printWeightMatrix(finalWeights,n);
                int mg = matchPairsWithCollisionResolution(n, priorityQueue, malesList, femaleList);
                System.out.println(mg);
                System.out.println("Running for GaleShapley");
                System.out.println("Total Weight is ");
                int g = GaleShapley.stableMarriage(malesPriority,femalesPriority,finalWeights);
                System.out.println(g);
                avg += (mg-g)*100/(float)g;
            t++;
            }
            System.out.println(avg/t);
        }

    }

