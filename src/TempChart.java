import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Steve on 9/21/2014.
 */
public class TempChart extends JPanel{

    //private static final Random randNum = new Random();
    private XYSeriesCollection dataCollection;
    private JFreeChart chart;
    ChartPanel chartPanel;
    public XYPlot plot;
    private XYSeries xyData;
    private Double [] temperaturesArray = new Double[300];

    boolean mode60= true;


    public TempChart(){
        System.out.println("In Constructor!");
        chart = createGraph();
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    public JFreeChart createGraph(){

        chart = ChartFactory.createXYLineChart("Temperature vs Time Graph","Elapsed Time (s)","Temperature (°C)",dataCollection);
        chart.removeLegend();

        Font axisTitlesFont = new Font("Dialog", Font.PLAIN, 30);
        Font axisNumFont = new Font("Dialog", Font.PLAIN, 25);

        plot = (XYPlot) chart.getPlot();
        plot.getDomainAxis().setInverted(true);
        plot.getDomainAxis().setLabelFont(axisTitlesFont);
        plot.getDomainAxis().setTickLabelFont(axisNumFont);
        if(mode60)
            plot.getDomainAxis().setRange(0,60);
        else
            plot.getDomainAxis().setRange(0,300);
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setLabelFont(axisTitlesFont);
        yAxis.setTickLabelFont(axisNumFont);
        yAxis.setRange(10, 50);
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    public void createDataSet(double newTempVal){    // add dataset as a parameter input
        xyData = new XYSeries("Graph Data");

        // Todo: make if statement saying if 60

        for(int i= temperaturesArray.length - 1; i >= 0 ; i--){
            if(i != 0){
                temperaturesArray[i] = temperaturesArray[i-1];
            }else if (i == 0){
                temperaturesArray[i] = newTempVal;
            }
            xyData.add(i,temperaturesArray[i]);
        }

        dataCollection = new XYSeriesCollection(xyData);

    }

    public void updateGraph(){
        remove(chartPanel);
        chartPanel = new ChartPanel(createGraph());
    }

    public void addChartPanel(){
        add(chartPanel);
    }

    public void updatePanel(double newValue){
        createDataSet(newValue);
        updateGraph();
        addChartPanel();
        validate();
    }
}
