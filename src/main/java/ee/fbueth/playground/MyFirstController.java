package ee.fbueth.playground;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {

    @GetMapping(value = "/greeting")
    public String greetUser(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return "Hello there";
        }
        return "Hello " + name;
    }
}
