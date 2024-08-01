package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.services.LeaderboardService;
import ca.sfu.cmpt276.utils.setHttpHeader;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model, HttpServletResponse res) {
        setHttpHeader.setHeader(res);
        List<Grower> topGrowers = leaderboardService.getTopGrowers();
        model.addAttribute("topGrowers", topGrowers);

        List<Sponsor> topSponsors = leaderboardService.getTopSponsors();
        model.addAttribute("topSponsors", topSponsors);

        return "protected/leaderboard";
    }
}
