package com.company;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //uworzeine danych testowych
                DataSet trainingData = new DataSet(2, 1);
                trainingData.setLabel("TrainingData");
        //dane do normalizacji danych wejściowych  i wyjściowych
                double a = 0.5;
                double b = (-1.0-(a*(-2.0)));

                double a2 = Math.abs(2/(80.54857778-0.101784126));
                double b2 = (-1.0-(a2*(0.101784126)));
        //wczytanie danych wejściowcyh z pliku
                File file = new File("learning_data.txt");
                Scanner in = null;
                double[][] data = new double[40][3];
                String[] result;
                try {
                    in = new Scanner(file);
                    for(int i=0;i<40;i++){
                        //while (in.hasNextLine()){
                        String line = in.nextLine();
                        result = line.split("\\s");
                        trainingData.addRow(new DataSetRow(new double[]{a*Double.parseDouble(result[0]) + b, a*Double.parseDouble(result[1])+b},new double[]{a2*Double.parseDouble(result[2])+b2}));
                        }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        //wybranie metody backPropagation i ustawienie max łędu i współczynnika uczenia
                    BackPropagation backPropagation = new BackPropagation();
                    backPropagation.setMaxError(0.01);
                    backPropagation.setLearningRate(0.01);
        //utworzenie sieci wielowarstwowej i ustalenie ilośći warstw wewnętrznych
                    MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 2, 5, 1);
                    multiLayerPerceptron.setLabel("MyNetwork");

                    multiLayerPerceptron.setLearningRule(backPropagation);

                    System.out.println("Teaching: " + multiLayerPerceptron.getLabel() + ", with data set: " + trainingData.getLabel());
                    System.out.println("Using algorithm backPropagation with configuration: \n Max Error: " + backPropagation.getMaxError() + "\n Learning Rate: " + backPropagation.getLearningRate());
                    multiLayerPerceptron.learn(trainingData);
                    System.out.println("Teaching is finished:");
        // Wyswietlenie liczby iteracji oraz całkowitego błędu uczenia w sieci neuronowej
                    System.out.println(" Number of iterations: " + backPropagation.getCurrentIteration());
                    System.out.println(" Total error: " + backPropagation.getErrorFunction().getTotalError());
        //zapis sieci
                    multiLayerPerceptron.save("my.nnet");
        //testowanie
                    NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile("my.nnet");
                    int counter = 1;
                    for (DataSetRow dataSetRow : trainingData.getRows()) {
                        double[] input = dataSetRow.getInput();
                        System.out.println("Pair " + counter + ": x1=" + ((input[0] - b) / a) + " x2=" + ((input[1] - b) / a));

                        double[] desiredOutput = dataSetRow.getDesiredOutput();
                        neuralNetwork.setInput(dataSetRow.getInput());
                        neuralNetwork.calculate();
                        double[] output = neuralNetwork.getOutput();
                        System.out.println("Desired output: " + ((desiredOutput[0] - b2) / a2));
                        System.out.println("Output: " + ((output[0] - b2) / a2));
                        System.out.println();
                        counter++;

                    }

    }
}
