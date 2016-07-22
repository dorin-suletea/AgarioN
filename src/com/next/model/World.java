package com.next.model;


import com.next.ai.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dorinsuletea on 7/15/16.
 */
public class World {
    public static final int MAX_PRAY_COUNT = 9;
    public static final int MAX_PREDATOR_COUNT = 5;
    private int TICKS_PER_GENERATION = 300;
    private static final World instance = new World();
    private List<Predator> predators;
    private List<Pray> pray;
    private int tickCount;
    private int generationCount;


    private World() {
        tickCount = 0;
        predators = new ArrayList<Predator>();
        pray = new ArrayList<Pray>();
    }

    public static World getInstance() {
        return instance;
    }

    public void makeRandomPredator() {
        double initX = Math.random() * Predator.maxX;
        double initY = Math.random() * Predator.maxY;
        predators.add(new Predator(initX, initY));
    }

    public void makeRandomPray() {
        double initX =  Math.random() * Pray.maxX;
        double initY = Math.random() * Pray.maxX ;
        pray.add(new Pray(initX, initY));
    }

    public void trySpawnPray() {
        if (pray.size() < MAX_PRAY_COUNT) {
            World.getInstance().makeRandomPray();
        }
    }

    public List<Predator> getPredatorList() {
        return Collections.unmodifiableList(predators);
    }

    public List<Pray> getPrayList() {
        return Collections.unmodifiableList(pray);
    }

    public List<Thing> getAllThings() {
        ArrayList<Thing> all = new ArrayList<Thing>();
        all.addAll(predators);
        all.addAll(pray);
        return all;
    }

    public void removeDead() {
        Iterator<Predator> i = predators.iterator();
        while (i.hasNext()) {
            Predator s = i.next();
            if (s.getIsDead()) {
                i.remove();
            }
        }

        Iterator<Pray> j = pray.iterator();
        while (j.hasNext()) {
            Pray s = j.next();
            if (s.getIsDead()) {
                j.remove();
            }
        }
    }

    public void update() {
        removeDead();
        trySpawnPray();

        tickCount++;
        //current generation is having fun
        if (tickCount < TICKS_PER_GENERATION) {
            for (Thing thing : getAllThings()) {
                thing.step();
            }
        } else {
            System.out.println(generationCount++);
            tickCount = 0;
            //reproduce all predators and die out
            GeneticAlgorithm algo = new GeneticAlgorithm();
            List<Predator> newGeneration = algo.getNewGeneration(predators);
            predators.clear();
            predators.addAll(newGeneration);
        }
    }
}
