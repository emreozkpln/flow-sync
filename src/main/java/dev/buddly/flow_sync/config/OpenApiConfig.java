package dev.buddly.flow_sync.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "FlowSync",
                        email = "ozkaplanemre1@gmail.com"
                ),
                description = "FlowSync documentation",
                title = "FlowSync Application",
                version = "1.0"
        ),
        security = {
            @SecurityRequirement(
                    name = "bearerAuth"
            )
        }
        /*servers = {
                @Server(
                        description = "Local Dev",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod Dev",
                        url = "http://localhost:8080"
                )
        }*/
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Jwt auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
