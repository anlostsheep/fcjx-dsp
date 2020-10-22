package com.richstonedt.fcjx.dsp.ticket.repository.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.ticket.constant.EnumRedisKey;
import com.richstonedt.fcjx.dsp.ticket.dao.PaasTagTicketDOMapper;
import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDO;
import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDOExample;
import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDOKey;
import com.richstonedt.fcjx.dsp.ticket.repository.IPaasTagTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

@Repository
public class PaasTagTicketRepositoryImpl implements IPaasTagTicketRepository {

    @Autowired
    private PaasTagTicketDOMapper paasTagTicketDOMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<PaasTagTicketDO> findByTag(List<String> tags) {
        List<PaasTagTicketDO> result = findByTagFromCache(tags);

        if ( CollectionUtils.isEmpty(result) ) {
            result = findByTagFromDB(tags);
            saveCache(result);
        }
        return result;
    }

    private List<PaasTagTicketDO> findByTagFromDB(List<String> tags) {
        PaasTagTicketDOExample example = new PaasTagTicketDOExample();
        example.createCriteria().andPaasTagIn(tags);
        return paasTagTicketDOMapper.selectByExample(example);
    }

    private List<PaasTagTicketDO> findByTagFromCache(List<String> tags) {
        String key = EnumRedisKey.PAAS_TAG_TICKET.getKey();
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        List<String> objects = opsForHash.multiGet(key, tags);
        ArrayList<PaasTagTicketDO> result = Lists.newArrayList();
        for (String object : objects) {
            if ( !StringUtils.isEmpty(object) ) {
                PaasTagTicketDO tagTicketDO = JSON.parseObject(object, PaasTagTicketDO.class);
                result.add(tagTicketDO);
            } else {
                return Lists.newArrayList();
            }
        }
        return result;
    }

    private void saveCache(List<PaasTagTicketDO> tagTicketDOList) {
        String key = EnumRedisKey.PAAS_TAG_TICKET.getKey();
        Map<String, String> map = tagTicketDOList.stream().collect(toMap(PaasTagTicketDOKey::getPaasTag, JSON::toJSONString));
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }
}
