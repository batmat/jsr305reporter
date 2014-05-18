package net.batmat.jsr305reporter;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class AspectExecutionIT
{
    @Test
    public void checkReportContent()
    {
        // FIXME : awkward, variabilize with "target" maven variable
        File report = new File( "target/jsr305-report.html" );
        assertThat( report ).exists();
        String fileContent = getFileContent( report );

        // FIXME : yay, counting the li is bad, to say the least 
        assertThat(fileContent.split( "<li>" )).hasSize( 5 /* issues */ + 1 /* 'cause split */ );
    }

    String getFileContent( File file )
    {
        BufferedReader reader = null;
        try
        {
            try
            {
                reader = new BufferedReader( new FileReader( file ) );
                StringBuilder fileContent = new StringBuilder();
                String line = null;
                while ( null != ( line = reader.readLine() ) )
                {
                    fileContent.append( line );
                }
                return fileContent.toString();
            }
            finally
            {
                // try-with-resources ?
                if ( reader != null )
                {
                    reader.close();
                }
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
}
