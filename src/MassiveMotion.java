import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * This is a simple animation graphic that has a yellow star and several black bodies moving
 * across the screen in different directions.
 * All settings are read from the file MassiveMotion.txt.
 * The displays use custom list implementations (ArrayList, LinkedList, DoublyLinkedList, DummyHeadLinkedList)
 */
public class MassiveMotion extends JPanel implements ActionListener {

    /** Controls how often the screen updates */
    protected Timer tm;

    /** Body size and movement speed of each body */
    protected int bodyVelocity;
    protected int bodySize;

    protected double genX, genY;

    /** Star position, size, and speed */
    protected int starX, starY, starSize;
    protected int starVX, starVY;

    /** Width and height of the window */
    protected int windowWidth, windowHeight;

    /** Stores all the moving bodies */
    protected List<MassiveMotion.Body> bodies;

    /** Represents a single object on screen */
    protected static class Body{
        int x, y, vx, vy;
        Body(int x, int y, int vx, int vy){
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
        }
    }

    /** Sets up animation by reading from the text file */
    // public MassiveMotion(String profile) {
    public MassiveMotion() throws Exception {
        Properties p = new Properties();
        p.load(new FileInputStream("MassiveMotion.txt"));

        int delay = Integer.parseInt(p.getProperty("timer_delay"));
        tm = new Timer(delay, this);

        bodyVelocity = Integer.parseInt(p.getProperty("body_velocity"));
        bodySize = Integer.parseInt(p.getProperty("body_size"));

        genX = Double.parseDouble(p.getProperty("gen_x"));
        genY = Double.parseDouble(p.getProperty("gen_y"));

        starX = Integer.parseInt(p.getProperty("star_position_x"));
        starY = Integer.parseInt(p.getProperty("star_position_y"));
        starSize = Integer.parseInt(p.getProperty("star_size"));
        starVX = (int)Math.round(Double.parseDouble(p.getProperty("star_velocity_x")));
        starVY = (int)Math.round(Double.parseDouble(p.getProperty("star_velocity_y")));

        windowWidth = Integer.parseInt(p.getProperty("window_size_x"));
        windowHeight = Integer.parseInt(p.getProperty("window_size_y"));

        String listType = p.getProperty("list", "arraylist");
        if(listType.contains("single")) bodies = new LinkedList<>();
        else if (listType.contains("double")) bodies = new DoublyLinkedList<>();
        else if (listType.contains("dummy")) bodies = new DummyHeadLinkedList<>();
        else bodies = new ArrayList<>();         
    }

    /** Draws the star and the bodies on the screen */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Probably best you leave this as is.

        g.setColor(Color.YELLOW);
        g.fillOval(starX- starSize/2, starY - starSize/2, starSize, starSize);

        g.setColor(Color.BLACK);
        for(int i = 0; i < bodies.size(); i++){
            Body b = bodies.get(i);
            g.fillOval(b.x, b.y, bodySize, bodySize);
        }
        // Recommend you leave the next line as is
        tm.start();
    }

    /** Moves all the bodies with 10 different patterns */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(bodies.size() < 10) {
            int mode = bodies.size() % 10;

            int x = 0, y = 0, vx = 0, vy = 0;

            if (mode == 0) { // top → down-right
                x = (bodies.size() * 20) % windowWidth;
                y = 0;
                vx = bodyVelocity;
                vy = bodyVelocity;
            } else if (mode == 1) { // bottom → up-left
                x = (bodies.size() * 25) % windowWidth;
                y = windowHeight - bodySize;
                vx = -bodyVelocity;
                vy = -bodyVelocity;
            } else if (mode == 2) { // left → right
                x = 0;
                y = (bodies.size() * 30) % windowHeight;
                vx = bodyVelocity;
                vy = 0;
            } else if (mode == 3) { // right → left
                x = windowWidth - bodySize;
                y = (bodies.size() * 18) % windowHeight;
                vx = -bodyVelocity;
                vy = 0;
            } else if (mode == 4) { // top → down-left
                x = (bodies.size() * 12) % windowWidth;
                y = 0;
                vx = -bodyVelocity;
                vy = bodyVelocity;
            } else if (mode == 5) { // bottom → up-right
                x = (bodies.size() * 15) % windowWidth;
                y = windowHeight - bodySize;
                vx = bodyVelocity;
                vy = -bodyVelocity;
            } else if (mode == 6) { // left → down-right (faster x)
                x = 0;
                y = (bodies.size() * 10) % windowHeight;
                vx = 2 * bodyVelocity;
                vy = bodyVelocity;
            } else if (mode == 7) { // right → up-left (faster y)
                x = windowWidth - bodySize;
                y = (bodies.size() * 22) % windowHeight;
                vx = -bodyVelocity;
                vy = -2 * bodyVelocity;
            } else if (mode == 8) { // top → straight down (slow)
                x = (bodies.size() * 14) % windowWidth;
                y = 0;
                vx = 0;
                vy = bodyVelocity / 2;
            } else { // bottom → straight up (slow)
                x = (bodies.size() * 19) % windowWidth;
                y = windowHeight - bodySize;
                vx = 0;
                vy = -bodyVelocity / 2;
            }

            bodies.add(new MassiveMotion.Body(x, y, vx, vy));
        }

        for (int i = 0; i < bodies.size(); i++){
            MassiveMotion.Body b = bodies.get(i);
            b.x += b.vx;
            b.y += b.vy;

            if (b.x > windowWidth - bodySize) b.x = 0;
            else if (b.x < 0) b.x = windowWidth - bodySize;
            if (b.y > windowHeight - bodySize) b.y = 0;
            else if (b.y < 0) b.y = windowHeight - bodySize;

        }

        starX = starX + starVX;
        starY = starY + starVY;

        // Keep this at the end of the function (no matter what you do above):
        repaint();
    }

    /** Starts the animaation */
    public static void main(String[] args) throws Exception{
        System.out.println("Massive Motion starting...");
        // MassiveMotion mm = new MassiveMotion(args[0]);
        Properties p = new Properties();
        p.load(new FileInputStream("MassiveMotion.txt"));
        int w = Integer.parseInt(p.getProperty("window_size_x"));
        int h = Integer.parseInt(p.getProperty("window_size_y"));

        MassiveMotion mm = new MassiveMotion();

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.setSize(w, h);
        jf.add(mm);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
