package com.blog.api.resp;

public class ErrorMessage {
    public String message;
    public String detail;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public static ErrorMessage noAuth() {
        return new ErrorMessage("Auth Token Error",
                "Auth token is not valid or expired");
    }

    public static ErrorMessage loginFailed() {
        return new ErrorMessage("Login Failed",
                "Username or password is not valid");
    }
    public static ErrorMessage notFound() {
        return new ErrorMessage("Not Found",
                "There is no resource available at this url");
    }
}
