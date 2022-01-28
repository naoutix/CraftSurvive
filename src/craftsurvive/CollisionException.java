package craftsurvive;

public class CollisionException extends Exception {

	/** Initialiser CollisionException
	 * @param message le message qui explique pourquoi il y a une collision.
	 */
	public CollisionException(String message) {
		super("Collision : " + message);
    }
}