package com.pafolder.graduation.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableCaching
public class ApplicationConfiguration {
    public static final String RESTAURANT_VOTING_APPLICATION_SUMMARY =
            "<b>Restaurant Voting Application</b> implements a Voting System for users making " +
                    "decisions which restaurant to have lunch at.<li> There are two types of users: Admins and " +
                    "regular Users.</li><li> Admins can input Restaurants and theirs lunch Menus of the day " +
                    "(dish names with prices).</li><li> Menus must be provided in advance for next (or any further) date.</li>" +
                    "<li> Users can vote for a restaurant they want to have lunch at today. Only one vote per user per " +
                    "date counts.</li><li> If User votes again the same day the existing vote should be deleted." +
                    "</li><li> Sending and deleting votes is possible if the time does not exceed 11:00. After that, existing vote " +
                    "can't be changed.<br><br>" +
                    "<b>Credentials for testing:</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "Admin:&nbsp;<i>admin@mail.com&nbsp;/&nbsp;admin</i><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;User:&nbsp;<i>user@mail.com&nbsp;/&nbsp;password</i>" +
                    "<br><br><div><a href=\"https://github.com/pafolder/rva\">Application source files (GitHub)</a></div>";

    @Bean
    public OpenAPI customOpenAPI(@Value("${rva.version}") String appVersion) {
        Contact contact = new Contact();
        contact.name("Sergei Pastukhov");
        contact.email("pafolder@gmail.com");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("REST API Documentation for Restaurant Voting Application")
                        .version(appVersion)
                        .description(RESTAURANT_VOTING_APPLICATION_SUMMARY)
                        .contact(contact));
    }
}
