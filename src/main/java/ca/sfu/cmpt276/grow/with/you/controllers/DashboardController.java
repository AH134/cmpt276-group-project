package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Notifications;
import ca.sfu.cmpt276.grow.with.you.models.Payment;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.NotificationService;
import ca.sfu.cmpt276.grow.with.you.services.PaymentService;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserRole;
import ca.sfu.cmpt276.utils.setHttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlantService plantService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    private NotificationService notifService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        setHttpHeader.setHeader(res);

        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() == UserRole.ADMIN) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("userList", users);

            return "protected/admin/dashboard";
        }

        if (user.getRole() == UserRole.GROWER) {
            List<Plant> plantsList = plantService.getPlantsByGrower((Grower) user);
            List<Notifications> notifList = notifService.getAllNotifsGrower((Grower) user);
            List<Payment> paymentList = paymentService.getPaymentByGrower((Grower) user);

            for (Payment payment : paymentList) {
                System.out.println(payment.getPaymentId());

            }

            model.addAttribute("activeListings", plantsList.size());
            model.addAttribute("lifetimeListings", ((Grower) user).getPlantsGrown());
            model.addAttribute("notifications", notifList);
            model.addAttribute("payments", paymentList);
            return "protected/user/growerDashboard";
        }

        if (user.getRole() == UserRole.SPONSOR) {
            List<Plant> plantsList = plantService.getPlantsBySponsor((Sponsor) user);
            List<Notifications> notifs = notifService.getAllNotifsSponsor((Sponsor) user);
            List<Payment> paymentList = paymentService.getPaymentBySponsor((Sponsor) user);

            model.addAttribute("activeSponsors", plantsList.size());
            model.addAttribute("lifetimeSponsors", ((Sponsor) user).getPlantsSponsored());
            model.addAttribute("notifications", notifs);
            model.addAttribute("payments", paymentList);
            return "protected/user/sponsorDashboard";
        }

        return "protected/user/sponsorDashboard";
    }
}
