package pw.react.backend.appException;

/** Created by Pawel Gawedzki on 07-Oct-2019. */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
