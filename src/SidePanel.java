import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by Steve Amancha on 9/20/2014.
 */
public class SidePanel extends JPanel {

    public static JButton inZoomButton, outZoomButton,tempButton, graphTypeButton;
    public static JLabel temperatureLabel, graphTypeLabel;

    public boolean inCelcius = true;
    public boolean bContinuous = true;


    public SidePanel(){
        setLayout(new GridLayout(0, 1, 30, 30));
        ButtonGroup buttonGroup = new ButtonGroup();

        inZoomButton = new JButton("Zoom +");
        outZoomButton = new JButton("Zoom -");

        tempButton = new JButton("°C/°F");

        String unitVar = "°C";
        temperatureLabel = new JLabel(Double.toString(Main.incomingVal) + unitVar);
        //temperatureLabel.setSize(20,20); TODO: Possibly adjust incomingVal size
        graphTypeButton = new JButton("Continuous/Not");
        String varString = "Continuous";
        graphTypeLabel = new JLabel(varString);

        JPanel zoomPanel = new JPanel();
        zoomPanel.add(inZoomButton);
        zoomPanel.add(outZoomButton);

        JPanel tempPanel = new JPanel();
        tempPanel.add(tempButton);
        tempPanel.add(temperatureLabel);

        JPanel graphPanel = new JPanel();
        graphPanel.add(graphTypeButton);
        graphPanel.add(graphTypeLabel);

        JPanel finalP = new JPanel();
        finalP.add(zoomPanel);
        finalP.add(tempPanel);
        finalP.add(graphPanel);

        add(finalP);
    }
}
