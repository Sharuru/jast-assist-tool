package self.srr.jast.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Account controller
 * <p>
 * Created by Sharuru on 2017/06/29.
 */
@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            // already logged in
            model.addAttribute("sysmsg", "您已成功登录，请勿重复操作。");
            return "fragments/redirect";
        } else {
            return "login";
        }
    }
}
