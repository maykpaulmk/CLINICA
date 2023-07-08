package hn.clinica;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@NpmPackage(value = "@fontsource/bitter", version = "4.5.0")
@Theme(value = "clinicalpaii", variant = Lumo.DARK)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }
}
