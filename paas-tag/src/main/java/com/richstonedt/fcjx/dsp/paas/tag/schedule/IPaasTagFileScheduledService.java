package com.richstonedt.fcjx.dsp.paas.tag.schedule;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <b><code>IPaasTagFileScheduledService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 3:20 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public interface IPaasTagFileScheduledService {

    /**
     * 下载文件到本地
     * @throws IOException
     */
    void downloadFile() throws IOException;

    /**
     * 解析文件
     * @param files
     * @throws IOException
     */
    void parseFile(List<File> files) throws IOException;

    /**
     * 定时任务
     */
    void scheduled();
}
