/**
 * Created by dillomotix on 10/1/14.
 */
import jssc.*;


// new shit
public class PortListener implements SerialPortEventListener{

    protected SerialPort serialPort;

    protected double currentTempReading = 25;
    protected boolean tempIsRetrieved;
    protected boolean waitingForTemp = false;

    private String receivedData = "";

    public PortListener(SerialPort serial){
        serialPort = serial;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR()) {
            try {
                serialPort.removeEventListener();
                System.out.println("Serial event");
                receivedData += serialPort.readString();
                System.out.println("recData: " + receivedData);
              /*  if(receivedData.length() == 0)
                    receivedData += serialPort.readString();
                else if(receivedData.substring(receivedData.length() - 1) == "*") {
                    System.out.println("recData: " + receivedData);
                    System.out.println("Temp in: " + Double.toString(currentTempReading = Double.parseDouble(receivedData.substring(0, receivedData.length() - 1))));
                    receivedData = "";
                    //handleSerialEvent(receivedData.substring(0, receivedData.length() - 1));
                }
                else{
                    System.out.println("recData: " + receivedData);
                    receivedData += serialPort.readString();
                }*/
                int i = 0;
                while (i < receivedData.length() ){
                    System.out.println("subString: " + receivedData.substring(i, i+1));
                    if(receivedData.charAt(i) == '*'){
                        System.out.println("length: " + Integer.toString(receivedData.length() - i));
                        System.out.println("Should be: " + receivedData.substring(0, i));
                        System.out.println("Temp in: " +
                                Double.toString(currentTempReading =
                                        Double.parseDouble(receivedData.substring(0, i))));
                        System.out.println("recData: " + receivedData);
                        receivedData = receivedData.substring(i+1);//may be null
                        //tempIsRetrieved = true;
                        waitingForTemp = false;
                    }

                    i++;
                }
                int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
                serialPort.addEventListener(this, mask);
            }
            catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
        else if(event.isCTS()){//If CTS line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("CTS - ON");
                try {
                    serialPort.openPort();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("CTS - OFF");
                try {
                    serialPort.closePort();
                    while(!serialPort.isOpened()){
                        ;
                    }

                } catch (SerialPortException ex) {
                    System.out.println("Tried to close port." + ex);
                }
            }
        }
        else if(event.isDSR()){///If DSR line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("DSR - ON");
            }
            else {
                System.out.println("DSR - OFF");
            }
        }
    }
    public  void handleSerialEvent(String value){

        tempIsRetrieved = true;
    }

}
