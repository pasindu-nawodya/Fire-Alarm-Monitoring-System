package SensorApp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.sf.json.JSONSerializer;

public class sensorApp implements  Runnable{
	
	static int num;
	static ArrayList<Sensor> sensorList = new ArrayList<>() ;
	@Override
	public void run() {
		
		//---generate random number-----
		Random r = new Random();
		
		 
		 for(;;) {
			
			try {
				
				Thread.sleep(1000);
			}
			catch(InterruptedException ie) {
				
			}
			
			 this.updateArrayList();
			 num = r.nextInt();
          	
          	 for(Sensor s:sensorList) {
          		 
          		 double smoke = s.getSmokeLevel();
          		 double cd = s.getCdLevel();
          		 
          		 //-----if generated number > 0, smoke level and cd level of all the sensors will decrease by 0.2---
          		 if(num > 0) {
          			 
          			 smoke = smoke - 0.5;
          			 cd = cd - 0.5;
          			 
          			 if(smoke > 0 && cd >0) {
          				 
          				 s.setCdLevel(cd);
          				 s.setSmokeLevel(smoke);
          			 }
          		 }else if(num < 0) {
          			 
          	   //-----if generated number > 0, smoke level and cd level of all the sensors will increase by 0.2
          			smoke = smoke + 0.5;
         			 cd = cd + 0.5;
         			 
         			 s.setCdLevel(cd);
     				 s.setSmokeLevel(smoke);
          		 }
          		 
          		
          	 }
          	
          	try {
          		
				this.updateAPI(sensorList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
		}
	}
	
	
	
	public static void main(String[] args) throws ParseException, JsonProcessingException, IOException {
		
	
		sensorApp sp = new sensorApp();
		Thread thread = new Thread(sp);
		
		thread.start();
       
	}
	
	//----this method updates the API with the changes of the gas levels------
	public void updateAPI(ArrayList<Sensor> sensorList) throws IOException {
		
		
		for(Sensor s:sensorList) {
			
		final String REQ_BODY = "{\n" +
	            "\"sensorid\":" +s.id+",\r\n" +
		        "    \"floor\":"+s.floorNo+",\r\n" +
		        "    \"colevel\":"+ s.cdLevel+",\r\n" +
		        "    \"smokelevel\":"+ s.smokeLevel+",\r\n" +
		        "    \"room\":"+s.RoomNo+"" + 
		        "\n}";
		
		
		   
		    URL obj = new URL("http://localhost:4000/sensors/"+s._id);
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(REQ_BODY.getBytes());
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
		        System.out.println(" ");
		    }
		}
		   
	}
	
	//-----This method updates the arraylist according to the changes of the database------
	public void updateArrayList() {
		
		
		        //----get response from api---------------
		
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target("http://localhost:4000/sensors");
			    
				//----parse response to JSON Object----------
				JSONParser parser = new JSONParser();
				Object obj;
				
				
				
				
				
				try {
					 obj = parser.parse(target.request(MediaType.TEXT_XML).get(String.class));
					 JSONArray array = (JSONArray)obj;
					
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
						 
						 sensorList.add(sensor);
						 
						
						 
						 
					 }
					 
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	

}
