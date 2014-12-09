package com.site.model.dao;

import com.site.model.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjie on 2014/12/8.
 */

public interface PersonRepository extends JpaRepository<Person, Long> {

}
