package com.richstonedt.fcjx.dsp.blackwhitelist.repository;

import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>BlackWhiteListStatusRepository</code></b>
 * <p/>
 * 广告素材实体DAO
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:38.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Repository
public interface AdRepository extends JpaRepository<AdEntity, String > {

    List<AdEntity> findByAdIdIn(List<String> adIds);
    List<AdEntity> findByStatus(Byte status);
    Page<AdEntity> findByStatus(Byte status, Pageable pageable);
}
