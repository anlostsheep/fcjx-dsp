package com.richstonedt.fcjx.dsp.paas.tag.schedule.impl;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

/**
 * <b><code>PaasTagFileScheduledServiceImplTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/17 11:39 上午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@SpringBootTest
class PaasTagFileScheduledServiceImplTest {

    @Autowired
    private PaasTagFileScheduledServiceImpl paasTagFileScheduledService;

    @Test
    void parseFile() throws IOException {
        String localpaht = "/Users/liangqinglong/Library/Containers/com.tencent.WeWorkMac/Data/Library/Application Support/WXWork/Data/1688851814121546/Cache/File/2020-04/x/ECHANNEL_RESULT_20200331200324.txt";
        File file = new File(localpaht);
        paasTagFileScheduledService.parseFile(Lists.newArrayList(file));
    }
}