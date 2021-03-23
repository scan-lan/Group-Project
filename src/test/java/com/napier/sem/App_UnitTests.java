package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class App_UnitTests
{
    @Test
    public void connect_invalidLocation()
    {
        // given
        String location = "localhost:80";

        // when
        Connection connection = App.connect(location, App.databaseDriver, true);

        // then
        assertNull(connection);
    }

    @Test
    public void connect_invalidDriver()
    {
        // given
        String databaseDriver = "mysqf";

        // when
        Connection connection = App.connect("localhost:80", databaseDriver, true);

        // then
        assertNull(connection);
    }
}
