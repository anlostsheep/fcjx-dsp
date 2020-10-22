package com.richstonedt.fcjx.dsp.blackwhitelist.repository;

import com.richstonedt.fcjx.dsp.blackwhitelist.entity.BlackWhiteListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>BlackWhiteListRegionDao</code></b>
 * <p/>
 * 名单列表DAO
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:31.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Repository
public interface BlackWhiteListRepository extends JpaRepository<BlackWhiteListEntity, String > {

    List<BlackWhiteListEntity> findByStatus(Byte status);
    List<BlackWhiteListEntity> findByAdIdIn(List<String> adIds);
}
