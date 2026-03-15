package nuc.edu.lumecho.controller.challenge;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeListResponse;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeSubmissionItemResponse;
import nuc.edu.lumecho.model.entity.Challenge;
import nuc.edu.lumecho.service.ChallengeService;
import nuc.edu.lumecho.service.ChallengeSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ChallengeSubmissionService challengeSubmissionService;
    /**
     * 创建挑战
     */
    @PostMapping("/create")
    public Result<Void> createChallenge(@RequestBody ChallengeCreateRequest request) {

        challengeService.createChallenge(request);

        return Result.ok();
    }


    /**
     * 挑战列表
     */
    @GetMapping("/list")
    public Result<ChallengeListResponse> getChallengeList(

            @RequestParam(required = false) Integer status,

            @RequestParam(required = false) String keyword,

            @RequestParam(defaultValue = "1") Integer pageNum,

            @RequestParam(defaultValue = "10") Integer pageSize
    ) {

        ChallengeListResponse response =
                challengeService.getChallengeList(status, keyword, pageNum, pageSize);

        return Result.ok(response);
    }

    @GetMapping("/{challengeId}/submission/list")
    public Result<List<ChallengeSubmissionItemResponse>> getSubmissionList(
            @PathVariable Long challengeId
    ) {

        List<ChallengeSubmissionItemResponse> list =
                challengeSubmissionService.getSubmissionList(challengeId);

        return Result.ok(list);

    }


    /**
     * 挑战详情
     */
    @GetMapping("/detail/{id}")
    public Result<Challenge> getChallengeDetail(@PathVariable Long id) {

        Challenge challenge = challengeService.getChallengeDetail(id);

        return Result.ok(challenge);
    }

}