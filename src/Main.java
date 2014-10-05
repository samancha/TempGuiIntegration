import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Steve on 9/20/2014.
 */



public class Main {
    private static final Random randNum = new Random();
    public static double incomingVal;
    public static SerialCommunicator serialCommunicator;
    public static void main(String [] Args)
    {
        int loopCount = 10;
        //serialCommunicator = new SerialCommunicator();

        FrameContainer frameContainer = new FrameContainer();
        frameContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameContainer.setSize(720, 520);
        frameContainer.setMinimumSize(new Dimension(720,520));
        frameContainer.setLocation(200, 100);
        frameContainer.setVisible(true);
        serialCommunicator.getArduinoTemperature();
        try {
            Thread.sleep(50);//wait for arduino to be ready;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true){
            if(loopCount >= 10){
                loopCount = 0;
                serialCommunicator.portListener.waitingForTemp = false;
            }

            try{
                System.out.println("main loop");
                if(serialCommunicator != null)
                    incomingVal = serialCommunicator.getArduinoTemperature();
                //incomingVal = randNum.nextInt(30)+15;
                frameContainer.tempChart.updatePanel(incomingVal);
                frameContainer.incomingVal = incomingVal;
                frameContainer.printTemperature();
                loopCount++;
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
