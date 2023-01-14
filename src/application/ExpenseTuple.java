package application;

import java.net.URL;
import java.util.ResourceBundle;

import dynamic.Expenselist;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ExpenseTuple implements Initializable{
	
    @FXML
    private Label amount;

    @FXML
    private ComboBox <String> choice;

    @FXML
    private Label date;
    
    @FXML
    private Label time;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		choice.setItems(FXCollections.observableArrayList("Food","Transportation","Entertainment","Shopping","Miscellaneous"));
	}
	
	public void setData(Expenselist el) {
		double amt;
		amt = el.getamount();
		String amt2= Double.toString(amt); 
		date.setText(el.getdate());
		time.setText(el.gettime());
		amount.setText(amt2);
	}


}
