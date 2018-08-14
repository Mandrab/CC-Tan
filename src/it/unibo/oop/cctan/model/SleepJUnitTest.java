package it.unibo.oop.cctan.model;

import org.junit.Test;

import com.sun.corba.se.spi.orb.Operation;

public class SleepJUnitTest {

    @Test
    public void testSleep() {
        PausableThread pThread = new PausableThread(10) {

            @Override
            protected void operation() {
                System.out.println("sleep ended");
            }
        };
        pThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pausaaa");
        pThread.pause();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("riprendii");
        pThread.resumeRun();
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public abstract class PausableThread extends Thread {
        
        private boolean pause;
        private final int amount;
        
        public PausableThread(int millis) {
            this.pause = false;
            this.amount = millis;
        }
        @Override
        public void run() {
            long remaining = this.amount;
            long time;
            synchronized (this) {
                while (remaining > 0) {
                    try {
                        time = System.currentTimeMillis();
                        wait(remaining);
                        remaining = remaining - (System.currentTimeMillis() - time);
                        if (pause) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.operation();
        }
        
        protected abstract void operation();
        
        public synchronized void pause() {
            if (!this.pause) {
                this.pause = true;
                notifyAll();
            }
        }
        
        public synchronized void resumeRun() {
            if(this.pause) {
                this.pause = false;
                notifyAll();
            }
        }
    }
}
