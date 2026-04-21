package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        Client classUnderTest = new Client();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
