package cobo.blog.global.Config.Jwt.Exception;

public class NotAuthorizationException extends RuntimeException{
    public NotAuthorizationException(String message) {
        super(message);
    }
}
