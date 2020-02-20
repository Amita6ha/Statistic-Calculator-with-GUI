/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

/**
 * Controller class for the main stage
 * @author Simon
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    
    /* The ArrayList stores the data imported from file. */
    private static ArrayList<Double> rawData = new ArrayList<>();
    
    /* The method is to be called in class ChartController for
    ** converting data from ArrayList to array which JFreeChart 
    ** accepts.
    */
    public static double[] getData(){
        
        double[] data = new double[rawData.size()];
        for(int i = 0;i < rawData.size();++i){
            data[i] = rawData.get(i);
        }
        return data;
        
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ImportButton"
    private Button ImportButton; // Value injected by FXMLLoader
    
    @FXML
    void ImportButtonClicked(ActionEvent event) throws IOException{
        
        /* Create a window for importing a file. */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT (*.txt)","*.txt"));//set limitation of input to txt files
        File filePath = fileChooser.showOpenDialog(new Stage());
        
        /* Stream from the file. */
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
                
        rawData.clear();//clear the data in the container before reading the file
        String data;
         while((data = bufferedReader.readLine()) != null){
            rawData.add(Double.valueOf(data));
        }
                
        bufferedReader.close();
        
        /* Once file streaming is successful, set the window for demonstrating the chart. */
        StackPane chartLayout = new StackPane();
        chartLayout.getChildren().add(FXMLLoader.load(getClass().getResource("ChartGUI.fxml")));
        Scene chartScene = new Scene(chartLayout,900,600);
        Stage chartWindow = new Stage();
        chartWindow.setScene(chartScene);
        chartWindow.setTitle("Chart");
        chartWindow.show();
                
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ImportButton != null : "fx:id=\"ImportButton\" was not injected: check your FXML file 'MainGUI.fxml'.";

    }
}