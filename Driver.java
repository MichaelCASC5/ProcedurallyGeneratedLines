import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.Robot;

public class Driver extends JComponent implements KeyListener, MouseListener, MouseMotionListener{
    private boolean midOnce = true;
    
    private int WIDTH, HEIGHT;
    private int deg;
    private int scale;
    private int mX,mY;
    private int border;
    
    private boolean play;
    
    private static double rM, rL;
    
    private ArrayList<Line> lines = new ArrayList<>();
    
    /*
        ** A list of cool seeds **
        * 3874927L spiral right
        * 81372657L spiral up
        * 1234567890L giant mickeys up
        * 813723357L giant pillars down
    */
    
    private Pseudorandom middle = new Pseudorandom(813723357L);
    private Pseudorandom linear = new Pseudorandom(3874927L);
    
    public Driver(){
        WIDTH = 1000;
        HEIGHT = 1000;
        
        deg = 2;
        scale = 1;
        border = 0;
        
        play = true;
        
        //Create rand lines
        for(int i=0;i<1;i++){
            lines.add(new Line(WIDTH/2, HEIGHT/2));
        }
        
        //Create middle-square lines
        for(int i=0;i<1;i++){
            lines.add(new Line(WIDTH/2, HEIGHT/2, "mid"));
        }
        
        //Create LCM lines
        for(int i=0;i<1;i++){
            lines.add(new Line(WIDTH/2, HEIGHT/2, "lcm"));
        }
       
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Procedural Generation");
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
       
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //Color pallet
        Color white = new Color(255,255,255,100);
        Color blue = new Color(100,100,255,100);
        Color pink = new Color(255,200,200,100);
        
        //Drawing Lines
        int a,b,c,d;
        for(int i=0;i<lines.size();i++){
            if(lines.get(i).getID().equals("rand")){
                g.setColor(white);
            }else if(lines.get(i).getID().equals("mid")){
                g.setColor(blue);
            }else{
                g.setColor(pink);
            }
            
            for(int j=0;j<lines.get(i).getCount();j++){
                a = lines.get(i).getX(j);
                a+=mX;
                a-=WIDTH/2;
                a*=scale;
                a+=WIDTH/2;
                
                b = lines.get(i).getY(j);
                b+=mY;
                b-=HEIGHT/2;
                b*=scale;
                b+=HEIGHT/2;
                
                c = (deg * scale);
                d = (deg * scale);
                
                g.fillRect(a,b,c-border,d-border);
            }
        }
        
        //Message
        g.setColor(Color.green);
        String playButton;
        if(play){
            playButton = "RUNNING";
        }else{
            playButton = "PAUSED";
        }
        g.drawString(playButton,(WIDTH/2)-50, (HEIGHT/2));
    }
    public void loop(){
        int x1=0, y1=0;
        
        double r;
        Point last;
        if(play){
            for(int i=0;i<lines.size();i++){
                last = lines.get(i).getLast();

                x1 = last.getX();
                y1 = last.getY();

                if(lines.get(i).getID().equals("rand")){
                    r = Math.random();
                }else if(lines.get(i).getID().equals("mid")){
                    r = rM;
                }else{
                    r = rL;
                }

                if(r >= 0 && r < 0.25){
                    x1 = x1 + deg;
                }else if(r >= 0.25 && r < 0.5){
                    y1 = y1 + deg;
                }else if(r >= 0.5 && r < 0.75){
                    x1 = x1 - deg;
                }else if(r >= 0.75 && r < 1){
                    y1 = y1 - deg;
                }

                last.setX(x1);
                last.setY(y1);

                lines.get(i).newPoint(last.getX(),last.getY());
                
//                if(lines.get(i).getCount() > 1000){
//                    lines.get(i).remove();
//                }
            }
        
            //Rand
            rM = middle.runMid(rM);
            rL = linear.runLin(rL);
        }
        repaint();
    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        
        int fact = 5;
        
        if(k == 87){//FORWARD
            mY+=fact;
        }else if(k == 83){//BACKWARD
            mY-=fact;
        }else if(k == 65){//RIGHT
            mX+=fact;
        }else if(k == 68){//LEFT
            mX-=fact;
        }
        
        if(k == 32){//UP
            if(play){
                play = false;
            }else{
                play = true;
            }
        }
        
        if(k == 27){//ESC
            System.exit(0);
        }
    }
    public void keyReleased(KeyEvent e){}
    public void mousePressed(MouseEvent e){
        int k = e.getButton();
        
        if(k == 1){
            scale*=2;
        }else if(k == 3){
            if(scale != 1)
                scale/=2;
        }else if(k == 2){
            if(border == 3){
                border = 0;
            }else{
                border = 3;
            }
        }
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        if(ticks!=0){
                            //e.printStackTrace();
                        }
                    }
                }
            }
        };
        gameThread.start();
    }
    public static void main(String[] args){
        Driver g = new Driver();
        g.start(1000);
    }
}