import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author user
 */

public class Admin implements Serializable {
	
	String userName;
	String Password;

   public Admin() {
        //To change body of generated methods, choose Tools | Templates.
    }
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		this.Password = password;
	}


	public Admin(String userName, String password) {
		super();
		this.userName = userName;
		this.Password = password;
	}
	
	
	

}

