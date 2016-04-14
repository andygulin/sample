/*
 * $Id: SphinxException.java 1172 2008-02-24 13:50:48Z shodan $
 */

package examples.showcase.sphinx;

/** Exception thrown on attempts to pass invalid arguments to Sphinx API methods. */
public class SphinxException extends Exception
{
	private static final long	serialVersionUID	= -5459631793379176675L;

	/** Trivial constructor. */
	public SphinxException()
	{
	}

	/** Constructor from error message string. */
	public SphinxException ( String message )
	{
		super ( message );
	}
}

/*
 * $Id: SphinxException.java 1172 2008-02-24 13:50:48Z shodan $
 */
