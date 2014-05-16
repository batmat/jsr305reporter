package net.batmat.jsr305reporter.reporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

// Basic impl, writing each time to disk
public class HTMLJSR305Reporter
    implements JSR305Reporter
{
    private static final String NO_ISSUE_FOUND = "No issue found";

    File jsr305reportFile = new File( "jsr305-report.html" );

    private static final String BASE_CONTENT = "<!doctype html>" + "<html lang='en'>"
        + "<head><meta charset='utf-8'><title>JSR 305 report</title><style></style></head>"
        + "<body><h1>JSR 305 Report</h1><div id='results'><ul>" + NO_ISSUE_FOUND + "</ul></div></body>" + "</html>\n";

    StringBuilder errorContent = new StringBuilder();

    public HTMLJSR305Reporter()
    {
        System.out.println("hiiii");
        write();
    }

    public void reportIncorrectNullParameter( JoinPoint joinPoint, int parameterIndex )
    {
        System.out.println("booombiiiim");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        errorContent.append( "<li>Passed null to " + "<em>" + methodSignature.getDeclaringType().getPackage().getName()
            + "." + methodSignature.getMethod().getName() + " (" + paramsToString( methodSignature, parameterIndex )
            + ")" + "</em>" );
        errorContent.append( ". <span class='exception'>See code at " );
        errorContent.append( joinPoint.getSourceLocation() );
        errorContent.append( "</span></li>" );

        write();
    }

    private void write()
    {
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( jsr305reportFile ) );
            String content = BASE_CONTENT;
            if ( errorContent.length() > 0 )
            {
                content = content.replace( NO_ISSUE_FOUND, errorContent.toString() );
            }
            bufferedWriter.write( content );
            bufferedWriter.close();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    private String paramsToString( MethodSignature methodSignature, int problematicIndex )
    {
        StringBuilder buffer = new StringBuilder();
        for ( int i = 0; i < methodSignature.getParameterTypes().length; ++i )
        {
            if ( i == problematicIndex )
            {
                buffer.append( "<strong>" );
            }
            buffer.append( methodSignature.getParameterTypes()[i].getSimpleName() ).append( ' ' ).append( methodSignature.getParameterNames()[i] );
            if ( i == problematicIndex )
            {
                buffer.append( "</strong>" );
            }
            if ( i < methodSignature.getParameterTypes().length - 1 )
            {
                buffer.append( ", " );
            }
        }
        return buffer.toString();
    }
}
