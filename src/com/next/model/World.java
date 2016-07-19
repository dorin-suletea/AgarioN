package com.next.model;
/*************************************************************************
 * Copyright (c) Metabiota Incorporated - All Rights Reserved
 * ------------------------------------------------------------------------
 * This material is proprietary to Metabiota Incorporated. The
 * intellectual and technical concepts contained herein are proprietary
 * to Metabiota Incorporated. Reproduction or distribution of this
 * material, in whole or in part, is strictly forbidden unless prior
 * written permission is obtained from Metabiota Incorporated.
 *************************************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dorinsuletea on 7/15/16.
 */
public class World {
    public static final int MAX_PRAY_COUNT = 50;
    private static final World instance = new World();
    private List<Predator> predators;
    private List<Pray> pray;


    private World() {
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
        double initX = Math.random() * Pray.maxX;
        double initY = Math.random() * Pray.maxY;
        pray.add(new Pray(initX, initY));
    }

    public void trySpawnPray() {
        if (Math.random() < Pray.spawnProb && pray.size()<MAX_PRAY_COUNT) {
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
}
