package net.batmat.testing;

public class TheMain
{
    public static void main( String[] args ) throws ClassNotFoundException
    {
        new App().someMethod( null );
        new App().someMethod( "hop" );
        new App().someOtherMethod( null , null);

        new App().someOtherMethod( "biiim" , 1);
        new App().someOtherMethod( "biiim" , null);
    }
}
