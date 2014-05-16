package net.batmat.testing;

public class TheMain
{
    public static void main( String[] args ) throws ClassNotFoundException
    {
		Class.forName("net.batmat.jsr305reporter.aspect.JSR305ReporterAspect");
        new App().someMethod( null );
        new App().someOtherMethod( null , null);

        new App().someOtherMethod( "biiim" , 1);
        new App().someOtherMethod( "biiim" , null);
    }
}
