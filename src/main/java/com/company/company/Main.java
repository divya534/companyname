package com.company.company;

import java.io.*;

public class Main {

    public final static String TMP_PROP = "java.io.tmpdir";

    public static void main(String[] args) throws IOException {
        printAvgDelta("input.csv", "34 + 0.5x");
    }

    public static void printAvgDelta(String fileName, String expression) throws IOException {


        File file = new File(System.getProperty(TMP_PROP) + fileName);

        if(!file.exists()){
            System.out.println(fileName + " not found in the temp dir: " + System.getProperty(TMP_PROP));
            return;
        }

        double deltaSum = 0;
        int recordCount = 0;

        double constant = 0;
        boolean isAddition = false;
        double xCoEfficient = 0;

        if (expression.contains("+"))
            isAddition = true;
        String[] tokens = null;

        if (isAddition)
            tokens = expression.split("\\+");
        else {
            if (expression.indexOf('-') == 0) {
                tokens = expression.substring(1).split("-");
                tokens[0] = "-" + tokens[0];
            } else
                tokens = expression.split("-");
        }

        constant = Double.valueOf(tokens[0].trim());
        xCoEfficient = Double.valueOf(tokens[1].replace("x", "").trim());


        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        br.readLine(); //Skip headers
        String line;
        while ((line = br.readLine()) != null) {
            String nums[] = line.split(",");
            double x = Double.valueOf(nums[0].trim());
            double y = Double.valueOf(nums[1].trim());
            double yComp = calcYComplement(constant, isAddition, xCoEfficient, x);
            deltaSum += calculateDelta(y, yComp);
            ++recordCount;

        }

        System.out.println(deltaSum / recordCount);


    }


    public static double calcYComplement(double constant, boolean isAddition, double xCoEfficient, double x) {
        if (isAddition)
            return constant + (xCoEfficient * x);
        else
            return constant - (xCoEfficient * x);
    }

    public static double calculateDelta(double a, double b) {
        return Math.pow(a - b, 2);
    }


}