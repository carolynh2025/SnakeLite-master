import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;
import java.applet.*;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class MainClass extends GraphicsProgram implements ActionListener {

    public GOval ball;

    private ArrayList<GRect> snakeBody;

    private int snakeX, snakeY, snakeWidth, snakeHeight;

    public Timer timer = new Timer(150, this);

    private boolean isPlaying, isGameOver;
    private int score, previousScore;
    private GLabel scoreLabel;
    private GLabel instructions;
    boolean blockKey = false;
    boolean goingUp = false;
    boolean goingDown = false;
    boolean goingLeft = true;
    boolean goingRight = false;


    GRect background = new GRect(450, 300);
    @Override
    public void keyReleased(KeyEvent e) {
        if (blockKey) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    goingUp = true;
                    goingDown = false;
                    goingLeft = false;
                    goingRight = false;
                    break;

                case KeyEvent.VK_DOWN:
                    goingUp = false;
                    goingDown = true;
                    goingLeft = false;
                    goingRight = false;
                    break;

                case KeyEvent.VK_LEFT:
                    goingUp = false;
                    goingDown = false;
                    goingLeft = true;
                    goingRight = false;
                    break;

                case KeyEvent.VK_RIGHT:
                    goingUp = false;
                    goingDown = false;
                    goingLeft = false;
                    goingRight = true;
                    break;
            }
            blockKey = false;

        }
    }

    // inner square colour
    public void background() {

        background.setLocation(100, 100);
        background.setFillColor(Color.white);
        background.setFilled(true);

        add(background);


    }

    public void run() {
        snakeBody = new ArrayList<>();
        createGCanvas();
        addKeyListeners();
        GRect square = new GRect(getGCanvas().getWidth(), getGCanvas().getHeight());
        square.setFillColor(Color.lightGray); //to change outer square colour
        square.setFilled(true);
        add(square);
        background();
        setUpInfo();
        timer.start();
        randomFood();
        drawSnake();
        addKeyListeners();



    }

    public void randomFood() {
        Random rand = new Random();
        ball = new Ball(50, 50, 15, 15);
        ball.setFillColor(Color.red);
        ball.setFilled(true);
        add(ball);
        int randX = 100 + rand.nextInt(30)*15;
        int randY = 100 + rand.nextInt(20)*15;
        ball.setLocation(randX, randY);
    }

    public void drawSnake() {
        snakeWidth = 15;
        snakeHeight = 15;
        for (int i = 0; i < 4; i++) {
            SnakePart part = new SnakePart(250 + i * 15, 340, snakeWidth, snakeHeight);
            part.setColor(Color.blue);
            part.setFilled(true);
            add(part);
            snakeBody.add(part);

        }
    }

    public void setUpInfo() {
        GLabel startLabel = new GLabel("Click anywhere in this window to start game.", (getWidth() / 2) -65,
                (getHeight() / 2)-20);
        startLabel.move(-startLabel.getWidth() / 2, -startLabel.getHeight());
        Font font = new Font("", Font.BOLD, 12);
        startLabel.setFont(font);
        startLabel.setColor(Color.black);
        add(startLabel);
        GLabel rulesLabel = new GLabel("Use arrow keys to control the snake!", (getWidth() / 2)-65,
                (getHeight() / 2) + 30);
        rulesLabel.move(-rulesLabel.getWidth() / 2, -rulesLabel.getHeight());
        rulesLabel.setFont(font);
        rulesLabel.setColor(Color.black);
        add(rulesLabel);
        GLabel instructionsLabel = new GLabel("Your goal is to eat as many pieces of food as possible.", (getWidth() / 2)-65,
                (getHeight() / 2) + 80);
        instructionsLabel.move(-instructionsLabel.getWidth() / 2, -instructionsLabel.getHeight());
        instructionsLabel.setFont(font);
        instructionsLabel.setColor(Color.black);
        add(instructionsLabel);



        // scoreboard
        scoreLabel = new GLabel("Score : " + score, getWidth() - 200, 70);
        scoreLabel.move(-scoreLabel.getWidth() / 2, -scoreLabel.getHeight());
        Font font1 = new Font("TimesRoman", Font.BOLD, 30);
        scoreLabel.setFont(font1);
        scoreLabel.setColor(Color.darkGray);
        add(scoreLabel);
        waitForClick();
        isPlaying = true;
        remove(startLabel);
        remove(rulesLabel);
        remove(instructionsLabel);
    }


    public void keyPressed(KeyEvent keyPressed) {
        System.out.println("keypressed");
        blockKey = true;
    }


    private void redrawSnake() {
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).setLocation(snakeBody.get(i - 1).getX(), (snakeBody.get(i - 1).getY()));
        }
        for (int i = 0; i < snakeBody.size(); i++) {
            add(snakeBody.get(i));
        }
    }

    private void growSnake() {

        GRect rect = new GRect(snakeBody.get(snakeBody.size() - 1).getX() - snakeWidth,
                snakeBody.get(snakeBody.size() - 1).getY() - snakeHeight, snakeWidth, snakeHeight);
        rect.setFilled(true);
        rect.setColor(Color.BLUE);
        snakeBody.add(rect);
    }

    private void moveUp() {
        redrawSnake();
        snakeBody.get(0).setLocation(snakeBody.get(0).getX(), snakeBody.get(0).getY() - 15);
    }

    private void moveDown() {
        redrawSnake();
        snakeBody.get(0).setLocation(snakeBody.get(0).getX(), snakeBody.get(0).getY() + 15);

    }

    private void moveLeft() {
        redrawSnake();
        snakeBody.get(0).setLocation(snakeBody.get(0).getX() - 15, snakeBody.get(0).getY());
    }

    private void moveRight() {
        redrawSnake();
        snakeBody.get(0).setLocation(snakeBody.get(0).getX() + 15, snakeBody.get(0).getY());
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(!isGameOver)
        {
            scoreLabel.setLabel("Score : " + score);
            if (goingUp) {
                moveUp();


            } else if (goingDown) {
                moveDown();


            } else if (goingRight) {
                moveRight();


            } else if (goingLeft) {
                moveLeft();


            }
            if (intersectsWithFood()) {
                score += 1;
                growSnake();
                remove(ball);
                randomFood();
            }
            if (intersectsWithSnake()||outOfBound()) {
                isGameOver = true;
            }
        }
        else {

            remove(scoreLabel);

            timer.stop();
            GLabel gameOverLabel = new GLabel("Game Over!", getWidth() / 2 -65, getHeight() / 2 -20);
            gameOverLabel.move(-gameOverLabel.getWidth() / 2, -gameOverLabel.getHeight());
            Font font = new Font("", Font.BOLD, 15);
            gameOverLabel.setFont(font);
            gameOverLabel.setColor(Color.black);
            add(gameOverLabel);

            GLabel finalScoreLabel = new GLabel("Final score : " + score, getWidth() /2 -65, getHeight() / 2 +20);
            finalScoreLabel.move(-finalScoreLabel.getWidth() / 2, -finalScoreLabel.getHeight());
            finalScoreLabel.setFont(font);
            finalScoreLabel.setColor(Color.black);
            add(finalScoreLabel);

        }


    }




    private boolean intersectsWithFood() {

        if (ball.getBounds().intersects(snakeBody.get(0).getBounds())) {
            return true;
        } else {
            return false;
        }

    }
    private boolean intersectsWithSnake() {
        GRect head = null;
        if ((!goingLeft && !goingDown && !goingUp && !goingRight) || goingRight) {
            head = new GRect(snakeBody.get(0).getX() + 15, snakeBody.get(0).getY() + 5, 5, 5);
        } else if (goingLeft) {
            head = new GRect(snakeBody.get(0).getX() - 5, snakeBody.get(0).getY() + 5, 5, 5);
        } else if (goingDown) {
            head = new GRect(snakeBody.get(0).getX() + 5, snakeBody.get(0).getY() + 15, 5, 5);
        } else if (goingUp) {
            head = new GRect(snakeBody.get(0).getX() + 5, snakeBody.get(0).getY() - 5, 5, 5);
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (head.getBounds().intersects(snakeBody.get(i).getBounds())) {
                return true;
            }
        }
        return false;

    }

    //for when the snake goes out of bound- going outside of the frame edge
    public boolean outOfBound(){
        if(snakeBody.get(0).getX()>535||snakeBody.get(0).getX()<100||snakeBody.get(0).getY()>385||snakeBody.get(0).getY()<100){
            return true;
        }

        return false;
    }

}
