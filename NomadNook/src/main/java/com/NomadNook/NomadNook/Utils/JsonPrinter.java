package com.NomadNook.NomadNook.Utils;

import com.NomadNook.NomadNook.Config.GsonConfiguration;
import com.google.gson.Gson;

public class JsonPrinter {

    private JsonPrinter() {
    }

    private static final Gson gson = new GsonConfiguration().gson();

    public static String toString(Object t) {
        return gson.toJson(t).trim().replace("\n", "").replace("\t", "");
    }
}