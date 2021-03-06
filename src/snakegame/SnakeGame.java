/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javax.swing.JFrame;

/**
 *
 * @author cstuser
 */
public class SnakeGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(480, 360);
        window.setResizable(true);
        window.setUndecorated(true);
        World game = new World();
        
        window.add(game);
        window.setVisible(true);
        
        while(true){
            Thread.sleep(game.speed);
            if(!game.gameOver)
                game.drawSnake();
            game.repaint();
            //System.out.println("DONE");
        }
    }
    
}
