package com.h3xstream.findsecbugs.sql;

import edu.umd.cs.findbugs.test.BaseDetectorTest;
import edu.umd.cs.findbugs.test.EasyBugReporter;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JpaInjectionDetectorTest extends BaseDetectorTest {

    @Test
    public void detectInjection() throws Exception {
        //Locate test code
        String[] files = {
                getClassFilePath("testcode/sqli/JpaSql")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        verify(reporter).doReportBug(
            bugDefinition()
                    .bugType("SQL_INJECTION")
                    .inClass("JpaSql").inMethod("getUserByUsername").atLine(16)
                    .build()
        );
        verify(reporter).doReportBug(
            bugDefinition()
                    .bugType("SQL_INJECTION")
                    .inClass("JpaSql").inMethod("getUserByUsernameAlt2").atLine(24)
                    .build()
        );

        //Only the previous 2 cases should be marked as vulnerable
        verify(reporter, times(2)).doReportBug(
            bugDefinition()
                    .bugType("SQL_INJECTION")
                    .build()
        );
    }
}
