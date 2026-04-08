package nuc.edu.lumecho.controller.PhotographerApplication;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import nuc.edu.lumecho.service.PhotographerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photographerapplication")
public class PhotographerApplicationController {

    @Autowired
    private PhotographerApplicationService photographerApplicationService;

    // ================= 用户端接口 =================

    /**
     * 用户提交摄影师认证申请
     */
    @PostMapping("/apply")
    public Result apply(@RequestBody @Valid PhotographerApplyRequest photographerApplyRequest) {
        photographerApplicationService.insertPhotographerApply(photographerApplyRequest);
        return Result.ok();
    }

    /**
     * 用户查询自己的申请状态
     */
    @GetMapping("/get")
    public Result getUserApply() {
        PhotographerApplication application = photographerApplicationService.getUserApply();
        return Result.ok(application);
    }



    @GetMapping("/admin/list")
    public Result<List<PhotographerApplication>> getAdminList() {
        List<PhotographerApplication> list = photographerApplicationService.getAllApplicationsForAdmin();
        return Result.ok(list);
    }


    @PutMapping("/admin/review/{id}")
    public Result<Void> reviewApplication(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            HttpServletRequest request) {

        Integer status = (Integer) body.get("status");
        String rejectReason = (String) body.get("rejectReason");


        Long reviewerId = WdfUserContext.getCurrentUserId();


            photographerApplicationService.reviewApplication(id, status, rejectReason, reviewerId);
            return Result.ok();

    }
}
