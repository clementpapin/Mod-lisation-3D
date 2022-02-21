package projet_modelisation.ply;

public class PlyHeaderException extends Exception {
	
	public PlyHeaderException(Exception excep) {
		super(excep);
	}
	
	public PlyHeaderException() {
		super();
	}
}