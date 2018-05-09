package com.imxiaomai.bms.util;

import java.util.UUID;

public class TokenUtil {
    /**
     * 无意义的token，如需要后期修改 todo
     */
    public static String getToken(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

}
