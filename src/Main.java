import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Steve on 9/20/2014.
 * Error branch
 */

public class Main {
    private static final Random randNum = new Random();
    public static double incomingVal;
    public static SerialCommunicator serialCommunicator;

    public static JFrame popUpWindow;
    public static JLabel errorLabel = new JLabel();

    static boolean closedPopUp = false;
    public static void main(String [] Args)
    {
        int loopCount = 10;
        //serialCommunicator = new SerialCommunicator();

        FrameContainer frameContainer = new FrameContainer();
        frameContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameContainer.setSize(980, 520);
        frameContainer.setMinimumSize(new Dimension(980,520));
        frameContainer.setLocation(200, 100);
        frameContainer.setVisible(true);
        //serialCommunicator.getArduinoTemperature();

        popUpWindow = new JFrame();
        popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popUpWindow.setSize(200, 200);
        popUpWindow.setMinimumSize(new Dimension(200,200));
        popUpWindow.setLocation(200, 100);

        //initArduino();


        while(true){
            if(loopCount >= 10){
                loopCount = 0;
                //serialCommunicator.portListener.waitingForTemp = false;
            }

            try{
                // TODOOOOOOOO: figure out here about closing the pop up window but it is not in the requirements
                if(!closedPopUp)
                    errorCheck();


                //if(serialCommunicator != null)
                    //incomingVal = serialCommunicator.getArduinoTemperature();
                incomingVal = randNum.nextInt(30)+15;
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
    public static void errorCheck(){
        if(SerialCommunicator.switchOffError || SerialCommunicator.thirdBoxError || SerialCommunicator.unpluggedError){
            TempChart.modeError = true;

            if(SerialCommunicator.switchOffError){
                errorLabel.setText("Switch off Error occured");
            }else if(SerialCommunicator.thirdBoxError){
                errorLabel.setText("Temp Button pressed");
            }else{
                errorLabel.setText("Continuous Button pressed");
            }

            popUpWindow.add(errorLabel);
            popUpWindow.setVisible(true);
        }
        else{
            popUpWindow.dispose();
        }
    }

    public static void initArduino(){
        serialCommunicator = new SerialCommunicator();
        serialCommunicator.getArduinoTemperature();

        try {
            Thread.sleep(50);//wait for arduino to be ready;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
