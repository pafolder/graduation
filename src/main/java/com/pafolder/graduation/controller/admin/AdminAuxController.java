package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Tag(name = "5.4 admin-aux-controller")
public class AdminAuxController extends AbstractController {
    static final String REST_URL = "/api/admin/aux/resetDataBase";
    private static final String TOMCAT_MANAGER_RELOAD_PATH = "/manager/text/reload?path=";
    private static final String PROTOCOL_LOCAL_HOST = "http://localhost:";
    private @Value("${rva.tomcat.script.user}") String tomcatUser;
    private @Value("${rva.tomcat.script.password}") String tomcatPassword;

    @PostMapping(AdminAuxController.REST_URL)
    @Operation(summary = "Restart Application with Database reset", security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public void restartApplication(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tomcatUser, tomcatPassword);
        new RestTemplate().exchange(PROTOCOL_LOCAL_HOST + request.getLocalPort() + TOMCAT_MANAGER_RELOAD_PATH +
                request.getContextPath(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}
