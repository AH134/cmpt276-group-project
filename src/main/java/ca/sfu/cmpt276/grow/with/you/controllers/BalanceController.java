package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.setHttpHeader;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BalanceController {
    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    public String getBalance(Model model, HttpSession session, HttpServletResponse res) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);

        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() == UserRole.GROWER) {
            Grower grower = (Grower) user;
            model.addAttribute("user", grower);
        } else if (user.getRole() == UserRole.SPONSOR) {
            Sponsor sponsor = (Sponsor) user;
            model.addAttribute("user", sponsor);
        } else {
            model.addAttribute("user", user);
        }

        return "protected/user/balance.html";
    }

    @PostMapping("/balance/complete")
    public String updateBalance(@RequestParam Map<String, String> formInput, HttpSession session,
            HttpServletResponse res,
            HttpServletRequest req, Model model) {
        try {
            setHttpHeader.setHeader(res);
            User user = (User) session.getAttribute("session_user");
            Double newBalance = Double.parseDouble(formInput.get("balance"));

            if (user == null) {
                return "redirect:/";
            }

            if (user.getRole() == UserRole.GROWER) {
                Grower grower = (Grower) user;
                grower.setBalance(grower.getBalance() - newBalance);
                userService.createUser(grower);
                model.addAttribute("user", grower);
            } else if (user.getRole() == UserRole.SPONSOR) {
                Sponsor sponsor = (Sponsor) user;
                sponsor.setBalance(sponsor.getBalance() + newBalance);
                userService.createUser(sponsor);
                model.addAttribute("user", sponsor);
            } else {
                model.addAttribute("user", user);
            }

            return "protected/user/balance.html";
        } catch (Exception e) {
            res.setStatus(400);
            return "protected/user/balance.html";
        }
    }
}
