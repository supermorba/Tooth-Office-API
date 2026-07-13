package org.odk.tooth_office.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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
    public Response(String statut, String message){
        this.statut = statut;
        this.message = message;
    }

    public static Response succes(String message, Object data){
        return new Response( "OK", message, data);
    }

    public static Response error(String message){
        return new Response("KO", message);
    }

}
