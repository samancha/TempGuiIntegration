import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.Locale;

/**
 * Created by Steve on 9/20/2014.
 */
public class FrameContainer extends JFrame {

    public TempChart tempChart;
    public SidePanel sidePanel;

    public double incomingVal;

    public FrameContainer(){
        super("Temperature GUI");

        sidePanel = new SidePanel();
        tempChart = new TempChart();
        tempChart.validate();

        //setLayout(new FlowLayout());
        //add(tempChart);
        //add(sidePanel);

        setLayout(new BorderLayout());
        add(tempChart, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.SOUTH);
        pack();

        sidePanel.inZoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempChart.mode60 = true;
            }
        });
        sidePanel.outZoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempChart.mode60 = false;
            }
        });
        sidePanel.tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sidePanel.inCelcius = !sidePanel.inCelcius;

                // Testing error mode
                SerialCommunicator.thirdBoxError = !SerialCommunicator.thirdBoxError;
                printTemperature();
            }
        });
        sidePanel.graphTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sidePanel.bContinuous = !sidePanel.bContinuous;

                // Testing error mode
                tempChart.modeError = !tempChart.modeError;
                SerialCommunicator.unpluggedError = !SerialCommunicator.unpluggedError;
                printGraphType();
            }
        });
    }

    public void printTemperature(){
        if(sidePanel.inCelcius)
            sidePanel.temperatureLabel.setText(Double.toString(incomingVal)+"°C");
        else
            sidePanel.temperatureLabel.setText(Double.toString((incomingVal*9.0 / 5.0) +32.0) + " °F");

        if(tempChart.modeError)
            sidePanel.temperatureLabel.setText("Error!");

    }

    public void printGraphType(){
        if(!sidePanel.bContinuous)
            sidePanel.graphTypeLabel.setText("Continuous");
        else
            sidePanel.graphTypeLabel.setText("Not Continuous");
    }
}


























