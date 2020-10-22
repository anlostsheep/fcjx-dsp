package com.richstonedt.fcjx.dsp.paas.tag.service.impl;

import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagPageableBO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;
import com.richstonedt.fcjx.dsp.paas.tag.repository.TagBitmapRepository;
import com.richstonedt.fcjx.dsp.paas.tag.service.IPaasTagService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.roaringbitmap.longlong.Roaring64NavigableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <b><code>PaasTagServiceImplTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 6:57 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@SpringBootTest
class PaasTagServiceImplTest {

    @Autowired
    private IPaasTagService paasTagService;
    @Autowired
    private TagBitmapRepository tagBitmapRepository;

    @Test
    void getBitmapByTagName() {
        Roaring64NavigableMap bitmap = tagBitmapRepository.findBitmapByTagName("xx");
    }

    @Test
    void getTagByPhone() {
        String phone = "13553532608";
        List<PaasTagDTO> tags = paasTagService.getTagByPhone(phone);
        assert !CollectionUtils.isEmpty(tags);

        tags = paasTagService.getTagByPhone("1064713142313");
        assert !CollectionUtils.isEmpty(tags);
    }

    @Test
    void testGetTagByPhone() {
    }

    @Test
    void getPhoneByTagAllIn() {
        paasTagService.getPhoneByTagAllIn(Lists.newArrayList("2800_branch_HY", "2800_P0110"), 1, 10);
    }

    @Test
    void getPhoneByTagAnyIn() {
        PaasTagPageableBO<String> resultPageable = paasTagService.getPhoneByTagAnyIn(Lists.newArrayList("2800_branch_HY", "2800_P0110"), 1, 30);
        List<String> result = resultPageable.getData();
        PaasTagPageableBO<String> firstPageable = paasTagService.getPhoneByTagAnyIn(Lists.newArrayList("2800_branch_HY", "2800_P0110"), 1, 10);
        List<String> first = firstPageable.getData();
        PaasTagPageableBO<String> secondPageable = paasTagService.getPhoneByTagAnyIn(Lists.newArrayList("2800_branch_HY", "2800_P0110"), 2, 10);
        List<String> second = secondPageable.getData();
        PaasTagPageableBO<String> thirdPageable = paasTagService.getPhoneByTagAnyIn(Lists.newArrayList("2800_branch_HY", "2800_P0110"), 3, 10);
        List<String> third = thirdPageable.getData();

        assert result.get(0).equalsIgnoreCase(first.get(0));
        assert result.get(9).equalsIgnoreCase(first.get(9));

        assert result.get(10).equalsIgnoreCase(second.get(0));
        assert result.get(19).equalsIgnoreCase(second.get(9));

        assert result.get(20).equalsIgnoreCase(third.get(0));
        assert result.get(29).equalsIgnoreCase(third.get(9));
    }

    @Test
    void encrypt() {
    }

    @Test
    void decrypt() {
    }
}