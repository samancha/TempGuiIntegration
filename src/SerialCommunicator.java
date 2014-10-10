
import jssc.*;
import java.util.Scanner;

import java.net.SocketPermission;

public class SerialCommunicator{
    //Declare Special Symbol Used in Serial Data Stream from Arduino
    final static String start_char = "@";
    final static String end_char = "#";
    final static String sep_char = ":";

    private final static String LED_MODE_MSG = "0*";
    private final static String TEMP_MSG = "1*";


    public static boolean switchOffError = false;
    public static boolean thirdBoxError = false;
    public static boolean unpluggedError = false;


    public SerialPort serialPort;

    protected PortListener portListener;


    public SerialCommunicator(){// MAY NEED TO THROW INTERRUPTED EXCEPTION!!!!!
        //Find and display available ports
        String[] portList = SerialPortList.getPortNames();
        System.out.print("Ports found: ");

        //debug
        for(int i = 0; i < portList.length; i++) {
            System.out.println(portList[i]);
            if (i != portList.length - 1)
                System.out.println(", ");
        }
        /*
        //Have user enter one of the listed ports for communication
        String chosenPort = null;
        while(chosenPort == null){
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter one of the available ports: ");
            chosenPort = reader.nextLine();
            String[] returnedPorts = SerialPortList.getPortNames(chosenPort);
            if (returnedPorts.length > 0)
                chosenPort = SerialPortList.getPortNames(chosenPort)[0];//check if the entered port is available
        } */
        //Set port
        String chosenPort = portList[0];
        serialPort = new SerialPort(chosenPort);
        System.out.println("Successfully chose port " + chosenPort);

        openSerialConnection();
    }//end constructor

    public void openSerialConnection() {
        try {
            //Open Serial Port
            System.out.println("Port opened: " + serialPort.openPort());
            serialPort.setFlowControlMode(serialPort.FLOWCONTROL_RTSCTS_IN | serialPort.FLOWCONTROL_RTSCTS_OUT);

            //Define Parameter -- can be found in Device Manager
            // baudRate, iataBits, stopBits, parity
            System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));

            portListener = new PortListener(serialPort);
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.addEventListener(portListener, mask);
        } catch (SerialPortException ex) {

            //Display Errors
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    public double getArduinoTemperature(){
        double temperature = 25;
         if(!portListener.waitingForTemp) {
             portListener.waitingForTemp = true;
             try {
                 serialPort.writeString("1*");
                 //serialPort.writeByte("*".getBytes()[0]);
                 System.out.println("Sent message");

                 //serialPort.writeString("\n");
             } catch (SerialPortException ex) {
                 System.out.println("Tried writing tmp_msg: " + ex);
                 ex.printStackTrace();
             }
         }
        //else if(portListener.tempIsRetrieved) {
          //  portListener.tempIsRetrieved = false;
            //portListener.waitingForTemp = false;
            temperature = portListener.currentTempReading;
        //}
        return temperature;
    }

    public void changeLED_Mode(){
        try{serialPort.writeBytes(LED_MODE_MSG.getBytes());}
        catch (SerialPortException ex){
            System.out.println("Tried writing led_mode_msg: " + ex);
            ex.printStackTrace();
        }
    }

    public void endSerialConnection(){
        try {
            System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println("Tried to close port. " + ex);
            ex.printStackTrace();
        }
    }
}