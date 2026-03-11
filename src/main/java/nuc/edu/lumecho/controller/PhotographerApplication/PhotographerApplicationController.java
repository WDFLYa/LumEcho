package nuc.edu.lumecho.controller.PhotographerApplication;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.service.PhotographerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/photographerapplication")
public class PhotographerApplicationController {

    @Autowired
    private PhotographerApplicationService photographerApplicationService;

    @RequestMapping("/apply")
    public Result apply(@RequestBody @Valid PhotographerApplyRequest photographerApplyRequest) {
        photographerApplicationService.insertPhotographerApply(photographerApplyRequest);
        return Result.ok();
    }
}
