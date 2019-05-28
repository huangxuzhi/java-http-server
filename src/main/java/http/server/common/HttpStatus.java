package http.server.common;


import com.sun.tools.corba.se.idl.constExpr.Not;

public enum HttpStatus{
    Continue("100", "Continue"),
    Switching_Protocols("101", "Switch Protocols"),
    Processing("102", "Processing"),
    Early_Hints("103", "Early Hints"),

    OK("200", "OK"),
    Created("201", "Created"),
    Accepted("202", "Accepted"),
    Non_Authoritative_Information("203", "Non-Authoritative Information"),
    No_Content("204", "No Content"),
    Reset_Content("205", "Reset Content"),
    Partial_Content("206", "Partial Content"),
    Multi_Status("207", "Multi-Status"),
    Already_Reported("208", "Already Reported"),
    IM_Used("226", "IM Used"),

    Multiple_Choices("300", "Multiple Choices"),
    Moved_Permanently("301", "Moved Permanently"),
    Found("302", "Found"),
    See_Other("303", "See Other"),
    Not_Modified("304", "Not Modified"),
    Use_Proxy("305", "Use Proxy"),
    Switch_Proxy("306", "Switch Proxy"),
    Temporary_Redirect("307", "Temporary Redirect"),
    Permanent_Redirect("308", "Permanent Redirect"),

    Bad_Request("400", "Bad Request"),
    Unauthorized("401", "Unauthorized"),
    Payment_Required("402", "Payment Required"),
    Forbidden("403", "Forbidden"),
    Not_Found("404", "Not Found"),
    Method_Not_Allowed("405", "Method Not Allowed"),
    Not_Acceptable("406", "Not Acceptable"),
    Proxy_Authentication_Required("407", "Proxy Authentication Required"),
    Request_Timeout("408", "Request Timeout"),
    Conflict("409", "Conflict"),
    Gone("410", "Gone"),
    Length_Required("411", "Length Required"),
    Precondition_Failed("412", "Precondition Failed"),
    Payload_Too_Large("413", "Payload Too Large"),
    URI_Too_Long("414", "URI Too Long"),
    Unsupported_Media_Type("415", "Unsupported Media Type"),
    Range_Not_Satisfiable("416", "Range Not Satisfiable"),
    Expectation_Failed("417", "Expectation Failed"),
    Im_a_teapot("418", "I'm a teapot"),
    Misdirected_Request("421", "Misdirected Request"),
    Unprocessable_Entity("422", "Unprocessable Entity"),
    Locked("423", "Locked"),
    Failed_Dependency("424", "Failed Dependency"),
    Too_Early("425", "Too Early"),
    Upgrade_Required("426", "Upgrade Required"),
    Precondition_Required("428", "Precondition Required"),
    Too_Many_Requests("429", "Too Many Requests"),
    Request_Header_Fields_Too_Large("431", "Request Header Fields Too Large"),
    Unavailable_For_Legal_Reasons("451", "Unavailable For Legal Reasons"),

    Internal_Server_Error("500", "Internal Server Error"),
    Not_Implemented("501", "Not Implemented"),
    Bad_Gateway("502", "Bad Gateway"),
    Service_Unavailable("503", "Service Unavailable"),
    Gateway_Timeout("504", "Gateway Timeout"),
    HTTP_Version_Not_Supported("505", "HTTP Version Not Supported"),
    Variant_Also_Negotiates("506", "Variant Also Negotiates"),
    Insufficient_Storage("507", "Insufficient_Storage"),
    Loop_Detected("508", "Loop Detected"),
    Not_Extended("510", "Not Extended"),
    Network_Authentication_Required("511", "Network Authentication Required");

    private String code;
    private String status;

    HttpStatus(String code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
