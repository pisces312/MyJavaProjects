package spring.boot.learn.env.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.common.Response;
import spring.boot.common.SetParam;

/**
 * 
 * One class with application and controller
 * 
 * @author nil4
 *
 */
@RestController
// contained @ResponseBody
@EnableAutoConfiguration
public class SingleControllerApp {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    // http://localhost:8080/get?id=1
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response get(@RequestParam String id) {
        System.out.println(Thread.currentThread());
        Response response = new Response();
        response.setMessage(id);
        return response;
    }
    
    
    // http://localhost:8080/echo?msg=1
    @RequestMapping(value = "/echo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response echo(@RequestParam String msg) {
        System.out.println(Thread.currentThread());
        Response response = new Response();
        response.setMessage(msg);
        return response;
    }

    @RequestMapping(value = "/getset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response get(@RequestBody SetParam setParam) {
        if (setParam != null) {
            if (!CollectionUtils.isEmpty(setParam.getSet())) {
                System.out.println(setParam.getSet()
                    .getClass()
                    .getName());
                for (Object obj : setParam.getSet()) {
                    System.out.println(obj);
                }
            }
        }


        Response response = new Response();
        response.setMessage("Success");
        return response;
    }

    @Override
    public String toString() {
        return "SpringBootSampleController";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SingleControllerApp.class, args);
    }
}
