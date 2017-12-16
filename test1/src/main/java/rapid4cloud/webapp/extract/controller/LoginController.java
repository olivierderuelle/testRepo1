package rapid4cloud.webapp.extract.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rapid4cloud.webapp.extract.service.LoginService;

@Controller
public class LoginController 
{

	@Autowired
	private LoginService loginService;

	/*
	 * Sample on how to read properties from the file application.properties in the resources folder
	 */
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){

        boolean isValidUser = loginService.validateUser(name, password);

        if (!isValidUser) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }

        model.put("name", name);
        //model.put("password", password);
        model.put("time", new Date());
		model.put("message", "Welcoma MR GAUTAM "+name);

        return "welcome";
    }

}