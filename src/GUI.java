import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

    private JRadioButton script1;
    private JRadioButton script2;
    private JRadioButton script3;
    private JRadioButton script4;
    private String script11;
    private String script22;
    private String script33;
    private String script44;
    private JButton extract;
    private JButton browse;

    GUI() {
        setTitle("Email Stream Link Extractor");
        script11 = "";
        script22 = "";
        script33 = "";
        script44 = "";
        script1 = new JRadioButton("first");
        script2 = new JRadioButton("second");
        script3 = new JRadioButton("third");
        script4 = new JRadioButton("fourth");
        final ButtonGroup scriptType = new ButtonGroup();
        scriptType.add(script1);
        scriptType.add(script2);
        scriptType.add(script3);
        scriptType.add(script4);
        script1.setSelected(true);
        JPanel panel = new JPanel();
        panel.add(script1);
        panel.add(script2);
        panel.add(script3);
        panel.add(script4);
        setLayout(new GridLayout(3, 4, 0, 0));
        browse = new JButton("Browse for template");
        browse.addActionListener((ActionEvent e) -> {});
        extract = new JButton("Extract links");
        extract.addActionListener((ActionEvent e) -> {});
        add(browse, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(extract, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(700, 800));
        //        start.setPreferredSize(new Dimension(0, 50));
        setVisible(true);
        pack();
    }

}
