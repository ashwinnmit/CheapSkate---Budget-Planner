package application;

import dynamic.Expenselist; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Expenditure implements Initializable{
	
	HBox array[] = new HBox[10];
	
	static int no_of_expenses=0;
	
    @FXML
    private Button home;

    @FXML
    private Button submit;
    
    @FXML
    private Label amount;

    @FXML
    private Label date;
    
    @FXML
    private Label time; 
    
    @FXML
    private VBox Expenditurelist; 


    @FXML
    void homepage(ActionEvent event) throws Exception {
    	DBUtils.expenses();
    	Main m = new Main();
    	m.changeScene("Homepage.fxml");
    }

    @FXML
    void submitUpdate(ActionEvent event) throws Exception{
    	Main m = new Main();
    	DBUtils.cleartable();
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setContentText("Updated Expenses Successfully!");
    	alert.show();
    	m.changeScene("Homepage.fxml");
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		List<Expenselist> expenses = new ArrayList<>(DBUtils.expenselist());
		no_of_expenses = expenses.size();
		for(int i=0;i<expenses.size();i++) {
			FXMLLoader fxml = new FXMLLoader();
			fxml.setLocation(getClass().getResource("ExpenseTuple.fxml"));
			
			try {
				HBox hbox = fxml.load();
				ExpenseTuple e = fxml.getController();
				e.setData(expenses.get(i));
				array[i] = hbox;
				Expenditurelist.getChildren().add(hbox);
			}catch(Exception x) {
				x.printStackTrace();
			}
		}
	}
}