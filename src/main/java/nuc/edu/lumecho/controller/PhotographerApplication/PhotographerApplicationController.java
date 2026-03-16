package nuc.edu.lumecho.controller.PhotographerApplication;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import nuc.edu.lumecho.service.PhotographerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/photographerapplication")
public class PhotographerApplicationController {

    @Autowired
    private PhotographerApplicationService photographerApplicationService;

    @PostMapping("/apply")
    public Result apply(@RequestBody @Valid PhotographerApplyRequest photographerApplyRequest) {
        photographerApplicationService.insertPhotographerApply(photographerApplyRequest);
        return Result.ok();
    }
    @GetMapping("/get")
    public Result getUserApply() {
        PhotographerApplication application = photographerApplicationService.getUserApply();
        return Result.ok(application);
    }
}
