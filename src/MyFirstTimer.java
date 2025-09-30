import acm.graphics.*;
import acm.program.*;
import javax.swing.*;           
import java.awt.event.*;        // ActionListener, ActionEvent

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
    public static final int PROGRAM_HEIGHT = 600;
    public static final int PROGRAM_WIDTH  = 800;
    public static final int MAX_STEPS = 20;
    private GLabel myLabel;
    private int numTimes;        // How many times the timer fires
    private Timer t;             // javax.swing.Timer

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
        requestFocus();
    }

    public void run() {
        myLabel = new GLabel("# of times called? 0", 20, 100);
        add(myLabel);

        numTimes = 0;

        // Step 1: create timer (1000 ms, wake up THIS object)
        t = new Timer(1000, this);

        // extra: 3s initial delay before the first tick
        t.setInitialDelay(3000);

        // Step 3: start the timer
        t.start();
    }

    // Step 2: ActionListener handler — called automatically every wake-up
    public void actionPerformed(ActionEvent e) {
        numTimes++;
        myLabel.move(5, 0);
        myLabel.setLabel("times called? " + numTimes);

        // extra: stop at 10
        if (numTimes >= 10) {
            t.stop();
        }
    }

    public static void main(String[] args) {
        new MyFirstTimer().start();
    }
}
