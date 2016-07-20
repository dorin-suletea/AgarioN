package com.next.model;/*
    // Thing.... the superclass for all moving objects in this demonstration
*/

import com.next.Sim2D;

import java.awt.*;

public abstract class Thing {
    /*Properties*/
    protected int stepSize = 1;
    protected double radius = 1;
    protected Color drawColor;
    protected boolean isStatic = false;

    /*Meta*/
    protected double xPos;
    protected double yPos;
    private boolean isDead = false;

    public static double maxX = Sim2D.xDimension;    // maximum x position this Thing can occupy
    public static double maxY = Sim2D.yDimension;    // maximum y position this Thing can occupy
    public static double deltaT = Sim2D.deltaT;




    public Thing(double initX, double initY) {
        xPos = initX;
        yPos = initY;
        isDead = false;
    }

    public abstract void step();


    public void drawThing(Graphics g, double scale, double[] offset) {
        g.setColor(this.drawColor);

        int xCenter = (int) ((xPos - offset[0]) * scale);
        int yCenter = (int) ((yPos - offset[1]) * scale);
        int pixelRadius = (int) (radius * scale);
        int pixelDiameter = (int) (2 * radius * scale);

        g.drawOval(xCenter - pixelRadius, yCenter - pixelRadius, pixelDiameter, pixelDiameter);
    }

    public void setIsDead(boolean val){
        isDead=val;
    }

    public boolean getIsDead(){
        return isDead;
    }

    public double getXpos(){
        return xPos;
    }

    public double getYpos(){
        return yPos;
    }
}

