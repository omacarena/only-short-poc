package my.java.spring.mongo.myapp.controllers;

import my.java.spring.mongo.myapp.repositories.MyMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my")
public class MyController {

    private final MyMongoRepository myMongoRepository;

    @Autowired
    public MyController(MyMongoRepository myMongoRepository) {
        this.myMongoRepository = myMongoRepository;
    }

    @GetMapping()
    public String getSomething() {
        boolean ok = myMongoRepository.canConnect();
        return ok ? "works :)" : "does not work :(";
    }
}
