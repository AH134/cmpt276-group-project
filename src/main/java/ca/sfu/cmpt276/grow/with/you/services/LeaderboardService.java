package ca.sfu.cmpt276.grow.with.you.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.GrowerRepository;

import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.SponsorRepository;

import java.util.List;

@Service
public class LeaderboardService {

    @Autowired
    private GrowerRepository growerRepository;

    public List<Grower> getTopGrowers() {
        return growerRepository.findTopGrowers();
    }

    @Autowired
    private SponsorRepository sponsorRepository;

    public List<Sponsor> getTopSponsors() {
        return sponsorRepository.findTopSponsors();
    }
}