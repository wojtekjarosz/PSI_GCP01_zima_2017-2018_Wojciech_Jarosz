import org.neuroph.core.data.BufferedDataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Kohonen;

import java.util.ArrayList;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File learningDataFile = new File("learningData.txt");
        File testingDataFile = new File("testingData.txt");
        BufferedDataSet dataSet;
        try {
            dataSet = new BufferedDataSet(learningDataFile, 4,  10,";");
            WTA wta = new WTA();
            Kohonen kohonen = new Kohonen(4, 10);
            kohonen.randomizeWeights();
            kohonen.setLearningRule(wta);
            kohonen.learn(dataSet);

            dataSet = new BufferedDataSet(testingDataFile, 4, 10, ";");
            List<Double> winners = new ArrayList<>();
            for(DataSetRow row: dataSet.getRows()){
                kohonen.setInput(row.getInput());
                kohonen.calculate();
                double[] output = kohonen.getOutput();
                double tmp = 0.0;
                double winner = 0.0;
                for(int i = 0; i < output.length; i++){
                    if(output[i] > tmp){
                        tmp = output[i];
                        winner = i;
                    }
                }
                winners.add(winner);
            }
            for(double d: winners){
                System.out.println(d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
