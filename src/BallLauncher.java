import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BallLauncher extends GraphicsProgram implements ActionListener {
    public static final int PROGRAM_HEIGHT = 600;
    public static final int PROGRAM_WIDTH  = 800;
    public static final int SIZE = 25;

    // Timer + movement
    private static final int MS = 50;   // wake interval
    private static final int SPEED = 2; // pixels per tick

    private ArrayList<GOval> balls;
    private Timer movement;

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
        requestFocus();
    }

    public void run() {
        balls = new ArrayList<>();
        addMouseListeners();

        // Animate all balls with a Swing Timer (no pause())
        movement = new Timer(MS, this);
        movement.start();
    }

    public void mousePressed(MouseEvent e) {
        // Cool down: if any ball is still near the left edge, do nothing
        for (GOval b : balls) {
            if (b.getX() < 100) return;
        }

        GOval ball = makeBall(SIZE / 2.0, e.getY());
        add(ball);
        balls.add(ball);
    }

    public GOval makeBall(double x, double y) {
        GOval temp = new GOval(x - SIZE / 2.0, y - SIZE / 2.0, SIZE, SIZE);
        temp.setColor(Color.RED);
        temp.setFilled(true);
        return temp;
    }

    // Move every ball a bit each tick
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).move(SPEED, 0);
        }
    }

    public static void main(String[] args) {
        new BallLauncher().start();
    }
}
