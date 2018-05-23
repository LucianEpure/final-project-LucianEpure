package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        // Get the role of logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String targetUrl = "";
        if(role.contains("Quartermaster")) {
            targetUrl = "/quartermaster";
        } else if(role.contains("Regiment Commander")) {
            targetUrl = "/regimentCommander";
        }else if(role.contains("Chief Commander")){
            targetUrl = "/chiefCommander";
        }
        return targetUrl;
    }
}