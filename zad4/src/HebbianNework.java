import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Wojtek on 06.12.2017.
 */
public class HebbianNework {

    double lRate;
    double fRate;
    int size;
    int epoch;
    double e;
    double[][] learningData; //dane uczace
    double[][] testingData; //dane testujace
    double[][] weights;//wagi
    double[] a; //sygnal wyjsciowy
    public HebbianNework(double lRate, double fRate, int size) {
        this.lRate = lRate;
        this.fRate = fRate;
        this.size = size;
        learningData = new double[size][4];
        testingData = new double[size][2];
        weights = new double[size][4];
        epoch = 0;
        e = 0.0;
        a = new double[size];
        for(int i=0;i<size;i++){
            a[i] = 1.;
        }
    }
//metoda generujaca wagi
    void randWeights(){
        for(int i=0; i< size;i++){
            for (int j=0;j<4;j++){
                weights[i][j] = Math.random();
            }
        }
    }
//wczytanie danych uczących z pliku
    void loadData(){
        double n=Math.sqrt(size);
        File file = new File("learning_data.txt");
        Scanner in = null;
        String[] result;
        try {
            in = new Scanner(file);
            for(int i=0;i<4;i++){
                //while (in.hasNextLine()){
                String line = in.nextLine();
                result = line.split("\\s");
                for(int j=0; j< 64; j++){
                    learningData[j][i]=Double.parseDouble(result[j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
//wypisanie dannych uczacych
    void printLearningData(){
        System.out.println("Learning Data:");
        for(int i=0;i<4;i++){
            for(int j=0; j< 64; j++){
                System.out.print(learningData[j][i]+" ");
            }
            System.out.println("");
        }
    }
//metoda odpowiedzialna za uczenie sieci ze współczynnikiem zapominania
    void learnWithForgetRate() {
        double tmp = 0.0;
        double error = 0.0;
        do {
            for (int i = 0; i < 4; ++i) {
                e = 0.0;
                for (int j = 0; j < size; ++j) {
                    tmp = a[j];
                    //sygnal wyjsciowy
                    a[j] = (weights[j][i] * learningData[j][i]);
                    //nowe wagi = stare wagi * wsp zapominania * sygnal wyjsciowy * wsp uczenia
                    weights[j][i] = weights[j][i] * fRate + lRate * a[j] * learningData[j][i];
                    //bledy
                    if (error == Math.abs(tmp - a[j])) break;
                    error = Math.abs(tmp - a[j]);
                    e = e + Math.pow(error, 2);
                }
                //bledy MSE i MAPE
                double MSE = Math.pow(e,2)/(size);
                double MAPE = (e*100/size);
                System.out.println(" MSE: " + MSE + " MAPE: " + MAPE + "%");

            }
            epoch++;
            }
            while (e != 0 && epoch < 5000) ;
            System.out.println("Learning finished!!!\nEpochs: " + epoch);


    }
// metoda odpowedzialna za uczenie sieci bez wpółczynnika zapominania
    void learnWithoutForgetRate(){
        double tmp = 0.0;
        double error = 0.0;
        do{
            for(int i=0;i<4;++i){
                e = 0.0;
                for(int j=0;j<size;++j){
                    tmp = a[j];
                    //sygnal wyjsciowy
                    a[j] = weights[j][i]*learningData[j][i];
                    //nowe wagi = stare wagi * wsp zapominania * sygnal wyjsciowy * wsp uczenia
                    weights[j][i] = weights[j][i]+lRate*a[j]*learningData[j][i];
                    //bledy
                    if(error==Math.abs(tmp-a[j])) break;
                        error = Math.abs(tmp - a[j]);
                        e = e + Math.pow(error, 2);

                }
                //bledy MSE i MAPE
                double MSE = Math.pow(e,2)/(size);
                double MAPE = (e*100/size);
                System.out.println(" MSE: " + MSE + " MAPE: " + MAPE + "%");
            }

            epoch++;
        }while(e!=0 && epoch<5000);
        System.out.println("Learning finished!!!\nEpochs: "+epoch);


    }

    void printWeights(){
        for(int i =0; i<4;i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(weights[j][i] + " ");
            }
            System.out.println("");
        }
    }

}
