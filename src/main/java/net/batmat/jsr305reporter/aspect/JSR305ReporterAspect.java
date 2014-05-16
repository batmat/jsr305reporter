package net.batmat.jsr305reporter.aspect;

import java.lang.annotation.Annotation;

import net.batmat.jsr305reporter.reporter.HTMLJSR305Reporter;
import net.batmat.jsr305reporter.reporter.JSR305Reporter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class JSR305ReporterAspect
{
    static
    {
        System.out.println( "[JSR305Reporter] Loading null checking aspect - DO NOT USE IT IN PRODUCTION." );
    }

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

    @After( "call(* *(..)) &&  !within(net.batmat.jsr305reporter..*)" )
    public void afterCall( JoinPoint joinPoint )
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[] annotations = methodSignature.getReturnType().getAnnotations();
        if ( annotations.length > 0 )
        {
            System.out.println( " <<< Retour de " + methodSignature.getName() );
            for ( Annotation annot : annotations )
            {
                System.out.println( annot );
            }
            System.out.println( "> Après appel" );
        }
    }
}
