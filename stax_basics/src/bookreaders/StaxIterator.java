package bookreaders;

import exceptions.ReaderException;
import java.io.InputStream;

public class StaxIterator extends BookReader{

    @Override
    public void parse(InputStream inputStream) throws ReaderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
