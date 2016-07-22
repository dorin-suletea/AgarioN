package com.next.ai;

import com.next.model.Predator;
import com.next.model.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dorinsuletea on 7/20/16.
 */
public class GeneticAlgorithm {
    private static int FITTEST_SLICE_PERCENTAGE = 8; //25%

    public List<Predator> getNewGeneration(List<com.next.model.Predator> currentGeneration) {
        //Sort current generation by fitness + Replicate only the most fit ones
        Collections.sort(currentGeneration);
        List<Predator> fittestPredators = new ArrayList<Predator>();
        for (int i = currentGeneration.size() / FITTEST_SLICE_PERCENTAGE; i < currentGeneration.size(); i++) {
            //add one to the list x fitness times
            for (int j=0;j<currentGeneration.get(i).getBrain().getFitness()+1;j++) {
                fittestPredators.add(currentGeneration.get(i));
            }
        }

        System.out.println("Fittest entity : " + (fittestPredators.get(fittestPredators.size() - 1).getBrain().getFitness()));


        List<Predator> newPredators = makeNewGeneration(World.MAX_PREDATOR_COUNT,fittestPredators);
        return newPredators;
    }

    private List<Predator> makeNewGeneration(int generationPopulation, List<Predator> fittestCurrentPredators) {
        List<Predator> ret = new ArrayList<Predator>();
        for (Predator p : fittestCurrentPredators){
            //System.out.println("Combining predators { " + p.getBrain().getFitness() + "]");
        }

        for (int i = 0; i < generationPopulation; i++) {
            int parent1Index = 0 + (int) (Math.random() * fittestCurrentPredators.size());
            int parent2Index = 0 + (int) (Math.random() * fittestCurrentPredators.size());
            Predator parent1 = fittestCurrentPredators.get(parent1Index);
            Predator parent2 = fittestCurrentPredators.get(parent2Index);
            ret.add(breed(parent1, parent2));
        }

        System.out.println("==================New Gen");
        System.out.println(ret.get(0).getBrain().getNetwork().getWeights());
        return ret;
    }

    private Predator breed(Predator parent1, Predator parent2) {
        List<Double> newWeights = new ArrayList<Double>();
        int cross =  (int) (Math.random() * parent1.getBrain().getNetwork().getWeights().size());

        for (int i=0;i<cross;i++){
            newWeights.add(parent1.getBrain().getNetwork().getWeights().get(i));
        }
        for (int i=cross;i<parent2.getBrain().getNetwork().getWeights().size();i++){
            newWeights.add(parent2.getBrain().getNetwork().getWeights().get(i));
        }


        //random mutation
        //mutation chance 20 %
        /*
        if (Math.random()<0.5){
            int nrOfMutations =(int) Math.random() * newWeights.size();
            for (int i=0;i<nrOfMutations;i++){
                int index= (int) Math.random() * newWeights.size();
                newWeights.set(index,Math.random());
            }
        }
        */
        double initX = Math.random() * Predator.maxX;//Predator.maxX / 2;//;
        double initY = Math.random() * Predator.maxY;//Predator.maxY / 2;//Math.random() * Predator.maxY;
        Predator offspring = new Predator(initX, initY);
        offspring.getBrain().getNetwork().setWeights(newWeights);
        return offspring;
    }
}
