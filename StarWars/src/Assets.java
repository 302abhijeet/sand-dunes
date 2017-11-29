
import java.awt.image.BufferedImage;


    
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Needs change

public class Assets {
    
    private static final int width=32,height= 32;
    
    public static BufferedImage player, dirt,grass, stone, tree,space;
    
    public static void init(){
        SpriteSheet sheet=new SpriteSheet(imageLoader.loadImage("/textures/sheet.png"));
        
        player = sheet.crop(64,64,width,height);
        dirt = imageLoader.loadImage("/textures/dirt.jpg");
        space = imageLoader.loadImage("/textures/space.jpg");
        grass = sheet.crop(width*2, 0, width, height);
        stone =sheet.crop(width*3, 0, width, height);
        tree = sheet.crop(0,height,width,height);
        
    }
}


