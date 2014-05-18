package net.batmat.jsr305reporter.aspect;

import java.lang.annotation.Annotation;

import net.batmat.jsr305reporter.reporter.HTMLJSR305Reporter;
import net.batmat.jsr305reporter.reporter.JSR305Reporter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class JSR305ReporterAspect
{
    static
    {
        System.out.println( "[JSR305Reporter] Loading null checking aspect - DO NOT USE IT IN PRODUCTION." );
        // Assert JSR305 presence
        try
        {
            Class.forName( "javax.annotation.Nonnull" );
        }
        catch ( ClassNotFoundException e )
        {
            throw new RuntimeException( e );
        }
    }

    // TODO : Define a way to make/inject that implementation custom
    JSR305Reporter reporter = new HTMLJSR305Reporter();

    @Before( "call(* *(..)) &&  !within(net.batmat.jsr305reporter..*)" )
    public void beforeCall( JoinPoint joinPoint )
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        for ( int parameterIndex = 0; parameterIndex < parameterAnnotations.length; ++parameterIndex )
        {
            Annotation[] as = parameterAnnotations[parameterIndex];
            for ( Annotation a : as )
            {
                if ( isNonnullAnnotation( a ) && joinPoint.getArgs()[parameterIndex] == null )
                {
                    reporter.reportIncorrectNullParameter( joinPoint, parameterIndex );
                }
            }
        }
    }

    private boolean isNonnullAnnotation( Annotation a )
    {
        return a.annotationType().getSimpleName().toLowerCase().contains( "nonnull" );
    }

    @AfterReturning( pointcut = "call(* *(..)) &&  !within(net.batmat.jsr305reporter..*)", returning="result"  )
    public void afterCall( JoinPoint joinPoint , Object result)
    {
        // TODO
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[] annotations = methodSignature.getMethod().getAnnotations();
        if ( annotations.length > 0 )
        {
            System.out.println( " <<< Retour de " + methodSignature.getName() );
            for ( Annotation annot : annotations )
            {
                if ( isNonnullAnnotation( annot ) && result == null  ) // TODO get return value
                {
                    reporter.reportIncorrectNullReturn( joinPoint );
                }
            }
        }
    }
}
