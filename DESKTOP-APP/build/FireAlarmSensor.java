import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

//Remote interface
public interface FireAlarmSensor extends java.rmi.Remote {

	public void addSensor(Sensor s)throws
	java.rmi.RemoteException;
	
	public List<Sensor> getSensors() throws
	java.rmi.RemoteException;
	
	public List<Admin> getAdmins() throws
	java.rmi.RemoteException;
	
	public void EditSensor(Sensor sensor) throws
	java.rmi.RemoteException;
	
	
	public void addClient(FireAlarmClient client) throws
	java.rmi.RemoteException;
	
	public void removeClient(FireAlarmClient client) throws
	java.rmi.RemoteException;
	
	public void sendSMS(int floor, String room, double smokeLevel, double cdLevel) throws
	java.rmi.RemoteException;
	
}
