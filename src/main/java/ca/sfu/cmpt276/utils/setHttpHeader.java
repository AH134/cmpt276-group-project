package ca.sfu.cmpt276.utils;

import jakarta.servlet.http.HttpServletResponse;

public abstract class setHttpHeader {
    public static void setHeader(HttpServletResponse res) {
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Expires", "0");
    }
}