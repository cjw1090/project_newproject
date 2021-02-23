package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import shapes.GEGroup;
import shapes.GEShape;

public class GERotater extends GETransformer {

	private ArrayList<GEShape> shapelist;
	private Point2D.Double ROrigin;
	private double theta; 
	private boolean moved;
	
	public GERotater(GEShape shape) {
		super(shape);
		if(shape instanceof GEGroup) {
			shapelist = new ArrayList<GEShape>();
			for(GEShape subshape : ((GEGroup) shape).getChildList()) {
				shapelist.add(subshape);
			}
		}
		//af = new AffineTransform();
	}

	@Override
	public void transformer(Graphics2D g2d, Point p) {
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(dashedLineStroke);
		double nextTheta = theta - Math.atan2(ROrigin.y- p.getY(), p.getX() - ROrigin.x);
		if(shape instanceof GEGroup){
			GEShape temp;
			for(int i = 0; i < shapelist.size(); i++){
				temp = shapelist.get(i);
				temp.draw(g2d);
				temp.rotaterCoordinate(nextTheta, ROrigin);
				temp.draw(g2d);
				
			}
		}else{
			shape.draw(g2d);
			shape.rotaterCoordinate(nextTheta, ROrigin);
			shape.draw(g2d);
		}
	}
	
	public boolean isMoved(){
		return moved;
	}
	
	public void setMove(boolean moved){
		this.moved = moved;
//		moved = false;
	}
	

	@Override
	public void init(Point p) {
		ROrigin = new Point2D.Double(shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
		theta = Math.atan2(ROrigin.y - p.getY(), ROrigin.x - p.getX());

	}
	
	public void rotater(double theta, Graphics2D g2d){
		ROrigin = new Point2D.Double(shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
		shape.rotaterCoordinate(theta, ROrigin);
	}

}



