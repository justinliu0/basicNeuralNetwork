public class NeuralNetwork {
    private Neuron hiddenNeuron1;
    private Neuron hiddenNeuron2;
    private Neuron outputNeuron;

    public NeuralNetwork(double[] weights, double bias) {
        // Initialize neurons with weights and biases
        hiddenNeuron1 = new Neuron(new double[]{weights[0], weights[1]}, bias);
        hiddenNeuron2 = new Neuron(new double[]{weights[2], weights[3]}, bias);
        outputNeuron = new Neuron(new double[]{weights[4], weights[5]}, bias);
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double derivSigmoid(double x) {
        double sigmoidX = sigmoid(x);
        return sigmoidX * (1 - sigmoidX);
    }

    public double feedForwardOutput(double[] inputs) {
        // Calculate hidden layer outputs
        double hiddenOutput1 = hiddenNeuron1.feedForward(inputs);
        double hiddenOutput2 = hiddenNeuron2.feedForward(inputs);
        double[] hiddenLayerOutputs = {hiddenOutput1, hiddenOutput2};

        // Calculate output layer result
        return outputNeuron.feedForward(hiddenLayerOutputs);
    }

    public double meanSquaredErrorLoss(double[] trueValues, double[] predictedValues) {
        double sum = 0;
        for (int i = 0; i < trueValues.length; i++) {
            sum += Math.pow(trueValues[i] - predictedValues[i], 2);
        }
        return sum / trueValues.length;
    }

    public void train(double[][] data, double[] allTrueValues, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < data.length; i++) {
                double[] inputs = data[i];
                double trueValue = allTrueValues[i];

                // Feedforward
                double hiddenOutput1 = hiddenNeuron1.feedForward(inputs);
                double hiddenOutput2 = hiddenNeuron2.feedForward(inputs);
                double[] hiddenLayerOutputs = {hiddenOutput1, hiddenOutput2};
                double predictedValue = outputNeuron.feedForward(hiddenLayerOutputs);

                // Calculate the loss gradient
                double dLoss_dPredictedValue = -2 * (trueValue - predictedValue);

                // Output neuron gradients
                double sumOutput = outputNeuron.getBias(); // Recalculate the sum for the derivative
                for (int j = 0; j < hiddenLayerOutputs.length; j++) {
                    sumOutput += outputNeuron.getWeights()[j] * hiddenLayerOutputs[j];
                }
                double dPredictedValue_dWeight5 = hiddenOutput1 * derivSigmoid(sumOutput);
                double dPredictedValue_dWeight6 = hiddenOutput2 * derivSigmoid(sumOutput);
                double dPredictedValue_dBias3 = derivSigmoid(sumOutput);

                double dPredictedValue_dHiddenOutput1 = outputNeuron.getWeights()[0] * derivSigmoid(sumOutput);
                double dPredictedValue_dHiddenOutput2 = outputNeuron.getWeights()[1] * derivSigmoid(sumOutput);

                // Hidden neurons gradients
                double sumHidden1 = hiddenNeuron1.getBias();
                for (int j = 0; j < inputs.length; j++) {
                    sumHidden1 += hiddenNeuron1.getWeights()[j] * inputs[j];
                }
                double dHiddenOutput1_dWeight1 = inputs[0] * derivSigmoid(sumHidden1);
                double dHiddenOutput1_dWeight2 = inputs[1] * derivSigmoid(sumHidden1);
                double dHiddenOutput1_dBias1 = derivSigmoid(sumHidden1);

                double sumHidden2 = hiddenNeuron2.getBias();
                for (int j = 0; j < inputs.length; j++) {
                    sumHidden2 += hiddenNeuron2.getWeights()[j] * inputs[j];
                }
                double dHiddenOutput2_dWeight3 = inputs[0] * derivSigmoid(sumHidden2);
                double dHiddenOutput2_dWeight4 = inputs[1] * derivSigmoid(sumHidden2);
                double dHiddenOutput2_dBias2 = derivSigmoid(sumHidden2);

                // Update weights and biases
                // Output neuron
                outputNeuron.updateWeights(0, learningRate * dLoss_dPredictedValue * dPredictedValue_dWeight5);
                outputNeuron.updateWeights(1, learningRate * dLoss_dPredictedValue * dPredictedValue_dWeight6);
                outputNeuron.updateBias(learningRate * dLoss_dPredictedValue * dPredictedValue_dBias3);

                // Hidden neurons
                hiddenNeuron1.updateWeights(0, learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput1 * dHiddenOutput1_dWeight1);
                hiddenNeuron1.updateWeights(1, learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput1 * dHiddenOutput1_dWeight2);
                hiddenNeuron1.updateBias(learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput1 * dHiddenOutput1_dBias1);

                hiddenNeuron2.updateWeights(0, learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput2 * dHiddenOutput2_dWeight3);
                hiddenNeuron2.updateWeights(1, learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput2 * dHiddenOutput2_dWeight4);
                hiddenNeuron2.updateBias(learningRate * dLoss_dPredictedValue * dPredictedValue_dHiddenOutput2 * dHiddenOutput2_dBias2);
            }

            // Calculate loss at the end of each epoch
            if (epoch % 10 == 0) {
                double[] predictedValues = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    predictedValues[i] = feedForwardOutput(data[i]);
                }
                double loss = meanSquaredErrorLoss(allTrueValues, predictedValues);
                System.out.println("Epoch " + epoch + " loss: " + loss);
            }
        }
    }
}
