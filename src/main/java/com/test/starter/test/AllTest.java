package com.test.starter.test;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: xq
 * @Date: 2021/03/02 14:55
 */
public class AllTest {
    public static void main(String[] args) {
        LinkedList<String> argsList = new LinkedList<>();

        argsList.offerFirst("/home/sftp2/files/23/f5694255-9a14-405d-a045-3a9c177e09de/f5694255-9a14-405d-a045-3a9c177e09de/HomeStudent2019Retail.img");
        argsList.offerFirst("/home/sftp2/files/23/f5694255-9a14-405d-a045-3a9c177e09de/80000125708-立白全自动浓缩无磷粉每两件外送1千克除菌去渍洗衣液1袋-960克-2246001381-纸箱-正稿20210105-out.zip");
        argsList.offerFirst("/home/sftp2/files/23/f5694255-9a14-405d-a045-3a9c177e09de/dys");
        System.out.println(JSON.parseArray(JSON.toJSONString(argsList)));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sdf.format(new Date());
        System.out.println(data);
    }
}
