package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Random;

public class Controller {
    double weight1, weight2;
    @FXML
    Label weight1Label;
    @FXML
    Label weight2Label;
    @FXML
    Label correctAnswearsLabel;
    @FXML
    TextField learningRateTextField;
    @FXML
    Label iterationsLabel;

    @FXML
    private void initialize() {learningRateTextField.setText(Double.toString(0.1));}

    @FXML
    public void startButton(){
        //int[][] truthTable = Perceptron.andTruthTable;
        int[][] truthTable = Perceptron.orTruthTable;
        double[] weights = {Math.random(), Math.random()};
        Perceptron perceptron = new Perceptron();
        double learning_rate = 0.1;
        try {
            learning_rate = Double.parseDouble(learningRateTextField.getText());
        } catch (NumberFormatException e) {
            learningRateTextField.setText(String.valueOf(0.1));
            learning_rate=0.1;
        }
        int itrNumber = 0;
       boolean isErrorNotEqualToZero = true;
        double error = 0;
        double[] newWeights;
        while (isErrorNotEqualToZero){
            itrNumber++;
            System.out.println("\nItr: "+itrNumber);
            isErrorNotEqualToZero = false;
            error =0;
            for (int x=0;x<truthTable.length;x++){
                int[] tmp={truthTable[x][0],truthTable[x][1]};
                double sum = perceptron.calculateSum(tmp ,weights);
                int result = perceptron.activeationFunction(sum);
                error = truthTable[x][2] - result;
                if(error !=0) isErrorNotEqualToZero=true;
                newWeights = perceptron.calculateWeights(tmp, learning_rate,weights,error);
                System.out.println(String.format("%.4f",weights[0])+" "+String.format("%.4f",weights[1])+"\t"+truthTable[x][0]+"\t"+truthTable[x][1]+"\t"+truthTable[x][2]+
                "\t"+result+"\t"+error+"\t"+String.format("%.4f",sum)+"\t"+String.format("%.4f",newWeights[0])+"\t"+String.format("%.4f",newWeights[1]));
                weight1Label.setText(String.format("%.4f",newWeights[0]));
                weight1=newWeights[0];
                weight2Label.setText(String.format("%.4f",newWeights[1]));
                weight2=newWeights[1];
                weights =newWeights;
            }

        }
        iterationsLabel.setText(String.valueOf(itrNumber));

    }

    @FXML
    public void startTest() {
        if(weight1Label.getText().isEmpty()){
            correctAnswearsLabel.setText("Calculation first!!!");
        }else{
            int x1, x2;
            int answer=0;
            int N=10000;
            double[] weights ={weight1,weight2};
            Random generator = new Random();
            Perceptron perceptron = new Perceptron();
            for(int i=0; i<N;i++){
                x1=generator.nextInt(2);
                x2=generator.nextInt(2);
                int[] inData={x1,x2};

                double sum = perceptron.calculateSum(inData,weights);
                int result = perceptron.activeationFunction(sum);
                //AND TEST
               /* if(result==1){
                    if(x1==1&&x2==1) answear++;
                }else {
                    if (x1 != 1 || x2 != 1) answear++;
                }*/
                //OR TEST
                if(result==0){
                    if(x1==0&&x2==0) answer++;
                }else {
                    if (x1 != 0 || x2 != 0) answer++;
                }

            }
            correctAnswearsLabel.setText(String.valueOf((answer/N)*100)+"%");
        }
    }
}
