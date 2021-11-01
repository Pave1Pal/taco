package tacos.sia5;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Component
public class Transformer {

    public String transform(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        return content.toUpperCase(Locale.ROOT);
    }
}
