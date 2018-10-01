import org.apache.commons.codec.binary.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
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
    private JTextArea output;   // links output text area
    private JTextArea url;  // input url text area

    private LinkExtractor() {

        setTitle("Email Stream Link Extractor v2.2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initializing radio buttons (order of link attributes chooser)
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

        // creates two textfields, one for the output, and one for the input of an url address
        // added focus listener to the input box, so that everytime the box is selected the content is cleared
        // and the default message is shown if box is left empty
        output = new JTextArea();
        output.setEditable(true);
        url = new JTextArea();
        url.setEditable(true);
        url.setToolTipText("Insert URL here");
        url.setText("\n Browse for local template or insert URL link here...");
        url.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ((JTextComponent) e.getSource()).setText("");
                url.setBackground(Color.white);
                url.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (((JTextComponent) e.getSource()).getText().isEmpty())
                    ((JTextComponent) e.getSource()).setText("\n Browse for local template or insert URL link here...");
            }
        });

        JButton browse = new JButton("Get Links");
        browse.setPreferredSize(new Dimension(50, 50));
        browse.addActionListener((ActionEvent e) -> {
            // display an error and stop process if the URL inserted is invalid
            if ((!url.getText().contains("http") || url.getText().contains(" ")) && !url.getText().contains("Browse for local template")) {
                url.setText("\n   INVALID URL  !!!");
                url.setBackground(Color.RED);
                return;
            }
            // if the url input box contains the default message, clicking the button will open a dialog
            // to select a .html template from the local drive
            if (url.getText().contains("Browse for local template")) {
                output.setText("");
                try {
                    chooser = new JFileChooser("D:/");
                    chooser.setFileSelectionMode(0);
                    chooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".html");
                        }

                        @Override
                        public String getDescription() {
                            return ".html files";
                        }
                    });
                    chooser.showOpenDialog(this);
                    template = chooser.getSelectedFile(); }
                catch (Exception ex) {
                    output.append("No file was chosen.");
                }
            }
            try {
                // runs the links extracting algorithm, no matter if a local template file was choosen or not
                getLinks();
            } catch (IOException error) {
                output.append("Error encountered: " + error.getMessage());
            }
        });

        // interface setup
        JPanel radioButtons = new JPanel();
        radioButtons.setLayout(new GridLayout(1, 4, 0, 0));
        radioButtons.add(script1);
        radioButtons.add(script2);
        radioButtons.add(script3);
        radioButtons.add(script4);
        JPanel inputButton = new JPanel();
        inputButton.setLayout(new GridLayout(1, 2, 0, 0));
        inputButton.add(url);
        inputButton.add(browse);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 5, 0, 0));
        mainPanel.add(radioButtons);
        mainPanel.add(inputButton);
        add(mainPanel, BorderLayout.PAGE_START);
        add(new JScrollPane(output), BorderLayout.CENTER);
        add(new JLabel(("Programmed by Paul Tanasa for the Optaros Email Team")), BorderLayout.AFTER_LAST_LINE);

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
        output.setText("");

        // if a html template files was used, links will be extracted from that one
        if (url.getText().isEmpty() || url.getText().contains("Browse for local template"))
            links = Jsoup.parse(template, "UTF-8").select("a[href]");

        else {
            // in case a login is required to view the url
            // example, login is required for viewing the NOPVN AEM emails
            String username = "";  // add username to login to the AEM page
            String password = "";  // add password to login to the AEM page
            String login = username + ":" + password;
            String base64login = new String(Base64.encodeBase64(login.getBytes()));

            // if not, links will be extracted from the provided url (will also use the AEM login if needed)
            links = Jsoup.connect(url.getText()).header("Authorization", "Basic " + base64login)
                    .get().select("a[href]");
        }
        // gets the link attributes in the right order chosen by the radio buttons
        if (script1.isSelected()) {
            getFirst(links);
        } else if (script2.isSelected())
            getSecond(links);
        else if (script3.isSelected())
            getThird(links);
        else getFourth(links);
        finish();
    }

    // methods for extracting links according to the order of attributes chosen using the radio buttons
    // Description, cat1, cat2, Href
    private void getFirst(Elements links) {
        for (Element link : links) {
            if (!link.attr("href").equals("#"))
                output.append("description=\"" + link.attr("description") + "\" CAT1=\"" + link.attr("CAT1")
                        + "\" CAT2=\"" + link.attr("CAT2") + "\" " + link.attr("href") + "\n");
        }
    }

    // description, href
    private void getSecond(Elements links) {
        for (Element link : links) {
            if (!link.attr("href").equals("#"))
                output.append("description=\"" + link.attr("description") + "\" " + link.attr("href") + "\n");
        }
    }

    // href, description
    private void getThird(Elements links) {
        for (Element link : links) {
            if (!link.attr("href").equals("#"))
                output.append(link.attr("href") + " description=\"" + link.attr("description") + "\"\n");
        }
    }

    // href, description, cat1, cat2
    private void getFourth(Elements links) {
        for (Element link : links) {
            if (!link.attr("href").equals("#"))
                output.append(link.attr("href") + " description=\"" + link.attr("description") +
                        "\" CAT1=\"" + link.attr("CAT1") + "\" CAT2=\"" + link.attr("CAT2") + "\"\n");
        }
    }

    private void finish() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(output.getText()), null);
        JOptionPane.showMessageDialog(this, "Lniks have been extracted and copied to the clipboard",
                "Success!", JOptionPane.INFORMATION_MESSAGE);
    }
}

