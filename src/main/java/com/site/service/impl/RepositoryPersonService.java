package com.site.service.impl;

import com.site.model.dao.PersonRepository;
import com.site.model.domain.Person;
import com.site.model.dto.PersonDTO;
import com.site.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangjie on 2014/12/8.
 */
@Service("personService")
public class RepositoryPersonService implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryPersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    @Override
    public Person create(PersonDTO created) {
        Person person = new Person();
        person.setFirstName(created.getFirstName());
        person.setLastName(created.getFirstName());

        return personRepository.save(person);
    }

    @Override
    public Person delete(Long personId){
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Person findById(Long id) {
        return null;
    }

    @Override
    public Person update(PersonDTO updated){
        return null;
    }

    /**
     * This setter method should be used only by unit tests.
     * @param personRepository
     */
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
