
import java.util.*;


import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.chilkatsoft.CkJsonArray;
import com.chilkatsoft.CkJsonObject;
import com.chilkatsoft.CkStringBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.rmi.*;
import java.rmi.server.*;



public class FireAlarmSensorServer extends UnicastRemoteObject implements
FireAlarmSensor, Runnable,Serializable  {
	
	
	
	
	
	//---Account SID for twilio-------
	 public static final String ACCOUNT_SID = "AC2c49b397d2035b2e9a478ca8172bbc3c";
	 
	//---Account Authentication Token for twilio-------
	 public static final String AUTH_TOKEN = "fc88a0cac0f5b1d06b1d671b0b70e362";
	 
	 
	private static final long serialVersionUID = 1L;
	
	public   List<Sensor> sensorList = new CopyOnWriteArrayList<Sensor>();
	public   List<Admin> adminList = new CopyOnWriteArrayList<>();
	public   Sensor newSensor;
	private  List<FireAlarmClient> clientList = new CopyOnWriteArrayList<>();
	
	
	
	
	public FireAlarmSensorServer() throws java.rmi.RemoteException{
		
		//static data to test
		/*sensorList.add(new Sensor("11A",1,"1AAA",3,4));
		sensorList.add(new Sensor("22A",2,"2A",5,2));
		sensorList.add(new Sensor("22B",2,"2B",1,0));
		sensorList.add(new Sensor("33A",3,"3A",0,6));
		adminList.add(new Admin("admin","admin"));
		adminList.add(new Admin("dulini","dulini"));*/
		
	}

	public void saveSensorsToList() {
		
		
		  //----get response from api---------------
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:4000/sensors");
	    
		//----parse response to JSON Object----------
		JSONParser parser = new JSONParser();
		Object obj;
		
		
		
		
		
		try {
			 obj = parser.parse(target.request(MediaType.TEXT_XML).get(String.class));
			 JSONArray array = (JSONArray)obj;
			 
			 List<Sensor> sList = new CopyOnWriteArrayList<Sensor>();
			
			 //-----Iterate through Json array and update ArryList-----------
			 for(int i = 0 ;i<array.size();++i) {
				 JSONObject obj2 = (JSONObject)array.get(i);
				 Sensor sensor = new Sensor();
				 
				 sensor.set_id(obj2.get("_id").toString());
				 sensor.setId(obj2.get("sensorid").toString());
				 sensor.setFloorNo(new Integer(obj2.get("floor").toString()));
				 sensor.setRoomNo(obj2.get("room").toString());
				 sensor.setSmokeLevel(new Double( obj2.get("smokelevel").toString()));
				 sensor.setCdLevel(new Double( obj2.get("colevel").toString()));
				 
				 sList.add(sensor);
				 
				
				 
				 
			 }
			
			 sensorList = sList;
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void addClient(FireAlarmClient client) throws java.rmi.RemoteException {
		
		System.out.println("adding client -" + client);
		clientList.add(client);
	}

	public void removeClient(FireAlarmClient client) throws java.rmi.RemoteException {
		System.out.println("Remove client -" + client);
		clientList.remove(client);
		
	}
	
	
	//---------Get Admins from API--------
	
	public void getAdminsFromApi() {
		
         //----get response from api---------------
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:4000/users");
	    
		//----parse response to JSON Object----------
		JSONParser parser = new JSONParser();
		Object obj;
		
		
		
		
		
		try {
			 obj = parser.parse(target.request(MediaType.TEXT_XML).get(String.class));
			 JSONArray array = (JSONArray)obj;
			 
			 
			
			 //-----Iterate through Json array and update ArryList-----------
			 for(int i = 0 ;i<array.size();++i) {
				 JSONObject obj2 = (JSONObject)array.get(i);
				Admin admin = new Admin();
				 
				admin.setUserName(obj2.get("username").toString());
				admin.setPassword(obj2.get("password").toString());
				adminList.add(admin);
				 
				 
			 }
			
			 
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//-----returns admin list-------------
	@Override
	public List<Admin> getAdmins() throws RemoteException {
		
		return adminList;
	}
	
	//-----returns sensor list-------------
	@Override
	public List<Sensor> getSensors() throws RemoteException {
		
		
		return sensorList ;
	}

	 //----------Edit the sensor details-----------------
	
	public void EditAPISensordetails(Sensor sensor) throws IOException {
		
        //-------check the relevent sensor in sensor list
		
		Sensor updated = new Sensor();
		for(Sensor s:sensorList) {
			
			if(s.getId().equals(sensor.getId())) {
				
			      updated.set_id(s.get_id());
			      updated.setId(sensor.getId());
			      updated.setFloorNo(sensor.getFloorNo());
			      updated.setRoomNo(sensor.getRoomNo());
			      updated.setCdLevel(s.cdLevel);
			      updated.setSmokeLevel(s.getSmokeLevel());
			}
		}
		
		final String EDIT_SENSOR = "{\n" +
	            "\"sensorid\":"+ updated.getId()+",\r\n" +
		        "    \"floor\":"+updated.getFloorNo()+",\r\n" +
		        "    \"colevel\":"+updated.getCdLevel()+",\r\n" +
		        "    \"smokelevel\":"+updated.getSmokeLevel()+",\r\n" +
		        "    \"room\":"+ updated.getRoomNo()+""+ 
		        "\n}";
		
		
		    System.out.println(updated.getRoomNo());
		    URL obj = new URL("http://localhost:4000/sensors/"+updated._id);
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(EDIT_SENSOR.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		            postConnection.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = in .readLine()) != null) {
		            response.append(inputLine);
		        } in .close();
		        // print result
		        System.out.println(response.toString());
		    } else {
		        System.out.println("POST NOT WORKED");
		    }
		
		
	}
	@Override
	public void EditSensor(Sensor sensor) throws RemoteException {
		
		try {
			EditAPISensordetails(sensor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
			
			
		}
		
		
		
	

	//----------Add new sensors-----------------
	
	
	
	@Override
	public void addSensor(Sensor s) {
		
		
		try {
			AddNeWSensorToApi(s);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    public void AddNeWSensorToApi(Sensor s) throws IOException {
    	
    	
    	final String NEW_SENSOR = "{\n" +
	            "\"sensorid\":"+ s.getId()+",\r\n" +
		        "    \"floor\":"+s.getFloorNo()+",\r\n" +
		        "    \"colevel\":"+0+",\r\n" +
		        "    \"smokelevel\":"+0+",\r\n" +
		        "    \"room\":"+ s.getRoomNo()+""+ 
		        "\n}";
		
		
		    System.out.println(NEW_SENSOR);
		    URL obj = new URL("http://localhost:4000/sensors");
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("userId", "a1bcdefgh");
		    postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(NEW_SENSOR.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		            postConnection.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = in .readLine()) != null) {
		            response.append(inputLine);
		        } in .close();
		        // print result
		        System.out.println(response.toString());
		    } else {
		        System.out.println("POST NOT WORKED");
		    }
		
	}

	//----------Send sms when carbondioxide or smoke level goes up-----------------
	public void sendSMS(int floor, String room, double smokeLevel, double cdLevel) throws RemoteException {
		
		    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	        Message.creator(
	        		//----My phone number----
	                new com.twilio.type.PhoneNumber("+94715443619"),
	                //---Purchased number from twilio-----
	                new com.twilio.type.PhoneNumber("+13343731533"),
	                "Warning!!\n"+"Smoke Level and carbon Dioxide level is increasing\n"+"Floor Number:"+floor+"\n"+"Room Number:"+room)
	            .create();
		
	}
	
	
	
	@Override
	public void run() {
		
		//Random r = new Random();
	
		for(;;) {
			
			try {
				
				Thread.sleep(15000);
			}
			catch(InterruptedException ie) {
				
			}
			
          
          	
          	try {
          		
          		
          		
          		
          		saveSensorsToList();
				notifyClients();
				
                   for(Sensor s:sensorList) {
          			
          			if(s.getSmokeLevel() >5 || s.getCdLevel() > 5) {
          				sendSMS(s.getFloorNo(),s.getRoomNo(),s.getSmokeLevel(),s.getCdLevel());
          				
          			}
          		}
				
			} catch (RemoteException e) {
				
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	private void notifyClients() throws RemoteException,AlreadyBoundException {
		
		
		for(FireAlarmClient c :clientList){
			c.getSensorDetails(sensorList);
			
		}
	}
	
	
	
	
	public static void main(String[] args) {

		   System.setProperty("java.security.policy", "file:allowall.policy");
	 

			System.out.println("Loading RMI server");
		
			
			
			
	        // Registering the server to the RMI registry
			try {
				FireAlarmSensorServer fsensor = new FireAlarmSensorServer();
				String registry = "localhost";

				String registration = "rmi://" + registry + "/FireAlarmSensor";

				
				Naming.rebind(registration, fsensor);
				
				Thread thread = new Thread(fsensor);
				fsensor.saveSensorsToList();
				fsensor.getAdminsFromApi();
				thread.start();
			} catch (RemoteException re) {
				System.err.println("Remote Error - " + re);
			} catch (Exception e) {
				System.err.println("Error - " + e);
			}

		}


	

	


	


	
}
