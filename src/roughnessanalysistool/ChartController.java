/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

/**
 * Controller class for the chart demonstration stage
 * @author Simon
 */
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartController {

    /* Call the method from class ChartController to convert data
    ** from ArrayList to array which JFreeChart accepts.
    */
    private double[] data = MainController.getData();
    
    /* The method contains a series of operations for creating
    ** the chart and will be called in the change listener.
    */
    void getChart(HistogramDataset histogramDataset){
        
        /* Calculate the fitting parameters. */
        WeightedObservedPoints obs = new WeightedObservedPoints();
        for(int i=0;i<histogramDataset.getItemCount(0);++i){
            obs.add((double)histogramDataset.getX(0,i),(double)histogramDataset.getY(0,i));
        }
        double[] parameters = GaussianCurveFitter.create().fit(obs.toList());
        
        /* Sample the fitted curve. */
        XYSeries series=new XYSeries("");
        double temp = (double)histogramDataset.getX(0,histogramDataset.getItemCount(0)-1)-(double)histogramDataset.getX(0,0);
        for(double x = -temp;x < temp;x = x+temp/100000){
            double y = parameters[0]*exp(-0.5*pow(((x-parameters[1])/parameters[2]),2.));
            series.add(x,y);
        }
        XYDataset fittedDataset=new XYSeriesCollection(series);
        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        
        /* Set the properties of the chart. */
        JFreeChart chart = ChartFactory.createHistogram(null,"δ/um","frenquency",histogramDataset);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setDataset(1,fittedDataset);
        plot.setRenderer(1, renderer2);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        chart = new JFreeChart(null,JFreeChart.DEFAULT_TITLE_FONT,plot,true);
        
        ChartViewer viewer = new ChartViewer(chart);//plot the chart
        
        /* Update the GUI containers. */
        ChartArea.getChildren().add(viewer);
        alpha.setText(String.valueOf(parameters[0]));
        mu.setText(String.valueOf(parameters[1]));
        sigma.setText(String.valueOf(parameters[2]));
        
    }
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="FileExport"
    private MenuItem FileExport; // Value injected by FXMLLoader

    @FXML // fx:id="MeanValue"
    private TextArea MeanValue; // Value injected by FXMLLoader

    @FXML // fx:id="VarianceValue"
    private TextArea VarianceValue; // Value injected by FXMLLoader

    @FXML // fx:id="MedianValue"
    private TextArea MedianValue; // Value injected by FXMLLoader

    @FXML // fx:id="StandardDeviationValue"
    private TextArea StandardDeviationValue; // Value injected by FXMLLoader

    @FXML // fx:id="alpha"
    private TextArea alpha; // Value injected by FXMLLoader

    @FXML // fx:id="mu"
    private TextArea mu; // Value injected by FXMLLoader

    @FXML // fx:id="sigma"
    private TextArea sigma; // Value injected by FXMLLoader

    @FXML // fx:id="Option"
    private ComboBox<String> Option; // Value injected by FXMLLoader

    @FXML // fx:id="ChartArea"
    private StackPane ChartArea; // Value injected by FXMLLoader

    @FXML
    void MenuItemExportClicked(ActionEvent event) throws IOException {
        
        /* Create a window for exporting a file. */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export...");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("PNG (*.png)","*.png") );//set limitation of output to png files
        File filePath = fileChooser.showSaveDialog(new Stage());
        
        /* Stream to the file. */
        StackPane temp = ChartArea;
        WritableImage writableImage = new WritableImage((int)temp.getWidth(),(int)temp.getHeight());
        temp.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        ImageIO.write(renderedImage,"png",filePath);
        
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert FileExport != null : "fx:id=\"FileExport\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert MeanValue != null : "fx:id=\"MeanValue\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert VarianceValue != null : "fx:id=\"VarianceValue\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert MedianValue != null : "fx:id=\"MedianValue\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert StandardDeviationValue != null : "fx:id=\"StandardDeviationValue\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert alpha != null : "fx:id=\"alpha\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert mu != null : "fx:id=\"mu\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert sigma != null : "fx:id=\"sigma\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert Option != null : "fx:id=\"Option\" was not injected: check your FXML file 'ChartGUI.fxml'.";
        assert ChartArea != null : "fx:id=\"ChartArea\" was not injected: check your FXML file 'ChartGUI.fxml'.";

        /* After successfully loading the file in the main stage
        ** calculate the mean, variance, median and standard deviation
        */
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0;i < data.length;++i){
            stats.addValue(data[i]);
        }
        /* Update the GUI containers. */
        MeanValue.setText(String.valueOf(stats.getMean()));
        VarianceValue.setText(String.valueOf(stats.getVariance()));
        MedianValue.setText(String.valueOf(stats.getPercentile(50)));
        StandardDeviationValue.setText(String.valueOf(stats.getStandardDeviation()));
        
        /* Set the ComboBox and the change listener. */
        BinOption.Option[] option = BinOption.Option.class.getEnumConstants();
        ObservableList optionList = FXCollections.observableArrayList(option[0].toString());
        Option.setItems(optionList);
        for(int i = 1;i < option.length;i++){
            Option.getItems().addAll(option[i].toString());
        }
        Option.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, 
                                String newValue){
                if(newValue.equals("SquareRoot")){
                    ChartArea.getChildren().clear();//clear the chart
                    
                    /* Set properties of the histogram. */
                    HistogramDataset histogramDataset = new HistogramDataset();
                    histogramDataset.addSeries("",data,new SquareRoot(data).NoOfBin());//choose the way to define number of bin
                    histogramDataset.setType(HistogramType.SCALE_AREA_TO_1);//normalised histogram
                    
                    getChart(histogramDataset);
                }
                /* similar syntax as above */
                if(newValue.equals("SturgeFormula")){
                    ChartArea.getChildren().clear();
                    
                    HistogramDataset histogramDataset = new HistogramDataset();
                    histogramDataset.addSeries("",data,new SturgeFormula(data).NoOfBin());
                    histogramDataset.setType(HistogramType.SCALE_AREA_TO_1);
                    
                    getChart(histogramDataset);
                }
                if(newValue.equals("RiceRule")){
                    ChartArea.getChildren().clear();
                    
                    HistogramDataset histogramDataset = new HistogramDataset();
                    histogramDataset.addSeries("",data,new RiceRule(data).NoOfBin());
                    histogramDataset.setType(HistogramType.SCALE_AREA_TO_1);
                    
                    getChart(histogramDataset);
                }
            }
        });
        
    }
}