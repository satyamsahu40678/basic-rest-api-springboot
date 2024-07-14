package org.example.rest.webservices.restfulwebservices.helloworld;

import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

///REST API
@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // /hello-world - basically the url of our api
    //"Hello World"
    @RequestMapping(method = RequestMethod.GET, path = "/hello-world") //during the method get mapping
    // it is not necessarily to be written so we can remove it too
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean") //during the method get mapping
    // it is not necessarily to be written so we can remove it too
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    //Path parameter
    // /users/{id}/todos/{id} => /users/1/todos/200
    // /hello-world/path-variable/{name}
    // /hello-world/path-variable/Satyam
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
//        return new HelloWorldBean("Hello World" + name);
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }


    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {

        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default", locale);
//        return "Hello World";

//        1:good.morning.message
//        2:picking values
    }
}
