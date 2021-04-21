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
        String location = null;

        // when
        Connection connection = App.connect(location, App.DATABASE_DRIVER, true);

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
        Connection connection = App.connect("localhost:33061", databaseDriver, true);

        // then
        assertNull(connection);
    }

    // test that if the connection is null there is no error
    @Test
    public void disconnect_nullConnection()
    {
        //given
        Connection connection = null;

        //then
        App.disconnect(connection);
    }
}
