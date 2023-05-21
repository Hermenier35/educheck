package com.example.educheck.Utils;

public class HttpUrl {
    private static String ip = "http://10.245.217.29:3000/api/";
    public static String UrlPostOnUniversity = ip + "addUser";
    public static String UrlGetUniversity = "";
    public static String UrlGetUniversities = ip + "allUni";
    public static String UrlPostAcademicBackground = ip + "pathStudent";
    public static String UrlGetAcademicBackground = ip + "getPaths";
    public static String UrlConnexion = ip + "findUser";
    public static String UrlForgetPassword = ip + "resetPassword";
    public static String UrlSendMessageTo = ip + "sendMessageTo";
    public static String UrlRetrieveMessages = ip + "retrieveMessages";
}
