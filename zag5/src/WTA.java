import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.LearningRule;
import java.util.Iterator;
/**
 * Created by Wojtek on 03.01.2018.
 */
public class WTA extends LearningRule {


    private static final long serialVersionUID = 1L;

    private double learningRate = 0.5; //współćzynnik czenia
    private int iteration = 10000; //ilosc iteracji
    public WTA(){
        super();
    }

    // funkcja ucząca siec
    @Override
    public void learn(DataSet trainingSet) {

        for(int i = 0; i < iteration; i++) {
            Iterator<DataSetRow> iterator = trainingSet.iterator();
            while (iterator.hasNext() && !isStopped()) {
                DataSetRow dataSetRow = normalize(iterator.next());
                learnPattern(dataSetRow);
            }
            learningRate=learningRate*Math.exp(-(double)i/iteration);
        }
    }

    //funkcja normalizyjąca dane wejściowe
    private DataSetRow normalize(DataSetRow dataSetRow){
        double[] input = dataSetRow.getInput();
        double l = Math.sqrt(Math.pow(input[0], 2)+Math.pow(input[1], 2)+Math.pow(input[2], 2)+Math.pow(input[3], 2));
        input[0] = input[0]/l;
        input[1] = input[1]/l;
        input[2] = input[2]/l;
        input[3] = input[3]/l;
        DataSetRow dataSetRow1 = new DataSetRow();
        dataSetRow1.setInput(input);
        return dataSetRow1;
    }
    // węzeł oblicza swój poziom aktywacji jako iloczyn skalarny wektora wag i wektora wejściowego (podobnie jak w zwykłym neuronie)
	//Ten węzeł, który dla danego wektora wejściowego ma najwyższy poziom aktywacji, zostaje zwycięzcą i jest uaktywniony

    private void learnPattern(DataSetRow dataSetRow){
        neuralNetwork.setInput(dataSetRow.getInput());
        neuralNetwork.calculate();
        Neuron winner = getClosestNeuron();
        if(winner.getOutput() == 0)
            return;
        Layer map = neuralNetwork.getLayerAt(1);
        int indexOfWinner = map.indexOf(winner);
        for(int i = 0; i < map.getNeuronsCount(); i++){
            if(i == indexOfWinner) continue;
            map.getNeurons()[i].setOutput(0);
        }
        for(int i = 0; i < map.getNeuronsCount(); i++){
            if(map.getNeurons()[i].getOutput() == 0) continue;
            changeWeights(map.getNeurons()[i]);
        }
    }

    //uaktualnienie wag
    private void changeWeights(Neuron neuron){
        for(Connection conn : neuron.getInputConnections()) {
            double dWeight = learningRate*(conn.getInput() - conn.getWeight().getValue());
            conn.getWeight().inc(dWeight);
        }
    }

    private Neuron getClosestNeuron() {
        Neuron winner = new Neuron();
        double max = 0;
        for(Neuron n: this.neuralNetwork.getLayerAt(1).getNeurons()){
            if(n.getOutput() > max){
                max = n.getOutput();
                winner = n;
            }
        }
        return winner;
    }

    public void setIteration(int iteration){
        this.iteration = iteration;
    }
    public int getIteration(){
        return iteration;
    }
    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

}