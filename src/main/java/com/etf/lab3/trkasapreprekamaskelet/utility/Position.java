package com.etf.lab3.trkasapreprekamaskelet.utility;

public class Position 
{
    private double x;
    private double y;
    private double z;
    
    public Position(double x, double y, double z) 
    {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    
    public double getX()
    {
    	return this.x;
    }
    
    public double getY()
    {
    	return this.y;
    }
    
    public double getZ()
    {
    	return this.z;
    }
}
