package com.springapp.mvc;

import com.site.model.dao.PersonRepository;
import com.site.model.domain.Person;
import com.site.model.dto.PersonDTO;
import com.site.service.impl.RepositoryPersonService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by zhangjie on 2014/12/8.
 */
public class RepositoryPersonServiceTest {

    private static final Long PERSON_ID = Long.valueOf(5);
    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";

    private RepositoryPersonService personService;

    private PersonRepository personRepositoryMock;

    @Before
    public void setUp(){
        personService = new RepositoryPersonService();

        personRepositoryMock = mock(PersonRepository.class);

        personService.setPersonRepository(personRepositoryMock);
    }

    @Test
    public void create(){
        PersonDTO created = new PersonDTO();
        created.setLastName(FIRST_NAME);
        created.setFirstName(LAST_NAME);
        Person returned = personService.create(created);

    }
}
