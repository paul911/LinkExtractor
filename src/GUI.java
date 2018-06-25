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
    private JButton browse;
    private JFileChooser chooser;

    GUI() {
        setTitle("Email Stream Link Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        script11 = "";
        script22 = "";
        script33 = "";
        script44 = "";
        script1 = new JRadioButton("Description cat1 cat2 href");
        script2 = new JRadioButton("Description href");
        script3 = new JRadioButton("Href description");
        script4 = new JRadioButton("Href description cat1 cat 2");
        final ButtonGroup scriptType = new ButtonGroup();
        scriptType.add(script1);
        scriptType.add(script2);
        scriptType.add(script3);
        scriptType.add(script4);
        script1.setSelected(true);
        JPanel panel = new JPanel();
        panel.add(new JLabel("First choose the type of script,"));
        panel.add(new JLabel("then click browse and select the template:"));
        panel.add(new JSeparator());
        panel.add(script1);
        panel.add(script2);
        panel.add(script3);
        panel.add(script4);
        panel.setLayout(new GridLayout(7, 1, 5, 10));
        browse = new JButton("Browse for template");
        browse.addActionListener((ActionEvent e) -> {
            chooser = new JFileChooser();
            chooser.setFileSelectionMode(0);
            chooser.setFileFilter(new TypeOfFile());
            System.out.println("working?");
            chooser.showOpenDialog(this);
            /*if (returnVal == 0) {
                this.dir = this.chooser.getCurrentDirectory() + "\\" + this.chooser.getSelectedFile().getName();
                this.textPaneAppend("Scanning file: " + this.dir + "\n\n", 2);
                this.listFilenames();
                this.checkFiles();
                this.revisePaths();
                this.displayNumissues();*/
        });
        add(panel, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(browse, BorderLayout.SOUTH);
        browse.setPreferredSize(new Dimension(0, 100));
        setPreferredSize(new Dimension(300, 400));
        setResizable(false);
        //        start.setPreferredSize(new Dimension(0, 50));
        setVisible(true);
        pack();
    }

}
