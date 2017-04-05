import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * GUI类别
 * Created by ericwyn on 17-4-5.
 */
public class MainGUI extends JFrame {
    public MainGUI(){
        setSize(MainComponent.DEFAULT_WIDTH,MainComponent.DEFAULT_HEIGHT);
        add(new MainComponent());

    }
}

class MainComponent extends JComponent{
    public static final int DEFAULT_WIDTH=800;
    public static final int DEFAULT_HEIGHT=250;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
