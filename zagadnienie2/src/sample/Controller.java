package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    double weight1, weight2;
    double weights[] = new double [35];
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
        //tablica z literami - ciągiem 01 - dane uczące
        int[][] letters = Adaline.letters;
        //tablica z danymi oczekiwanymi
        int[] result_tab = Adaline.result_tab;
        //losowanie wag początkowych z zakresu 0-1
        for(int i=0;i<35;i++){ weights[i] = Math.random();}
        Adaline adaline = new Adaline();
        double learning_rate = 0.1;
        //pobranie współczynnika uczenia od użytkownika
        try {
            learning_rate = Double.parseDouble(learningRateTextField.getText());
        } catch (NumberFormatException e) {
            learningRateTextField.setText(String.valueOf(0.1));
            learning_rate=0.1;}
        int itrNumber = 0; //licznik iteracji
        boolean isErrorOK = false; //flaga akceptowanego błędu
        double error = 0; //błąd
        double delta;  // roznica wartości oczekiwanej i otrzymanej sumy
        double[] newWeights;  //nowe wagi
        double[] sum = new double[20];  //tablica przechowująca sumę dla każdej litery
        int j=0;
        while (!isErrorOK){
            itrNumber++;
            System.out.println("\nItr: "+itrNumber);
            isErrorOK = false; error =0;
            //pętla, wktóej dla każdej litery uczącej, obliczana jest suma, następnie delta i nowe wagi
            for (int x=0;x<20;x++){
                int[] tmp = letters[x];
                sum[x] = adaline.calculateSum(tmp ,weights);
                delta = result_tab[x] - sum[x];
                newWeights = adaline.calculateWeights(tmp, learning_rate,weights,delta);
                weights =newWeights;
            }
            //obliczenie błędu
            error=0.5*(result_tab[0] - sum[0])*(result_tab[0] - sum[0]);
            System.out.println("Err: "+error);
            //sprawdzenie czy uzyskany błąd mieści się w założonym progu
            if(error>0.001) isErrorOK = false;
            else isErrorOK=true;
        }
        iterationsLabel.setText(String.valueOf(itrNumber));

    }

    @FXML
    public void startTest() {
        //sprawdzenie czy dla danych uczących, podany zostanie poprawny wynik po zakończeniu nauki
        Adaline adaline = new Adaline();
        double[] sum = new double[20];
        int[] tmp;
        for (int x=0;x<20;x++){
            tmp = adaline.letters[x];
            //obliczenie sumy
            sum[x] = adaline.calculateSum(tmp ,weights);
            System.out.print("Litera "+ adaline.char_tab[x]+": ");
            //uzyskanie wyniku poprzez użycie funkcji aktywacji dla obliczonej sumy
            if(adaline.activeationFunction(sum[x])==1) {
                System.out.println("duża");
            }else{
                System.out.println("mała");
            }
        }
        //sprawdzenie poprawności dla liter, które nie zostały użyte jako dane testowe
        System.out.println("DANE TESTOWE");
        for (int x = 0; x< adaline.char_tab_test.length; x++){
            tmp = adaline.test_letters[x];
            sum[x] = adaline.calculateSum(tmp ,weights);
            System.out.print("Litera "+ adaline.char_tab_test[x]+": ");
            if(adaline.activeationFunction(sum[x])==1) {
                System.out.println("duża");
            }else{
                System.out.println("mała");
            }
        }

    }
}
