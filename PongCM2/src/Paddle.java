import java.awt.Color;

public class Paddle extends Sprite
{
	private static final Color PADDLE_COLOUR = Color.WHITE;
	private static final int PADDLE_WIDTH = 10;
	private static final int PADDLE_HEIGHT = 100;
	private static final int DISTANCE_FROM_EDGE = 10;
	
	public Paddle(Player player, int panelWidth, int panelHeight)
	{
		setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColour(PADDLE_COLOUR);
		
		int xPos;
		
		if (player == Player.One)
			xPos = DISTANCE_FROM_EDGE;
		else
			xPos = panelWidth - getWidth() - DISTANCE_FROM_EDGE;
		
		setInitialPosition(xPos, panelHeight / 2 - (getHeight() / 2));
		resetToInitialPosition();
	}
}
