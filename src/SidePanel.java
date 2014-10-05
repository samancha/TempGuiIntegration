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
        setLayout(new GridLayout(1, 1, 30, 30));

        inZoomButton = new JButton("Zoom +");
        inZoomButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        outZoomButton = new JButton("Zoom -");
        outZoomButton.setFont(new Font("Dialog", Font.PLAIN, 20));

        tempButton = new JButton("°C/°F");
        tempButton.setFont(new Font("Dialog", Font.PLAIN, 20));

        String unitVar = "°C";
        temperatureLabel = new JLabel(Double.toString(Main.incomingVal) + unitVar);
        temperatureLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        graphTypeButton = new JButton("Continuous/Not");
        graphTypeButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        String varString = "Continuous";
        graphTypeLabel = new JLabel(varString);
        graphTypeLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        graphTypeButton.setFont(new Font("Dialog", Font.PLAIN, 20));

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
