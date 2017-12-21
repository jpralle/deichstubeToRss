package de.cloneworks.deichstubeToRss.logging;

import org.junit.Ignore;
import org.junit.Test;

public class MyLoggerTest {

    @Test
    @Ignore("This is an end-to-end test, that requires the user to check the mails of the administrator and to set the property \"send mail\" to true.")
    public void testErrorWithMail() {
        MyLogger subject = new MyLogger(MyLoggerTest.class);

        subject.error("This a test error");
    }

    @Test
    @Ignore("This is an end-to-end test, that requires the user to check the mails of the administrator and to set the property \"send mail\" to true.")
    public void testErrorWithMailAndException() {
        Exception ex = new Exception("thsi is the exception message");
        MyLogger subject = new MyLogger(MyLoggerTest.class);

        subject.error("This a test error", ex);
    }

}