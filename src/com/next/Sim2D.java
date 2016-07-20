package com.next;


import com.next.model.Thing;
import com.next.model.World;
import com.next.ui.FileOps;
import com.next.ui.Jpegger;
import com.next.ui.WorldFrame;
import com.next.ui.WorldPanel;

import java.io.File;
import java.text.DecimalFormat;

public class Sim2D {

    public static WorldFrame theFrame;
    // the pixel world dimensions
    public static int width = 700;
    public static int height = 700;

    // the virtual world dimensions
    public static final double xDimension = 100;        // x dimension in unspecified units
    public static final double yDimension = 100;        // y dimension

    public static double deltaT = 0.001;            // time-step
    public static double simulationTime = 0;        // keeps track of current time
    public static double runTime = 100.0;            // final time to run to
    public static int counter = 0;                    // keeps track of current integration step number

    // for file writing
    public static int remoteCounter = 0;                // for counting integration steps between remote reports
    public static int remoteReportStep = 100;        // number of integration steps between printed info reports
    public static boolean infoWrite = false;            // write info to file flag
    public static int infoWriteCounter = 0;
    public static int infoWriteStep = 100;            // number of integration steps between info reports to file
    public static boolean makeMovie = false;
    public static int movieStep = 100;                // number of integration steps between movies
    public static int movieCounter = 0;                // for counting integration steps between frames
    public static String movieFileName = null;
    public static String moviePath = null;
    public static DecimalFormat fileIdFormat = new DecimalFormat("#000000000.#;#000000000.#");
    public static DecimalFormat timeOutFormat = new DecimalFormat("#0.00#; #0.00#");    // a clean format for simulation time printing


    // for painting control
    public static boolean paintOn = true;

    // for run control
    public static boolean running = false;

    public static void main(String args[]) {
        new Sim2D(args);
    }

    public Sim2D(String[] inputParams) {
        if (inputParams.length != 0) {
            parseParams(inputParams);
        }    // figure out what options have been specified
        // make things to start
        for (int i = 0; i < World.MAX_PREDATOR_COUNT; i++) {
            World.getInstance().makeRandomPredator();
        }


        // make the frame to which we draw
        theFrame = new WorldFrame(this, width, height);
        theFrame.showAll();        // draw once at beginning


        // start the time loop

        try {
            doLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void doLoop() throws InterruptedException {
        while (simulationTime <= runTime) {
            if (running) {
                World.getInstance().update();

                if (paintOn) {
                    theFrame.showAll();
                    Thread.sleep(10);
                } else {
                    theFrame.showInfoOnly();
                }
                if (makeMovie) {
                    checkJPEGWrite();
                    movieCounter++;
                }
                if (infoWrite) {
                    checkInfoWrite();
                    infoWriteCounter++;
                }

                counter++;
                simulationTime += deltaT;
            } else {
                Thread.sleep(200);
            }
        }

    }

    public void checkJPEGWrite() {
        if (movieCounter >= movieStep) {
            theFrame.showAll(); // paint before save
            String fileName = moviePath + File.separator + movieFileName + String.valueOf(fileIdFormat.format(counter) + ".jpg");
            Jpegger.jpegFromBufferedImage(WorldPanel.theImage, fileName);
            movieCounter = 0;
        }
    }

    public static void checkRemoteReport() {
        if (remoteCounter >= remoteReportStep) {
            System.out.println(getInfoString());
            remoteCounter = 0;
        }
    }

    public static void checkInfoWrite() {
        if (infoWriteCounter >= infoWriteStep) {
            FileOps.writeInfo();
            infoWriteCounter = 0;
        }
    }

    public static String getInfoString() {
        String timeStr = " Sim Time = " + String.valueOf(timeOutFormat.format(Sim2D.simulationTime));
        String mutCtStr = " Muttons: " + String.valueOf(World.getInstance().getPrayList().size());
        String glutCtStr = " Gluttons: " + String.valueOf(World.getInstance().getPredatorList().size());
        return glutCtStr + " , " + mutCtStr + " , " + timeStr;
    }

    private void parseParams(String[] inputParams) {
        for (int i = 0; i < inputParams.length; i++) {
            if ((inputParams[i].equals("-help")) | (inputParams[i].equals("?"))) {
                System.out.println("The following command line arguments are accepted.....");
                System.out.println("	-help	 prints this help message");
                System.out.println("	-r remote start (no local GUI) with default initial conditions");
                System.out.println("-rs [int] remote reporting step interval");
                System.out.println("	-dt [double] use this value for the fixed time-step");
                System.out.println("	-if [filename]	write info to the named file");
                System.out.println("	-is [int] integration steps between info writing");
                System.exit(0);
            }
            if (inputParams[i].equals("-r")) {
                running = true;
            }

            if (inputParams[i].equals("-rs")) {
                Double rStep = Double.valueOf(inputParams[i + 1]);
                remoteReportStep = (int) rStep.doubleValue();
            }

            if (inputParams[i].equals("-dt")) {
                Double dt = Double.valueOf(inputParams[i + 1]);
                deltaT = dt.doubleValue();
                Thing.deltaT = deltaT;
            }

            if (inputParams[i].equals("-if")) {
                File infodir = new File(inputParams[i + 1]);
                File altdir = new File(inputParams[i + 1]);
                int j = 1;
                while (altdir.isDirectory()) {
                    System.out.println(altdir.getName() + " exists.... keeping file name BUT changing directory name");
                    altdir = new File(inputParams[i + 1] + "." + String.valueOf(j));
                    j++;
                }
                altdir.mkdir();    // make directory for files
                String nameToUse = infodir.getName();
                FileOps.mkInfoFile(altdir.getAbsolutePath() + File.separator + nameToUse);
                //*******
                infoWrite = true;
            }

            if (inputParams[i].equals("-is")) {
                Double iStep = Double.valueOf(inputParams[i + 1]);
                infoWriteStep = (int) iStep.doubleValue();
            }

        }
    }

}

		
		
		
		
		
		
		
		
		
		
