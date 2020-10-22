package com.richstonedt.fcjx.dsp.paas.tag.repository;

import com.richstonedt.fcjx.dsp.paas.tag.dao.TagDOMapper;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.assember.PaasTagAssember;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.po.TagDO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.po.TagDOExample;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>PaasTagRepository</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/13 1:41 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Repository
public class PaasTagRepository {

    private final TagDOMapper tagDOMapper;

    public PaasTagRepository(TagDOMapper tagDOMapper) {
        this.tagDOMapper = tagDOMapper;
    }

    public List<PaasTagDTO> findTagByStatus(byte status) {
        TagDOExample example = new TagDOExample();
        example.createCriteria().andStatusEqualTo(status);
        List<TagDO> tagDOList = tagDOMapper.selectByExample(example);
        return tagDOList.stream().map(PaasTagAssember::po2Dto).collect(toList());
    }
}
