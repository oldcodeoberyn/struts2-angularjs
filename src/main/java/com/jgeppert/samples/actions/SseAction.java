/*
 * Copyright (c) 2016 Nokia Solutions and Networks. All rights reserved.
 */

package com.jgeppert.samples.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author Lex Li
 * @date 03/08/2016
 */
public class SseAction extends ActionSupport
{
    private static final Logger log = LogManager.getLogger(SseAction.class);
    public String handleSSE() {

        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        log.debug("Inside handleSSE()+suscribe "+Thread.currentThread().getName());

        int timeout = 15*1000;
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();

        while((end - start) < timeout) {

            try {
                PrintWriter printWriter = response.getWriter();
                printWriter.println(  "data: "+new Date().toString() );
                printWriter.println(); // note the additional line being written to the stream..
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            end = System.currentTimeMillis();

        }

        log.debug("Exiting handleSSE()-suscribe"+Thread.currentThread().getName());

        return SUCCESS;
    }
}
