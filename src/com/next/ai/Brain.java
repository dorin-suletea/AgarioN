package com.next.ai;

import java.util.List;

/**
 * Created by dorinsuletea on 7/20/16.
 */
public class Brain {
    private int fitness;
    private NeuronNetwork neuralNet;
    private GeneticAlgorithm geneticAlgo;

    public Brain() {
        fitness = 0;
        geneticAlgo = new GeneticAlgorithm();
        try {
            neuralNet = new NeuronNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void incrementFitness(int incrementAmount) {
        fitness += incrementAmount;
    }

    public int getFitness() {
        return fitness;
    }

    public NeuronNetwork getNetwork(){
        return this.neuralNet;
    }

    public List<Double> computeDirection(List<Double> inputs){
        return neuralNet.computeDirection(inputs);
    }
}
