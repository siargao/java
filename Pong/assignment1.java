//Jeffrey Sta-Ines, 14174869, Assignment 1, 159.103
import java.awt.*;
import java.awt.geom.*;
import java.awt.Stroke.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.*;


public class assignment1 extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning - don't edit this function.

		// Create a new game.
		GameEngine engine = new assignment1();

		// Initialise the game
		engine.init();

		// Start the game
		engine.gameLoop(60);
	}

	//-------------------------------------------------------
	// Your Program
	//-------------------------------------------------------

	
	

	// Keep track of keys
	boolean left, right, up1, down1, up2, down2;
	boolean gameOver;

	
	double ballX;
	double ballY;
	double velocityX;
	double velocityY;
	
	//paddle for player1
	double paddle1_width;
	double paddle1_height;
	double paddle1_X;
	double paddle1_Y;
	
	//paddle for player 2
	double paddle2_width;
	double paddle2_height;
	double paddle2_X;
	double paddle2_Y;
	
	double paddle_velocity;
	
	int leftScore = 0;
	int rightScore = 0;
	
	
	
	// Function to initialise the game
	public void init() {
		
		Clip music;
		// Setup booleans
		//left  = false;
		//right = false;
		up1    = false;
		down1  = false;
		up2    = false;
		down2  = false;
		gameOver = false;

		// Initialise the ball
		ballX = 400;
		ballY = 250;
		velocityX = 5;
		velocityY = 5;
	        
		//paddle for player 1
		paddle1_width=10;
		paddle1_height=150;
		paddle1_X=0;
	        paddle1_Y=175;
	       //paddle for player 2
		paddle2_width=10;
		paddle2_height=150;
		paddle2_X=800;
		paddle2_Y=175;
		
		paddle_velocity = 10;
		
		// Play Background soundtrack
		music = playSoundFileLoop("background.wav", -30);
		
		
		
	}
	
	public void update(double dt) {
		
			ballX = ballX + (velocityX * 1.0);
			ballY = ballY + (velocityY * 1.1);
			
		
		       //setting top screen boundary
			if (ballY <=5){
				velocityY = velocityY*(-1);
				ballY = ballY + (velocityY);
			}
			
                        
			//setting bottom screen boundary
			if (ballY >=495){
				velocityY = velocityY*(-1);
				ballY = ballY + (velocityY);
			}
			
			 //player 1 scores
			if (ballX >=795){
				playSoundFile("score.wav");
				ballX = 400;
				ballY = 250;
				velocityX = velocityX*(-1);
				ballX = ballX + (velocityX);
				leftScore++;
			}
			
			//player 2 scores
			if(ballX <=5){
				
				playSoundFile("score.wav");
				ballX = 400;
				ballY = 250;
				velocityX = velocityX*(-1);
				ballX = ballX + (velocityX);
				rightScore++;	
		        }
			
			//setting up collision of ball against paddle 1
			if (( (ballX-5 == (paddle1_X+10)) && ( (ballY >= paddle1_Y) &&(ballY <= paddle1_Y+150)))){
				playSoundFile("beep.wav");
				velocityX = velocityX*(-1);
				velocityX *= 20;
				ballX = ballX + (velocityX);
			}
			
			//setting up collision of ball against paddle 2
			if (( (ballX+5 == paddle2_X-5) && ( (ballY >= paddle2_Y) &&(ballY <= paddle2_Y+150)))){
				playSoundFile("beep.wav");
				velocityX = velocityX*(-1);
				velocityX*= 1.2;
				ballX = ballX + (velocityX);
				
			}
		        
		
		//playSoundFile("score.wav");
		
		


		
	
		//Paddle 1 movements
		if (up1){
			if (paddle1_Y >= 0) {
				paddle1_Y = paddle1_Y - paddle_velocity;
			}
		}
		
		if (down1){
			if (paddle1_Y + paddle1_height + paddle_velocity  <= 500){
				paddle1_Y = paddle1_Y + paddle_velocity;
			}
			
			
		}
		
		//Paddle 2 movements
		if (up2){
			if (paddle2_Y >= 0) {
				paddle2_Y = paddle2_Y - paddle_velocity;
			}
		}
		
		if (down2){
			if (paddle2_Y + paddle2_height + paddle_velocity  <= 500){
				paddle2_Y = paddle2_Y + paddle_velocity;
			}
			
			
		}
		
		
		
		       
	}		

	
	// Function to get frame of animation
	public int getAnimationFrame(double timer, double duration, int numFrames) {
		// Get frame
		int i = (int)Math.floor(((timer % duration) / duration) * numFrames);
		// Check range
		if(i >= numFrames) {
			i = numFrames-1;
		}
		// Return
		return i;
	}


	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to white
		changeBackgroundColor(white);
		clearBackground(800, 500);

		// If the game is not over yet
		if(gameOver == false) {
			
			changeColor(green);
			
			drawCircle(400, 250, 50);
			
			for (int i = 0; i <= 12;i++){
				drawSolidRectangle( 399, 40*i , 3, 30 ); 
			}	
				
			changeColor(black);
			drawSolidCircle(ballX, ballY, 10);
			changeColor(black);
			drawSolidRectangle( 5, paddle1_Y, paddle1_width, paddle1_height ); 
			drawSolidRectangle( 785, paddle2_Y, paddle2_width, paddle2_height ); 
			
			drawText(100,100,intToString(leftScore));
			drawText(700,100,intToString(rightScore));
			if ((leftScore == 10) || (rightScore == 10)){
			gameOver = true;
				
				
			}
		} else {
			// If the game is over
			// Display GameOver text
			changeColor(black);
			drawText(250, 250, "GAME OVER!", 50);
		}
		
	}

	// Called whenever a key is pressed
	public void keyPressed(KeyEvent e) {
		// The user pressed left arrow - record it
		// Record it
		
		if(e.getKeyCode() == KeyEvent.VK_A)    {up1  =  true;}
		
		if(e.getKeyCode() == KeyEvent.VK_Z)    {down1  = true;}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)    {up2  = true;}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)    {down2  = true;}
	}

	// Called whenever a key is released
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_A)    {up1    = false;}
		
		if(e.getKeyCode() == KeyEvent.VK_Z)    {down1    = false;}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)    {up2    = false;}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)    {down2    = false;}
	}
	
		
	public String intToString(int i) {
		return new Integer(i).toString();
	}
	
	//returns true if the ball and paddle1 colides
	void detectCollision1() {
//		if((((paddle1_X - ballX) <= 25) && (ballY => paddle1_Y +25 ))){
				ballX = velocityX + 5;
				ballY = velocityY  - 5*1.1;
		//}
	}	
	
	
}