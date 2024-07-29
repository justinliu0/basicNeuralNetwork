public class Neuron {
    private double[] weights;
    private double bias;

    public Neuron(double[] weights, double bias) {
        this.weights = weights;
        this.bias = bias;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public double feedForward(double[] inputs) {
        double total = 0;
        for (int i = 0; i < weights.length; i++) {
            total += weights[i] * inputs[i];
        }
        total += bias;
        return sigmoid(total);
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public void updateWeights(int index, double delta) {
        weights[index] -= delta;
    }

    public void updateBias(double delta) {
        bias -= delta;
    }
}
