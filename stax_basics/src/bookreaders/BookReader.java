package bookreaders;

import data.Book;
import data.Currencies;
import exceptions.ReaderException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class BookReader {

    protected final ArrayList<Book> books = new ArrayList<Book>();

    public final ArrayList<Book> getBooks() {
        return books;
    }

    public static Currencies recognizeCurrency(String value) {
        if (value.equals("USD")) {
            return Currencies.USD;
        } else if (value.equals("INR")) {
            return Currencies.INR;
        } else {
            throw new IllegalArgumentException("Unrecognized currency " + value);
        }
    }

    public abstract void parse(InputStream inputStream) throws ReaderException;
}