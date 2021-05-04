package tech.meliora.natujenge.charset;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Charset Server Example" ) ;

        int port = 6001;

        CharsetServer server = new CharsetServer();

        server.start(port);


        System.out.println( "The End!" ) ;

    }
}
