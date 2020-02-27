/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cstuser
 */
enum Direction{
    UP, DOWN, LEFT, RIGHT, STOP;
}

public class World extends JPanel implements KeyListener{
    private Integer[][] grid = new Integer[12*9][2];
    private final int SQUARE = 40;
    
    private int score;
    private int highScore; 
    
    public boolean gameOver = false;
    private JButton playAgain = new JButton("Play Again");
    
    private Direction direction = Direction.DOWN;
    
    public SnakePart fruit = new SnakePart();
    private ArrayList<SnakePart> snake;
    
    public World() {
        addKeyListener(this);
        setFocusable(true);
        this.add(playAgain);
        playAgain.setBackground(Color.BLACK);
        playAgain.setForeground(Color.WHITE);
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.clear();
                snake.add(new SnakePart(0, 0));
                playAgain.setVisible(false);
                direction = Direction.DOWN;
                gameOver = false;
            }
        });
        playAgain.setVisible(false);
        
        initializeGrid();
        
        snake = new ArrayList<>();
        snake.add(new SnakePart(0,0));
        
        drawFruit();
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        
        if(!gameOver){
            g.setColor(Color.BLACK);
            for (Integer[] square : grid) {
                g.fillRect(square[0]*SQUARE, square[1]*SQUARE, SQUARE, SQUARE);
            }

            g.setColor(Color.RED);
            for (SnakePart snakePart : snake) {
                g.fillRect(snakePart.x, snakePart.y, SQUARE, SQUARE);
            }

            g.setColor(Color.ORANGE);
            g.fillRect(fruit.x, fruit.y, SQUARE, SQUARE);
        }
        else{
            if(score > highScore)
                highScore = score;
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", SQUARE, SQUARE*2);
            g.setColor(Color.WHITE);
            g.drawString("Score : " + this.score, SQUARE, SQUARE*3+10);
            g.drawString("High Score : " + this.highScore, SQUARE, SQUARE*4+20);
            playAgain.setVisible(true);
        }
    }
    
    private void initializeGrid(){
        int[] ys = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] xs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        
        int i = 0;
        
        for (int x : xs) {
            for (int y : ys) {
                grid[i][0] = x;
                grid[i][1] = y;
                
                i++;
            }
        }
    }
    
    private void drawFruit(){
        Random rnd = new Random();
        
        ArrayList<Integer> squares = new ArrayList<>();
        for (int i = 0; i < 9*12; i++) {
            squares.add(i);
        }
        
        for (SnakePart snakePart : snake) {
            Integer[] coordinates = {snakePart.x/SQUARE, snakePart.y/SQUARE};
            
            for (int j = 0; j < grid.length; j++) {
                if(coordinates.equals(grid[j]))
                    squares.remove(j);
            }
        }
        
        Integer[] position = Arrays.copyOf(grid[squares.get(rnd.nextInt(squares.size()))], 2);
        
        fruit.setX(position[0]*SQUARE);
        fruit.setY(position[1]*SQUARE);
    }
    
    public void drawSnake() {
        switch(direction){
            case UP:
                snake.get(0).setY(snake.get(0).y - SQUARE);
                snake.get(0).setX(snake.get(0).x);
                break;
            case DOWN:
                snake.get(0).setY(snake.get(0).y + SQUARE);
                snake.get(0).setX(snake.get(0).x);
                break;
            case RIGHT:
                snake.get(0).setX(snake.get(0).x + SQUARE);
                snake.get(0).setY(snake.get(0).y);
                break;
            case LEFT:
                snake.get(0).setX(snake.get(0).x - SQUARE);
                snake.get(0).setY(snake.get(0).y);
                break;
        }
        
        for(int i = 1; i < snake.size(); i++){
            snake.get(i).setX(snake.get(i-1).getPreviousX());
            snake.get(i).setY(snake.get(i-1).getPreviousY());
        }
        
        this.gameOver = testGameOver();
        
        if(fruit.x == snake.get(0).x && fruit.y == snake.get(0).y){
            drawFruit();
            score += 10;
            snake.add(new SnakePart(snake.get(snake.size()-1).x, snake.get(snake.size()-1).y));
        }
    }
    
    private boolean testGameOver(){
        if(snake.get(0).x > 480 || snake.get(0).x < 0)
            return true;
        else if(snake.get(0).y > 360 || snake.get(0).y < 0)
            return true;
        for (int i = 1; i < snake.size(); i++){
            if(snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y)
                return true;
        }
        
        return false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if(!direction.equals(Direction.DOWN))
                    direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                if(!direction.equals(Direction.UP))
                    direction = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                if(!direction.equals(Direction.RIGHT))
                    direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if(!direction.equals(Direction.LEFT))
                    direction = Direction.RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                direction = Direction.STOP;
                break;
            default:
                drawFruit();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}



class SnakePart {
    public int x, y, previousX, previousY;

    public SnakePart() {
    }
    
    SnakePart(int x, int y){
        this.x = x;
        this.y = y;
        
    }

    public void setX(int x) {
        this.previousX = this.x;
        this.x = x;
    }
    
    public void setY(int y) {
        this.previousY = this.y;
        this.y = y;
    }

    public int getPreviousX() {
        return this.previousX;
    }

    public int getPreviousY() {
        return this.previousY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ") With Previous : (" + previousX + ", " + previousY + ")";
    }
}