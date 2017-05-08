package desbytes.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * A utility to read the queries from a path.
 * @author Daniel
 */
public class QueryReader {


    public QueryReader() {}

    // Read in a query file from a folder and string
    public String readQueryFile(String folder, String specificQuery) {
        String content = null;
        try {
            Resource res = new ClassPathResource("queries/" + folder + "/" + specificQuery);
            byte[] bdata = FileCopyUtils.copyToByteArray(res.getInputStream());
            content = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException exc){
            System.err.println("Cannot find queries/" + folder + "/" + specificQuery);
        }

        return content;
    }

}
