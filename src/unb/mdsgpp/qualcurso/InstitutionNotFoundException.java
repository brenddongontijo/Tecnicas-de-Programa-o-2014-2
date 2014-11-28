package unb.mdsgpp.qualcurso;

/**
 * Class name: InstitutionNotFoundException
 *
 * This class is a exception case a institution is not found.
 */
public class InstitutionNotFoundException extends Exception {

	// Necessary serial version.
	private static final long serialVersionUID = 1L;

	// Empty constructor.
	public InstitutionNotFoundException(){
		super();
	}
	
	// Constructor with a message error.
	public InstitutionNotFoundException(final String messageError){
		super(messageError);
	}
}
