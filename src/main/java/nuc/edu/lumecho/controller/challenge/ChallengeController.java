package nuc.edu.lumecho.controller.challenge;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.entity.Challenge;
import nuc.edu.lumecho.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    @PostMapping("/create")
    public Result createChallenge(@RequestBody ChallengeCreateRequest challengeCreateRequest) {
        challengeService.createChallenge(challengeCreateRequest);
        return Result.ok();
    }
    @GetMapping("/list")
    public Result<List<Challenge>> getList(@RequestParam(required = false) Integer status) {
            List<Challenge> challenges = challengeService.getChallengeList(status);
            return Result.ok(challenges);
    }
}
