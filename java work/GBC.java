import java.awt.*;

/**
* Simplifies the use of GridBagConstraints class.
* @version : 1.01
* @author : Cay Horstmann
*/

public class GBC extends GridBagConstraints{

	/**
	* Construct a GBC with a given gridx and gridy.
	*/
	public GBC(int gridx,int gridy){
		this.gridx = gridx;
		this.gridy = gridy;
	}
	
	/**
	* Construct a GBC with given gridx, gridy, gridwidth, gridheight and all 
	* other grid bag constraint values set to the default.
	*/
	public GBC(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	
	/**
	* Set the anchor.
	*/
	public GBC setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	
	/**
	* Set the fill direction.
	*/
	public GBC setFill(int fill){
		this.fill = fill;
		return this;
	}
	
	/**
	* Set the cell weight.
	*/
	public GBC setWeight(double weightx,double weighty){
		this.weightx = weightx;
		this.weighty = weighty;
		return this;
	}
	
	/**
	* Set the insets of this cell.
	*/
	public GBC setInsets(int distance){
		this.insets = new Insets(distance,distance,distance,distance);
		return this;
	}
	
	/**
	* Set the insets of this cell.
	*/
	public GBC setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
	
	/**
	* Set the internal padding.
	*/
	public GBC setIpad(int ipadx,int ipady){
		this.ipadx = ipadx;
		this.ipady = ipady;
		return this;
	}
}