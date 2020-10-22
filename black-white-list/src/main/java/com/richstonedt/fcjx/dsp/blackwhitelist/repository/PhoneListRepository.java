package com.richstonedt.fcjx.dsp.blackwhitelist.repository;

import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>PhoneListRepository</code></b>
 * <p/>
 * 号码包DAO
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:39.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Repository
public interface PhoneListRepository extends JpaRepository<PhoneListEntity, String > {

    List<PhoneListEntity> findByPhoneNumIn(List<String> phoneNums);
    List<PhoneListEntity> findByTag(String tag);
    List<PhoneListEntity> findByTag(String tag, Pageable pageable);
}
