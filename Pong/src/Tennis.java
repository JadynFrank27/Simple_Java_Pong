import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tennis extends Applet implements Runnable, KeyListener {
    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    Ball b1;
    AIPaddle p2;
    boolean gameStarted;
    Graphics gfx;
    Image img;

    public void init(){
        this.resize(WIDTH, HEIGHT);

        this.addKeyListener(this); //Tennis is a key listener
        gameStarted = false;
        p1 = new HumanPaddle(1); //1 means that the humanPaddle will be on the left. 2 would mean the paddle would be on the right.
        b1 = new Ball();
        p2 = new AIPaddle(2, b1);
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();
    }

    public void paint (Graphics g) {

        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, WIDTH, HEIGHT); //background
        if (b1.getX() < -10 || b1.getX() > 710) {
            g.setColor(Color.red);
            g.drawString("Game Over", 350, 250);
        } else {
            p1.draw(gfx);
            b1.draw(gfx);
            p2.draw(gfx);
        }

        if (!gameStarted) {
            gfx.setColor(Color.white);
            gfx.drawString("Tennis", 340, 100);
            gfx.drawString("Press Enter to Begin...", 310, 130);
        }
        g.drawImage(img, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
        for(;;) {
            //infinite loop
            if (gameStarted) {
                p1.move();
                p2.move();
                b1.move();
                b1.checkPaddleCollision(p1, p2);
            }
            repaint(); //This is what keeps the game running repeatedly.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.setUpAccel(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.setDownAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.setUpAccel(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.setDownAccel(false);
        }
    }
}
