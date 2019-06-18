package http.server.common;

public enum MIMEType {
    PLAIN("text/plain"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    FORM_DATA("multipart/form-data"),
    JSON("application/json");

    private String typeName;

    MIMEType(String typeName) {
        this.typeName = typeName;
    }
}
