package com.next.ai;

/**
 * Created by dorinsuletea on 7/20/16.
 */
public class Brain {
    private int fitness;
    private NeuronNetwork neuralNet;

    public Brain() {
        fitness = 0;
        try {
            neuralNet = new NeuronNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void incrementFitness(int incrementAmmount) {
        fitness += incrementAmmount;
    }

    public int getFitness() {
        return fitness;
    }

    public NeuronNetwork getNetwork(){
        return this.neuralNet;
    }
}
