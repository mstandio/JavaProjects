package bookreaders;

import data.Book;
import exceptions.ReaderException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dom extends BookReader {

    private Document doc;

    @Override
    public void parse(InputStream inputStream) throws ReaderException {

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            NodeList nodeList = doc.getFirstChild().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() != Node.TEXT_NODE && nodeList.item(i).getNodeName().equals("Book")) {
                    processBookNode(nodeList.item(i));
                }
            }
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

    private void processBookNode(Node bookNode) {
        NodeList nodeList = bookNode.getChildNodes();
        Book book = new Book();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() != Node.TEXT_NODE) {
                if (nodeList.item(i).getNodeName().equals("Title")) {
                    book.title = (nodeList.item(i).getTextContent()).trim();
                } else if (nodeList.item(i).getNodeName().equals("Cost")) {
                    book.cost = Float.valueOf(nodeList.item(i).getTextContent());
                    book.currency = BookReader.recognizeCurrency(
                            nodeList.item(i).getAttributes().getNamedItem("currency").getNodeValue());
                }
            }
        }
        books.add(book);
    }
}
