
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abhijeetsingh
 */
public class Game implements Runnable{
    
    int height,width;
    private Display display;
     public String title;
    
    private Thread thread;
    private boolean running = false;
    
    private BufferStrategy bs;
    private Graphics g;
    
    //States
    private State gameState;  
    private State menuState;
    
    //Inputs
    private KeyManager keyManager;
   
    public Game(String title,int width,int height){
        this.width=width;
        this.height=height;
        this.title=title; 
        keyManager=new KeyManager();
    }
    
    private void init(){
        display=new Display(title,width,height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();
        
        gameState=new GameState(this);
        menuState=new MenuState(this);
        State.setState(gameState);
    }
    
    private void tick(){
        keyManager.tick();
        
        if(State.getState() != null)
            State.getState().tick();
    }
    
    private void render(){
        bs=display.getCanvas().getBufferStrategy();
        if(bs==null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g=bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);
        //Draw here!
        
        if(State.getState() != null)
            State.getState().render(g);
         
        //End Drawing!
        bs.show();
        g.dispose();
    }
    
    public void run(){
        
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer=0;
        int ticks=0;
        
        while(running){
            now=System.nanoTime();
            delta += (now-lastTime)/timePerTick;
            lastTime = now;
            
            if(delta >= 1){
                tick();
                render();
                --delta;
            }
            
        }
        
        try {
            stop();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public KeyManager getKeyManager(){
        return keyManager;
    }
    
    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread=new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() throws InterruptedException{
        if(!running)
            return;
        running=false;
        thread.join();
    }
    
}
