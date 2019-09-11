package http.server.common;

public enum MIMEType {
    PLAIN("text/plain"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    FORM_DATA("multipart/form-data"),
    JSON("application/json"),
    HTML("text/html"),
    XML("application/xml");

    private String typeName;

    MIMEType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static MIMEType getByTypeName(String typeName) {
        if (typeName == null || typeName.equals("")) {
            return null;
        }
        for (MIMEType m : MIMEType.values()) {
            if (m.typeName.equals(typeName)) {
                return m;
            }
        }
        return null;
    }
}
