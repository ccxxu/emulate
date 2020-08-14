package com.ce.nw.common.util;

import com.ce.nw.common.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ccxxu on 2019-09-27.
 */
public class HttpUtils {

    public static void writerError(Result bs, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(bs.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),bs);
    }
}
