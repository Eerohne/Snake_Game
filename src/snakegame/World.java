/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cstuser
 */
public class World extends JPanel{
    private final int SQUARE = 40;
    private ArrayList<Integer> snake = new ArrayList<>();
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        
        g.fillRect(WIDTH, WIDTH, WIDTH, WIDTH);
        
    }
    
    private void fruit(Graphics g, int x, int y){
        g.fillRect(x, y, SQUARE, SQUARE);
    }
    
    private void drawSnake(){
        
    }
}

class Snake {
    
}