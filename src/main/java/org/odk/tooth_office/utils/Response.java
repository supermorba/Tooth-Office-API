package org.odk.tooth_office.utils;

public class Response {
    private String statut;
    private String message;
    private Object data;

    public Response(String statut, String message, Object data){
        this.statut = statut;
        this.message = message;
        this.data = data;
    }

    public Response(String message, Object data){
        this.message = message;
        this.data = data;
    }
    public Response(){}

    public static Response succes(String message, Object data){
        return new Response("OK", message, data);
    }

    public static Response error(String message){
        return new Response(" ", message);
    }

}
