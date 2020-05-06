import java.io.Serializable;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;



public class Client extends UnicastRemoteObject implements Serializable,
FireAlarmClient, Runnable {

	public static List<Sensor> sensorList;
	public static sensorui sList = new sensorui();
	public boolean isLoggedIn = false;
	

	private static final long serialVersionUID = 1L;

	public Client() throws RemoteException{
		
	}
	
	
	public static void main(String[] args) throws Exception {

		   System.setProperty("java.security.policy", "file:allowall.policy");
	 
	            //--------Searching the appropriate server in RMI registry using lookup() method-----
			try {
				
				String registration = "//localhost/FireAlarmSensor";
				Remote remoteService = Naming.lookup(registration);
				FireAlarmSensor sensor = (FireAlarmSensor) remoteService;
				
				
				sensorList = sensor.getSensors();
	
				Client c = new Client();
			
				sensor.addClient(c);
				
				sList.sensorList(sensorList);
				sList.show_sensor();
				sList.getServer(sensor);
				sList.getClient(c);
				sList.setVisible(true);
			       
				c.run();
				
			} catch (MalformedURLException mue) {
				System.out.println(mue);
			} catch (RemoteException re) {
				System.out.println(re);
			} catch (NotBoundException nbe) {
				System.out.println(nbe);
			}
		}
	

	@Override
	public void getSensorDetails(List<Sensor> sensorList) {
			
		
      
		this.sensorList = sensorList;
		sList.sensorList(sensorList);		
		sList.selectSID();
		sList.refreshTable();
		try {
			sList.show_sensor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void setLoginStatus(boolean result) {
		
		this.isLoggedIn = result;
	}
	
     public boolean getLoginStatus() {
		
		return this.isLoggedIn;
	}
	@Override
	public void run() {
		
		
		for (;;) {
			//count++;
		
		// note that this might only work on windows console
			//System.out.print("\r" + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}

		}
		
	}

}
