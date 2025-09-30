import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
    private ArrayList<GOval> balls;
    private ArrayList<GRect> enemies;
    private GLabel text;
    private Timer movement;
    private RandomGenerator rgen;

    public static final int SIZE = 25;
    public static final int SPEED = 2;
    public static final int MS = 50;
    public static final int MAX_ENEMIES = 10;
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH  = 300;

    private int numTimes = 0; // ticks since start

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        requestFocus();
    }

    public void run() {
        rgen = RandomGenerator.getInstance();
        balls = new ArrayList<>();
        enemies = new ArrayList<>();

        text = new GLabel("" + enemies.size(), 10, 20);
        add(text);

        movement = new Timer(MS, this);
        movement.start();
        addMouseListeners();
    }

    public void actionPerformed(ActionEvent e) {
        numTimes++;

        // Periodically add enemies
        if (numTimes % 40 == 0) {
            addAnEnemy();
        }

        moveAllBallsOnce();
        moveAllEnemiesOnce();
        detectCollisions();

        // Lose condition
        if (enemies.size() > MAX_ENEMIES) {
            movement.stop();
            removeAll();
            GLabel lose = new GLabel("You lost! Too many enemies.", 20, WINDOW_HEIGHT / 2.0);
            lose.setColor(Color.RED);
            add(lose);
        }
    }

    public void mousePressed(MouseEvent e) {
        // cool down: if any ball is still near the left side, skip
        for (GOval b : balls) {
            if (b.getX() < SIZE * 2.5) return;
        }
        addABall(e.getY());
    }

    private void addABall(double y) {
        GOval ball = makeBall(SIZE / 2.0, y);
        add(ball);
        balls.add(ball);
    }

    public GOval makeBall(double x, double y) {
        GOval temp = new GOval(x - SIZE / 2.0, y - SIZE / 2.0, SIZE, SIZE);
        temp.setColor(Color.RED);
        temp.setFilled(true);
        return temp;
    }

    private void addAnEnemy() {
        double y = rgen.nextDouble(SIZE / 2.0, WINDOW_HEIGHT - SIZE / 2.0);
        GRect e = makeEnemy(y);
        enemies.add(e);
        text.setLabel("" + enemies.size());
        add(e);
    }

    public GRect makeEnemy(double y) {
        GRect temp = new GRect(WINDOW_WIDTH - SIZE, y - SIZE / 2.0, SIZE, SIZE);
        temp.setColor(Color.GREEN);
        temp.setFilled(true);
        return temp;
    }

    private void moveAllBallsOnce() {
        for (GOval ball : balls) {
            ball.move(SPEED, 0);
        }
    }

    // small vertical wiggle
    private void moveAllEnemiesOnce() {
        for (GRect enemy : enemies) {
            enemy.move(0, rgen.nextInt(-2, 2));
        }
    }

    // If a ball is touching an enemy (point ahead of ball), remove that enemy
    private void detectCollisions() {
        ArrayList<GRect> toRemove = new ArrayList<>();

        for (GOval ball : balls) {
            double px = ball.getX() + ball.getWidth() + 1;
            double py = ball.getY() + ball.getHeight() / 2.0;
            GObject hit = getElementAt(px, py);
            if (hit instanceof GRect) {
                toRemove.add((GRect) hit);
            }
        }

        for (GRect g : toRemove) {
            remove(g);
            enemies.remove(g);
            text.setLabel("" + enemies.size());
        }
    }

    public static void main(String args[]) {
        new DodgeBall().start();
    }
}
