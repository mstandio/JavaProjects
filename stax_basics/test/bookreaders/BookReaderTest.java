package bookreaders;

import data.Currencies;
import exceptions.ReaderException;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookReaderTest {

    BookReader bookReader;
    FileInputStream fileInputStream;

    public BookReaderTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException {

        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
            }
        }
        File file = new File(getClass().getResource("/resources/books.xml").getFile());
        assertTrue(file.exists());
        fileInputStream = new FileInputStream(file);
    }

    @Test
    public void TestDom() {
        bookReader = new Dom();
        try {
            bookReader.parse(fileInputStream);
        } catch (ReaderException ex) {
            fail(ex.getMessage());
        }
        verifyConsistency();
    }

    @Test
    public void TestSax() {
        bookReader = new Sax();
        try {
            bookReader.parse(fileInputStream);
        } catch (ReaderException ex) {
            fail(ex.getMessage());
        }
        verifyConsistency();
    }
    
    
    @Test
    public void TestStaxCursor() {
        bookReader = new StaxCursor();
        try {
            bookReader.parse(fileInputStream);
        } catch (ReaderException ex) {
            fail(ex.getMessage());
        }
        verifyConsistency();
    }
    
    @Test
    public void TestStaxIterator() {
        bookReader = new StaxIterator();
        try {
            bookReader.parse(fileInputStream);
        } catch (ReaderException ex) {
            fail(ex.getMessage());
        }
        verifyConsistency();
    }

    /*
     * Sample xml:
     *
     * <?xml version="1.0" encoding="UTF-8"?>
     * <BookCatalogue xmlns="http://www.publishing.org">
     *   <Book>
     *      <Title>Yogasana Vijnana: the Science of Yoga</Title>
     *      <Cost currency="INR">11.50</Cost>
     *   </Book>
     *   <Book>
     *       <Title>The First and Last Freedom</Title>
     *       <Cost currency="USD">2.95</Cost>
     *    </Book>
     * </BookCatalogue>
     */
    public void verifyConsistency() {
        assertEquals(2, bookReader.books.size());

        assertNotNull(bookReader.books.get(0).title);
        assertEquals("Yogasana Vijnana: the Science of Yoga", bookReader.books.get(0).title);
        assertEquals(Currencies.INR, bookReader.books.get(0).currency);
        assertEquals(11.5f, bookReader.books.get(0).cost, 0.001f);

        assertNotNull(bookReader.books.get(1).title);
        assertEquals("The First and Last Freedom", bookReader.books.get(1).title);
        assertEquals(Currencies.USD, bookReader.books.get(1).currency);
        assertEquals(2.95f, bookReader.books.get(1).cost, 0.001f);
    }
}
