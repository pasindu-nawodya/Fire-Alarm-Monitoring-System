import java.util.ArrayList;
import java.util.List;

public interface FireAlarmClient extends java.rmi.Remote {

	
	public void getSensorDetails(List<Sensor> sensorList) throws 	java.rmi.RemoteException;;
}
