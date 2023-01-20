package org.roxyfy.wait;

public class WaitCondition {

    boolean content = false;

    // Thread which is waiting for content
    public synchronized void waitForContent() {//lock on this.
        while (!content) {
            try {
                wait();//unlock this. Thread is waiting for event of content = true
                // wait is "spurious wakeup" that means thread could wake up with no reasons.
                // That's why we put it in loop
            } catch (InterruptedException e) {
                System.out.println("Interruption");
            }

            System.out.println("Content has been arrived.");
        }
    }

    // Thread which send the content
    public synchronized void deliverContent() {
        content = true;
        notifyAll();//wake up all threads which is waiting this resource - content
    }
}
