package exceptions;

import java.io.FileNotFoundException;

public class FileDoesntExist extends FileNotFoundException {
    public FileDoesntExist() {
        super("File not Found\n");
    }
}
