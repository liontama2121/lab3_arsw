package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback = null;

    private int health;

    private boolean pause;

    private int defaultDamageValue;

    private boolean dead;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue = defaultDamageValue;
        this.pause = false;
        this.dead=false;
    }


    public void run() {

        while (!dead && immortalsPopulation.size()>1) {
            while (pause) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Immortal im;

            int myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }

            im = immortalsPopulation.get(nextFighterIndex);

            this.fight(im);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void fight(Immortal i2) {
        Immortal immortal1;
        Immortal immortal2;
        if (this.getId() != i2.getId()) {
            immortal1 = this.getId() < i2.getId() ? this : i2;//funcion lamda
            immortal2 = this.getId() > i2.getId() ? this : i2;//funcion lamda
        } else {
            immortal1 = this;
            immortal2 = i2;
        }
        if (this.getHealth() <= 0) {
            this.dead=true;
            immortalsPopulation.remove(this);


        } else if (i2.getHealth() > 0) {
            synchronized (immortal1) {
                synchronized (immortal2) {
                    // me encargo que pongo en en espera immortal 1 y despues a inmortal 2
                    i2.changeHealth(i2.getHealth() - defaultDamageValue);
                    this.health += defaultDamageValue;
                    updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");

                }
            }

        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }

    }

    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
