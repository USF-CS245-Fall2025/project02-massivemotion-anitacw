import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;

    protected int bodyVelocity;
    protected int bodySize;
    protected double genX, genY;

    protected int starX, starY, starSize;
    protected int starVX, starVY;

    protected int windowWidth, windowHeight;

    protected int tick = 0;
    protected List<Body> bodies;

    protected static class Body{
        int x, y, vx, vy;
        Body(int x, int y, int vx, int vy){
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
        }
    }

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
        starVX = (int)Math.round(Double.parseDouble(p.getProperty("star_velocity_y")));

        windowWidth = Integer.parseInt(p.getProperty("window_size_x"));
        windowHeight = Integer.parseInt(p.getProperty("window_size_y"));

        //String whichList = p.getProperty("list", "arraylist");
    }

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


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int w = windowWidth;
        int h = windowHeight;

        int y; int vy;

        if(tick % 2 ==0){
            y = 0;
            vy = bodyVelocity;
        }else{
            y = h - bodySize;
            vy = -bodyVelocity;
        }

        int x = (bodies.size() * 20) % (w -bodySize);
        bodies.add(new Body(x, y, 0, vy));
        tick++;

        for (int i = 0; i < bodies.size(); i++){
            Body b = bodies.get(i);
            b.y = b.y + b.vy;

            if (b.y > h - bodySize){
                b.y = 0;
            } else if (b.y < 0){
                b.y = h - bodySize;
            }
        }

        starX = starX + starVX;
        starY = starY + starVY;

        // Keep this at the end of the function (no matter what you do above):
        repaint();
    }

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
