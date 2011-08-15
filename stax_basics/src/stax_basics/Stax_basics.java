package stax_basics;

import bookreaders.Dom;
import bookreaders.Sax;
import exceptions.ReaderException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Stax_basics {

    public static void main(String[] args) {
        //System.out.println(new File("resources/books.xml").exists());
        try {
            //Dom dom = new Dom();
            //dom.parse(new FileInputStream(new File("resources/books.xml")));
           
            Sax sax = new Sax();
            sax.parse(new FileInputStream(new File("resources/books.xml")));
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
