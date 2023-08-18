package cobo.blog.domain.All.Data.Exception;

import java.net.ProtocolException;

public class BadResponseException extends ProtocolException {
    public BadResponseException(String message) {
        super(message);
    }

}
