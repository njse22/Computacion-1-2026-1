package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        Server classUnderTest = new Server();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
