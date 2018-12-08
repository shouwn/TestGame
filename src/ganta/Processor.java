package ganta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Processor extends Thread{
    private Queue<Runnable> runnableQueue = new ConcurrentLinkedQueue<>();

    public Processor(){
        super();
        this.setDaemon(true);
    }

    public void add(Runnable runnable){
        runnableQueue.add(runnable);

        synchronized (this){
            this.notify();
        }
    }

    @Override
    public void run(){
        while (true){
            try {
                while(!runnableQueue.isEmpty())
                    runnableQueue.poll().run();

                synchronized (this){
                    this.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
