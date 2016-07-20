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

    public List<Predator> getNewGeneration(List<com.next.model.Predator> currentGeneration) {
        //sort current generation by fitness
        List<Predator> currentGenCoppy = new ArrayList<Predator>();
        currentGenCoppy.addAll(currentGeneration);
        Collections.sort(currentGenCoppy);

        List<Predator> fittestPredators = new ArrayList<Predator>();
        //replicate only the most fit ones
        for (int i = currentGenCoppy.size() / 3; i < currentGenCoppy.size(); i++) {
            fittestPredators.add(currentGenCoppy.get(i));
        }

        List<Predator> newPredators = new ArrayList<Predator>();
        boolean mutatingHeavy = false;
        if (fittestPredators.get(fittestPredators.size()-1).getBrain().getFitness()==0){
            mutatingHeavy=true;
            System.out.println("Mutate heavy");
        }

        for (int i = 0; i < World.MAX_PREDATOR_COUNT; i++) {
            int parent1Index = 0 + (int) (Math.random() * fittestPredators.size());
            int parent2Index = 0 + (int) (Math.random() * fittestPredators.size());
            newPredators.add(crossOver(fittestPredators.get(parent1Index), fittestPredators.get(parent2Index),mutatingHeavy));
        }
        return newPredators;

    }

    private Predator crossOver(Predator parent1, Predator parent2, boolean mutateHeavy) {
        int crossPoint = 0 + (int) (Math.random() * parent1.getBrain().getNetwork().getWeights().size());

        List<Double> newWeights = new ArrayList<Double>();
        for (int i = 0; i < crossPoint; i++) {
            newWeights.add(parent1.getBrain().getNetwork().getWeights().get(i));
        }
        for (int i = crossPoint; i < parent1.getBrain().getNetwork().getWeights().size(); i++) {
            newWeights.add(parent2.getBrain().getNetwork().getWeights().get(i));
        }

        //random mutation
        if (mutateHeavy) {
            newWeights.clear();
            for (int i =0;i < parent1.getBrain().getNetwork().getWeights().size(); i++) {
                newWeights.add(Math.random());
            }
            //for (int i=0;i<parent1.getBrain().getNetwork().getWeights().size()/2;i++) {
            //    int randomMutationIndex = 0 + (int) (Math.random() * newWeights.size());
            //    Collections.replaceAll(newWeights, newWeights.get(randomMutationIndex), Math.random());
            //}

        }

        double initX = Predator.maxX/2;//Math.random() * Predator.maxX;
        double initY = Predator.maxY/2;//Math.random() * Predator.maxY;
        Predator offspring = new Predator(initX, initY);
        offspring.getBrain().getNetwork().setWeights(newWeights);
        return offspring;
    }
}
