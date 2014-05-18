package net.batmat.testing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class App
{
    @Nonnull
    public String someMethod(@Nullable String pnullable)
    {
        System.out.println( "somemethod " + pnullable );
        return null;
    }
    
    public void someOtherMethod(@Nonnull String pnonnull, @Nonnull Integer ooo)
    {
        System.out.println( "somemethod " + pnonnull );
    }
}
