package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Box;

public class Player extends GameObject implements EventHandler<Event> 
{
	private static final double DEFAULT_POSITION_X = 0;
	private static final double DEFAULT_POSITION_Y = 0;
	private static final double DEFAULT_POSITION_Z = 0;
	
    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;
	
    private PerspectiveCamera camera;
    private Box shape;
    
    private int lane = 1;
	
	public Player(Position position)
	{
		super(position);
		
		shape = new Box(30.0, 30.0, 30.0);
		shape.setVisible(false);

        camera = new PerspectiveCamera(true);
        camera.setNearClip(NEAR_CLIP);
        camera.setFarClip(FAR_CLIP);
        camera.setFieldOfView(FIELD_OF_VIEW);
        
        this.setTranslateY(position.getY());
        
        this.getChildren().addAll(shape, camera);
	}
	
	public static Player InstantiatePlayer() 
	{
		return new Player(new Position(DEFAULT_POSITION_X,
									   DEFAULT_POSITION_Y,
									   DEFAULT_POSITION_Z));
	}

	@Override
	public void handle(Event event) 
	{		
		if (event instanceof KeyEvent) 
		{
			KeyEvent keyEvent = (KeyEvent)event;
	        if (keyEvent.getCode() == KeyCode.ESCAPE &&
	        	keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
	        {
	            System.exit(0);
	        }
	        else 
	        {
	    		if (!Game.isGameActive())
	    		{
	    			return;
	    		}
	    		
		        if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) &&
			        	  keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
		        {
		        	moveLeft();
		        }
		        else if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) &&
			        	  keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
		        {
		        	moveRight();
		        } 	
	        }
		}
	}
	
	public Bounds getParentBounds() 
	{
		return this.shape.getBoundsInParent();
	}
	
    public Camera getCamera() 
    {
        return camera;
    }
    
    private void moveLeft() 
    {
    	if (lane == 0) 
    	{
    		return;
    	}
    	
    	lane--;
    	this.setTranslateX(this.getTranslateX() - Track.LANE_WIDTH);
    }
    
    private void moveRight() 
    {
    	if (lane == 2) 
    	{
    		return;
    	}
    	
    	lane++;
    	this.setTranslateX(this.getTranslateX() + Track.LANE_WIDTH);
    }
}
