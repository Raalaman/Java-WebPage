package paw.util;

/**
 *
 * @author fgarcia
 */
public class ReCaptchaException extends Exception {

    /**
     * Creates a new instance of <code>ReCaptchaException</code> without detail
     * message.
     */
    public ReCaptchaException() {
    }

    /**
     * Constructs an instance of <code>ReCaptchaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReCaptchaException(String msg) {
        super(msg);
    }
}
