package spring.boot.customwebcontainer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.common.Response;

@RestController
public class SampleController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    // http://localhost:8080/get?id=1
    @RequestMapping(value = "/get", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Response get(@RequestParam String id) {
        Response response = new Response();
        response.setMessage("Success");
        return response;
    }
}
