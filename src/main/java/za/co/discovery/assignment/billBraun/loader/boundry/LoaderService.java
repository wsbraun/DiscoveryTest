package za.co.discovery.assignment.billBraun.loader.boundry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;

public interface LoaderService {

	void loadFromStream(InputStream stream) throws EncryptedDocumentException, IOException;

	void loadFromFile(File file) throws EncryptedDocumentException, IOException;

	
}
