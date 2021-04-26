package ua.com.foxminded.university.dao.postgres.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class d {

    private final CreateDeleteDate createDeleteDate = new CreateDeleteDate();

    @Test
    void setUp() throws URISyntaxException, IOException {
        createDeleteDate.createDate();
    }

    @Test
    void tearDown() {
        createDeleteDate.deleteDate();
    }
}
