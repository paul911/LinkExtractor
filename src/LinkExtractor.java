import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


public class LinkExtractor extends JFrame {

    private JRadioButton script1;
    private JRadioButton script2;
    private JRadioButton script3;
    private JFileChooser chooser;
    private File template;
    private JTextArea output;

    private LinkExtractor() {

        setTitle("Email Stream Link Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        script1 = new JRadioButton("Desc cat1 cat2 Href");
        script2 = new JRadioButton("Desc Href");
        script3 = new JRadioButton("Href Desc");
        JRadioButton script4 = new JRadioButton("Href Desc cat1 cat2");
        final ButtonGroup scriptType = new ButtonGroup();
        scriptType.add(script1);
        scriptType.add(script2);
        scriptType.add(script3);
        scriptType.add(script4);
        script1.setSelected(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5,0 , 0));
        output = new JTextArea();
        output.setEditable(true);
        JButton browse = new JButton("Browse for template");
        browse.addActionListener((ActionEvent e) -> {
            chooser = new JFileChooser("D:/");
            chooser.setFileSelectionMode(0);
            chooser.setFileFilter(new TypeOfFile());
            System.out.println("working?");
            chooser.showOpenDialog(this);
            template = chooser.getSelectedFile();
            try {
                if (script1.isSelected())
                    getFirst(template);
                else if (script2.isSelected())
                    getSecond(template);
                else if (script3.isSelected())
                    getThird(template);
                else getFourth(template);
            } catch (IOException error) {
                System.out.println("Error encountered: " + error.getMessage());
            }
        });

        panel.add(script1);
        panel.add(script2);
        panel.add(script3);
        panel.add(script4);
        panel.add(browse);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        browse.setPreferredSize(new Dimension(0, 50));
        setPreferredSize(new Dimension(800, 800));
        setResizable(true);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {

        new LinkExtractor();

    }


    private void getFirst(File html) throws IOException {
        Elements links = Jsoup.parse(html, "UTF-8").getElementsByTag("a");
        for (Element link : links) {
            output.append("description=" + link.attr("description") + "CAT1=" + link.attr("CAT1")
                    + "CAT2=" + link.attr("CAT2") + "href=" + link.attr("href") + "\n");
        }
    }

    private void getSecond(File html) throws IOException {
        Elements links = Jsoup.parse(html, "UTF-8").getElementsByTag("a");
        for (Element link : links) {
            System.out.println("description=" + link.attr("description") + "CAT1=" + link.attr("CAT1")
                    + "CAT2=" + link.attr("CAT2") + "href=" + link.attr("href"));
        }
    }

    private void getThird(File html) throws IOException {
        Elements links = Jsoup.parse(html, "UTF-8").getElementsByTag("a");
        for (Element link : links) {
            System.out.println("description=" + link.attr("description") + "CAT1=" + link.attr("CAT1")
                    + "CAT2=" + link.attr("CAT2") + "href=" + link.attr("href"));
        }
    }

    private void getFourth(File html) throws IOException {
        Elements links = Jsoup.parse(html, "UTF-8").getElementsByTag("a");
        for (Element link : links) {
            System.out.println("description=" + link.attr("description") + "CAT1=" + link.attr("CAT1")
                    + "CAT2=" + link.attr("CAT2") + "href=" + link.attr("href"));
        }
    }
}

