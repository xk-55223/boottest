package com.keith.test.boottest;

import com.keith.test.boottest.utils.HttpClientUtil;
import com.keith.test.boottest.utils.RedisLockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpTest {

    public static int index = 0;

    @Test
    public void testHttp() throws Exception {
        String result = HttpClientUtil.httpGet("http://parcelway-cn-test-temp.oss-accelerate.aliyuncs.com/parcel/invoice/2020/11/12/invoice-b5f34183b30e54023cb277376ae3ac96-P1605150997.pdf", null, null);
        String base64 = Base64Utils.encodeToString(result.getBytes());
        System.out.println(base64);
    }

    @Test
    public void getTest() throws Exception {
        /*String proxyResult = HttpClientUtil.httpGet("http://api.web.21ds.cn/platform/getProxyIp?apkey=f2c87527-f931-18aa-0d88-53b56f814ae6&nums=20", null, null);
        JSONObject proxyJson = JSON.parseObject(proxyResult);
        JSONArray proxyList = proxyJson.getJSONArray("data");
        for (Object proxyObject : proxyList) {
            TaokeProxyDTO proxyDTO = JSON.parseObject(proxyObject.toString(), TaokeProxyDTO.class);
        }*/
        Map<String, String> header = new HashMap<>();
        header.put("referer", "https://login.taobao.com/member/login.jhtml?redirectURL=https%3A%2F%2Fmember1.taobao.com%2Fmember%2Ffresh%2Faccount_security.htm%3Fspm%3D2013.1.754894437.5.24681ed0KKNpUJ");
        String url = "http://h5api.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/?data=%7B%22itemNumId%22%3A%22603133486650%22%7D";
        for (int i = 0; i < 25; i++) {
            Thread.sleep(1000);
            String result = HttpClientUtil.httpGet(url, header, null);
            System.out.println("返回结果为" + result);
        }
    }


    public void test1() {
        synchronized (this.getClass()) {
            index = ++index & 31;
            System.out.println(index);
        }
    }

    @Test
    public void threadText() {
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    test1();
                }
            });
            thread.run();
        }
    }

}
