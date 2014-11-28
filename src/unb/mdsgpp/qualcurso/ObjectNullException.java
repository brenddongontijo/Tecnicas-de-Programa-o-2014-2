package unb.mdsgpp.qualcurso;
/**
 * Class name: ObjectNullException
 *
 * This class is a exception case one object is not instantiate.
 */
public class ObjectNullException extends Exception {
	
	// Required serial version.
	private static final long serialVersionUID = 1L;

	// Empty constructor.
	public ObjectNullException(){
		super();
	}
	
	// Constructor with a message error.
	public ObjectNullException(final String messageError){
		super(messageError);
	}
}
