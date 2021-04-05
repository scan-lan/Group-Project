package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class App_UnitTests
{
    // test that the connection is null if the localhost is invalid
    @Test
    public void connect_invalidLocation()
    {
        // given
        String location = "klo0";

        // when
        Connection connection = App.connect(location, App.databaseDriver, true);

        // then
        assertNull(connection);
    }

    // test that the connection is null if the database driver is invalid
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
