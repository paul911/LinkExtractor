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
    private JTextArea url;

    private LinkExtractor() {

        setTitle("Email Stream Link Extractor v2.0");
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

        JPanel radioButtons = new JPanel();
        radioButtons.setLayout(new GridLayout(1, 4, 0, 0));
        JPanel inputButton = new JPanel();
        inputButton.setLayout(new GridLayout(1, 2, 0, 0));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 5, 0, 0));

        output = new JTextArea();
        output.setEditable(true);
        output.setText("\nChoose the order of the output elements, and insert URL in the empty orange field above, or" +
                " leave " +
                "empty to browse for a local file...");
        url = new JTextArea();
        url.setEditable(true);
        url.setBackground(Color.ORANGE);
        url.setToolTipText("Insert URL here");



        JButton browse = new JButton("Get Links");
        browse.setPreferredSize(new Dimension(50, 50));
        browse.addActionListener((ActionEvent e) -> {
            if (url.getText().isEmpty()) {
                chooser = new JFileChooser("D:/");
                chooser.setFileSelectionMode(0);
                chooser.setFileFilter(new TypeOfFile());
                System.out.println("working?");
                chooser.showOpenDialog(this);
                template = chooser.getSelectedFile();
            }
            output.setText("");
            try {
                getLinks();
            } catch (IOException error) {
                output.append("Error encountered: " + error.getMessage());
            }
        });


        inputButton.add(url);
        inputButton.add(browse);
        radioButtons.add(script1);
        radioButtons.add(script2);
        radioButtons.add(script3);
        radioButtons.add(script4);
        mainPanel.add(radioButtons);
        mainPanel.add(inputButton);
        add(mainPanel, BorderLayout.PAGE_START);
        add(new JScrollPane(output), BorderLayout.CENTER);
        add(new JLabel(("Programmed by Paul Tanasa for the Optaros Email Team")), BorderLayout.PAGE_END);


        setPreferredSize(new Dimension(800, 800));
        setResizable(true);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {

        new LinkExtractor();

    }

    private void getLinks() throws IOException {
        Elements links;

        if (!url.getText().isEmpty())
            links = Jsoup.connect(url.getText()).get().select("a[href]");
        else
            links = Jsoup.parse(template, "UTF-8").select("a[href]");

        if (script1.isSelected()) {
            getFirst(links);
        }
        else if (script2.isSelected())
            getSecond(links);
        else if (script3.isSelected())
            getThird(links);
        else getFourth(links);
    }

    private void getFirst(Elements links) {
        for (Element link : links) {
            output.append("description=\"" + link.attr("description") + "\" CAT1=\"" + link.attr("CAT1")
                    + "\" CAT2=\"" + link.attr("CAT2") + "\" " + link.attr("href") + "\n");
        }
    }

    private void getSecond(Elements links) {
        for (Element link : links) {
            output.append("description=\"" + link.attr("description") + "\" " + link.attr("href") + "\n");
        }
    }

    private void getThird(Elements links) {
        for (Element link : links) {
            output.append(link.attr("href") + " description=\"" + link.attr("description") + "\"\n");
        }
    }

    private void getFourth(Elements links) {

        for (Element link : links) {
            output.append(link.attr("href") + " description=\"" + link.attr("description") +
                    "\" CAT1=\"" + link.attr("CAT1") + "\" CAT2=\"" + link.attr("CAT2") + "\"\n");
        }
    }
}

