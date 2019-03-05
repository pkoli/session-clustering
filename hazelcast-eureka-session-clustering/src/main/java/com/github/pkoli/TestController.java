package com.github.pkoli;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    @RequestMapping(value = "/testing")
    public String testSessionClustering(HttpSession httpSession) {

        Integer counts = (Integer) httpSession.getAttribute("counts");

        if (counts == null) {
            counts = 0;
        }

        httpSession.setAttribute("counts", ++counts);

        return httpSession.getAttribute("counts").toString();
    }
}
