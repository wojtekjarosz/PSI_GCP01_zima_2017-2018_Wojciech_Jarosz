package sample;

/**
 * Created by Wojtek on 18.10.2017.
 */
public class Perceptron {
    public static final int[][] andTruthTable = {{0,0,0},{0,1,0},{1,0,0},{1,1,1}};
    public static final int[][] orTruthTable = {{0,0,0},{0,1,1},{1,0,1},{1,1,1}};
    public double calculateSum(int[] truthTable, double[] weights){
        double sum = 0;
        for(int x=0; x < truthTable.length; x++)
            sum += truthTable[x] * weights[x];
        return sum;
    }
    public int activeationFunction(double sum){
        int result;
        if(sum > 1) result = 1;
        else result=0;
        return result;
    }
    public double[] calculateWeights(int[] truthTable, double learning_rate, double[] weights, double error){
        double[] newWeights = new double[weights.length];
        for(int x=0; x < weights.length;x++)
            newWeights[x] = learning_rate * error * truthTable[x] + weights[x];
        return newWeights;
    }
}
