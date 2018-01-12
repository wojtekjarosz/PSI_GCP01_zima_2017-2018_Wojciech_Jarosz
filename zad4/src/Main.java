import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        int size = 64;
        double lRate = 0.01 ; // wspólczynnik uczenia
        double fRate = 0.001; // wpsolczynnik zapominania

        HebbianNework hebbianNework = new HebbianNework(lRate  ,fRate,size);
        //wywolanie metody odpowiedzialnej za wylosownie wag
        hebbianNework.randWeights();
        //wczytanie danych uczacych z pliku
        hebbianNework.loadData();
        //metoda wypisujaca dane uczace
        hebbianNework.printLearningData();
        //uczeine sieci ze wspolczynnikiem zapominania
        hebbianNework.learnWithForgetRate();
        //uczenie sieci bez wpsolczynnika zapominania
        //hebbianNework.learnWithoutForgetRate();
        //metoda wypisujaa aktualne wagi
        hebbianNework.printWeights();
        //hebbianNework.test();
    }
}
