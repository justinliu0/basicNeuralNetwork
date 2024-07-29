import java.util.*;

public class Main {
    public static void main(String[] args) {

        Person alice = new Person(133, 65, 1);
        Person bob = new Person(160, 72, 0);
        Person charlie = new Person(152, 70, 0);
        Person diana = new Person(120, 60, 1);

        int shiftedHeight = Person.shiftHeight();
        int shiftedWeight = Person.shiftWeight();

        ArrayList<double[]> data = new ArrayList<>();
        data.add(new double[]{alice.getWeight() - shiftedWeight, alice.getHeight() - shiftedHeight});
        data.add(new double[]{bob.getWeight() - shiftedWeight, bob.getHeight() - shiftedHeight});
        data.add(new double[]{charlie.getWeight() - shiftedWeight, charlie.getHeight() - shiftedHeight});
        data.add(new double[]{diana.getWeight() - shiftedWeight, diana.getHeight() - shiftedHeight});

        double[] allYTrues = new double[] {1, 0, 0, 1};

        double[] weights = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
        double bias = 0.1;
        NeuralNetwork network = new NeuralNetwork(weights, bias);

        network.train(data.toArray(new double[0][]), allYTrues, 1000, 0.1);

        double[][] testInputs = {
                {128 - shiftedWeight, 63 - shiftedHeight},  // Emily
                {155 - shiftedWeight, 68 - shiftedHeight}, // Frank
                {0, 0} // Alex

        };

        System.out.println("Emily " + network.feedForwardOutput(testInputs[0]));
        System.out.println("Frank " + network.feedForwardOutput(testInputs[1]));
        System.out.println("Alex " + network.feedForwardOutput(testInputs[2]));
    }
}
