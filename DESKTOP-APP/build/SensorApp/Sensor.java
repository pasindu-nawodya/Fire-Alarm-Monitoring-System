package SensorApp;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author user
 */
import java.io.Serializable;

public class Sensor implements Serializable {
	
   public String id;
   public String _id;
   public int floorNo;
   public String RoomNo;
   public double smokeLevel;
   public double cdLevel;
   
   public Sensor() {
	   
   }
   
   
   public Sensor(String id,int floorNo, String roomNo, double smokeLevel, double cdLevel) {
	super();
	this.id = id;
	this.floorNo = floorNo;
	this.RoomNo = roomNo;
	this.smokeLevel = smokeLevel;
	this.cdLevel = cdLevel;
}

   
   public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}
   
   public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

public int getFloorNo() {
	return floorNo;
}


public void setFloorNo(int floorNo) {
	this.floorNo = floorNo;
}


public String getRoomNo() {
	return RoomNo;
}


public void setRoomNo(String roomNo) {
	RoomNo = roomNo;
}


public double getSmokeLevel() {
	return smokeLevel;
}


public void setSmokeLevel(double smokeLevel) {
	this.smokeLevel = smokeLevel;
}


public double getCdLevel() {
	return cdLevel;
}


public void setCdLevel(double cdLevel) {
	this.cdLevel = cdLevel;
}
   
	

}


