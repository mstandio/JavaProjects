package bookreaders;

import data.Book;
import exceptions.ReaderException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Sax extends BookReader {

    @Override
    public void parse(InputStream inputStream) throws ReaderException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        try {
            SAXParser sp = spf.newSAXParser();
            org.xml.sax.InputSource input = new InputSource(inputStream);
            sp.parse(input, new Handler(books));
        } catch (IOException ex) {
            throw new ReaderException("Error while reading data", ex);
        } catch (ParserConfigurationException ex) {
            throw new ReaderException("Error in parser configuration", ex);
        } catch (SAXException ex) {
            throw new ReaderException("Error while parsing", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}

class Handler extends DefaultHandler {

    private ArrayList<Book> books;
    private Book currentBook;
    private final StringBuffer accumulator = new StringBuffer();

    public Handler(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        super.startElement(uri, localName, qName, atrbts);
        if (qName.equals("Book")) {
            currentBook = new Book();
        } else if (qName.equals("Title")) {
            accumulator.delete(0, accumulator.length() - 1);
        } else if (qName.equals("Cost")) {
            accumulator.delete(0, accumulator.length() - 1);
            currentBook.currency = BookReader.recognizeCurrency(atrbts.getValue("currency"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("Book")) {
            books.add(currentBook);
        } else if (qName.equals("Title")) {
            currentBook.title = accumulator.toString().trim();
        } else if (qName.equals("Cost")) {
            currentBook.cost = Float.valueOf(accumulator.toString());
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        super.characters(chars, start, length);
        accumulator.append(chars, start, length);
    }
}