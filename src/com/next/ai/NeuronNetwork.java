package com.next.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dorinsuletea on 7/18/16.
 */

class NeuronNetwork {
    public static final int BIAS = -1;
    public static final int ACTIVATION_RESPONSE = 1;
    public static final int INPUT_COUNT = 4;
    public static final int OUTPUT_COUNT = 2;
    public static final int HIDDEN_LAYER_COUNT = 2;
    public static final int NEURONS_PER_HIDDEN_LAYER = 8;

    private List<NeuronLayer> layers;


    NeuronNetwork() throws Exception {
        //each neuron from layer 0 is linked to each neuron from layer 1 .. etc
        layers = new ArrayList<NeuronLayer>();
        if (HIDDEN_LAYER_COUNT <= 0) {
            throw new Exception("Must have hidden layer");
        }
        //create first hidden layer
        layers.add(new NeuronLayer(NEURONS_PER_HIDDEN_LAYER, INPUT_COUNT));
        // -1 since we already added one hidden layer
        for (int i = 0; i < HIDDEN_LAYER_COUNT - 1; ++i) {
            //hidden layers have the same input neuron count as the neurons
            layers.add(new NeuronLayer(NEURONS_PER_HIDDEN_LAYER, NEURONS_PER_HIDDEN_LAYER));
        }
        // make output layer
        layers.add(new NeuronLayer(OUTPUT_COUNT, NEURONS_PER_HIDDEN_LAYER));
    }

    void setWeights(List<Double> weights) {
        int weightIndex = 0;
        for (NeuronLayer layer : this.layers) {
            for (Neuron n : layer.neurons) {
                List<Double> newWeights = new ArrayList<Double>();
                for (int i = 0; i < n.inputWeights.size(); i++) {
                    newWeights.add(weights.get(weightIndex));
                    weightIndex++;
                }
                n.inputWeights.clear();
                n.inputWeights.addAll(newWeights);
            }
        }
    }

    List<Double> computeDirection(List<Double> inputs) {
        List<Double> outputs = new ArrayList<Double>();
        int cWeight = 0;

        for (int i=0; i<HIDDEN_LAYER_COUNT + 1; ++i) {
            if (i > 0) {
                inputs.clear();
                inputs.addAll(outputs);
            }

            outputs.clear();
            cWeight = 0;

            for (int j = 0; j < layers.get(i).neurons.size(); ++j) {
                double netinput = 0;
                int	NumInputs = layers.get(i).neurons.get(j).inputWeights.size();

                //for each weight
                for (int k=0; k<NumInputs - 1; ++k){
                    //sum the weights x inputs
                    netinput += layers.get(i).neurons.get(j).inputWeights.get(k) * inputs.get(cWeight++);
                }
                netinput += layers.get(i).neurons.get(j).inputWeights.get(NumInputs-1) *  BIAS;
                outputs.add(sigmoid(netinput,ACTIVATION_RESPONSE));
                cWeight = 0;
            }

        }


        /*
        for (int j = 0; j < this.layers.size(); j++) {
            NeuronLayer layer = this.layers.get(j);
            //makes this recursive
            if (j > 0) {
                inputs.clear();
                inputs.addAll(outputs);
            }

            outputs.clear();
            cWeight = 0;


            for (Neuron neuron : layer.neurons) {
                double netInput = 0;
                for (int i = 0; i < neuron.inputWeights.size() - 1; i++) {
                    //sum the weights * inputs
                    netInput += inputs.get(cWeight++) * neuron.inputWeights.get(i);
                }

                // add in the bias
                netInput += neuron.inputWeights.get(neuron.inputWeights.size() - 1) * BIAS;
                outputs.add(sigmoid(netInput, ACTIVATION_RESPONSE));
            }
        }
        */
        return outputs;
    }


    private double sigmoid(double netInput, double response) {
        return (1 / (1 + Math.exp(-netInput / response)));
    }

    public List<Double> getWeights() {
        List<Double> ret = new ArrayList<Double>();
        for (NeuronLayer layer : this.layers) {
            for (Neuron n : layer.neurons) {
                ret.addAll(n.inputWeights);
            }
        }
        return ret;
    }


    /**
     * Sub-Object of ANN
     **/
    class Neuron {
        private List<Double> inputWeights;

        public Neuron(int inputCount) {
            inputWeights = new ArrayList<Double>();
            //init with random weights
            for (int i = 0; i < inputCount; i++) {
                inputWeights.add(Math.random());
            }
        }
    }

    class NeuronLayer {
        private List<Neuron> neurons;

        public NeuronLayer(int thisLayerNeuronCount, int inputPerNeuronCount) {
            neurons = new ArrayList<Neuron>();
            for (int i = 0; i < thisLayerNeuronCount; i++) {
                neurons.add(new Neuron(inputPerNeuronCount));
            }

        }
    }
}

