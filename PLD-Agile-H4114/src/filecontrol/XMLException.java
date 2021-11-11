/**
 * XMLException
 * @author 4IF-4114
 */

package filecontrol;

/**
 * Show the error messages during the parsing of the .xml file
 * */
public class XMLException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Send the error message
     * @param message the description of the Exception
     */
    public XMLException(String message) {
        super(message);
    }

}
