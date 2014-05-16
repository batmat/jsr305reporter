package net.batmat.testing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class App
{
    public void someMethod(@Nullable @Deprecated String pnullable)
    {
        System.out.println( "somemethod " + pnullable );
    }
    
    public void someOtherMethod(@Nonnull String pnonnull, @Nonnull Integer ooo)
    {
        System.out.println( "somemethod " + pnonnull );
    }
}
