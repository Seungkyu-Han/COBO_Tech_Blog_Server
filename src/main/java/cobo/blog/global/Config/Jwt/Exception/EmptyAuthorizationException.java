package cobo.blog.global.Config.Jwt.Exception;

public class EmptyAuthorizationException extends NullPointerException{

    public EmptyAuthorizationException(String message) {
        super(message);
    }
}
