package com.site.controller;

import com.site.model.domain.Person;
import com.site.model.dto.PersonDTO;
import com.site.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangjie on 2014/12/8.
 */
@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(name = "/create.html", method = RequestMethod.GET)
    public String show(){
        return "create";
    }

    @RequestMapping(name = "/create.html", method = RequestMethod.POST)
    public void create(PersonDTO personDTO){
        Person person = personService.create(personDTO);
    }

//    @RequestMapping(name = "/delete.html", method = RequestMethod.POST)
//    public void delete(PersonDTO personDTO){
//        personService.delete(personDTO.getId());
//    }
}
