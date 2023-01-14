package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class login {
	
    @FXML
    private Button button;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label wronglogin;
    
    @FXML
    private Button signup;
    
    @FXML
    private ImageView image;
    
    @FXML
    private ProgressBar progressbar;
	
    public login(){
    	
    }
    
    @FXML
    void userLogin(ActionEvent event) throws Exception {
    	checkLogin();
    }
    
    @FXML
    void userSignup(ActionEvent event) throws Exception{
    	Main m = new Main();
    	m.changeScene("Signup.fxml");
    }
    
    private void checkLogin() throws Exception{
    	String uname;
    	String pass;
    	int f=0;
    	if(username.getText().isEmpty()&&password.getText().isEmpty()) {
    		wronglogin.setText("Enter the required information");
    		return;
    	}
    	uname=username.getText().toString();
    	pass=password.getText().toString();
    	f = DBUtils.Checkinfo(uname, pass);
    	if(f==1) {
    		wronglogin.setText("Username does not exist");
    		return;
    	}
    	if(f==2) {
    		wronglogin.setText("Incorrect Password!");
    		return;
    	}   	
    }
}