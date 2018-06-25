//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import javax.swing.filechooser.FileFilter;

class TypeOfFile extends FileFilter {
    TypeOfFile() {
    }

    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".html");
    }

    public String getDescription() {
        return ".html files";
    }
}
