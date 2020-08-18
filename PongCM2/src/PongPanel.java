
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener
{
	private final static Color BACKGROUND_COLOUR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	private static final int BALL_MOVEMENT_SPEED = 2;
	private static final int PADDLE_MOVEMENT_SPEED = 2;
	
	
	GameState gameState = GameState.Initialising;
	
	Ball ball;
	Paddle paddle1;
	Paddle paddle2;
	
	public PongPanel()
	{
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		
		addKeyListener(this);
		setFocusable(true);
	}
	
	private void update()
	{
		switch(gameState)
		{
			case Initialising:
			{
				createObjects();
				gameState = GameState.Playing;
				ball.setXVelocity(BALL_MOVEMENT_SPEED);
				ball.setYVelocity(BALL_MOVEMENT_SPEED);
				break;
			}
			case Playing:
			{
				moveObject(paddle1);
				moveObject(paddle2);
				moveObject(ball);
				checkWallBounce();
				checkPaddleBounce();
				break;
			}
			case GameOver:
			{
				break;
			}
		}
	}
	
	private void paintSprite(Graphics g, Sprite sprite)
	{
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	public void createObjects()
	{
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	public void paintDottedLine(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	public void moveObject(Sprite sprite)
	{
		sprite.setXPosition(sprite.getXPosition() + sprite.getXVelocity());
		sprite.setYPosition(sprite.getYPosition() + sprite.getYVelocity(), getHeight());
	}
	
	public void checkWallBounce()
	{
		if (ball.getXPosition() <= 0)
		{
			ball.setXVelocity(-ball.getXVelocity());
			resetBall();
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth())
		{
			ball.setXVelocity(-ball.getXVelocity());
			resetBall();
		}
		
		if(ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() -ball.getHeight())
			ball.setYVelocity(-ball.getYVelocity());
	}
	
	public void checkPaddleBounce()
	{
		if (ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle()))
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
		
		if (ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle()))
			ball.setXVelocity(-BALL_MOVEMENT_SPEED);
		
	}
	
	public void resetBall()
	{
		ball.resetToInitialPosition();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintDottedLine(g);
		if (gameState != GameState.Initialising)
		{
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		// move player 1
		if (event.getKeyCode() == KeyEvent.VK_W)
			paddle1.setYVelocity(-PADDLE_MOVEMENT_SPEED);
		else if (event.getKeyCode() == KeyEvent.VK_S)
			paddle1.setYVelocity(PADDLE_MOVEMENT_SPEED);
		
		// move player 2
		if (event.getKeyCode() == KeyEvent.VK_UP)
			paddle2.setYVelocity(-PADDLE_MOVEMENT_SPEED);
		else if (event.getKeyCode() == KeyEvent.VK_DOWN)
			paddle2.setYVelocity(PADDLE_MOVEMENT_SPEED);
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		// stop player 1
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S)
			paddle1.setYVelocity(0);

		// stop player 2
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)
			paddle2.setYVelocity(0);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		update();
		repaint();
	}
}
