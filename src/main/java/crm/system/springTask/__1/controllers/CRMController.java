package crm.system.springTask.__1.controllers;

import crm.system.springTask.__1.db.ApplicationRequest;
import crm.system.springTask.__1.repositories.CRMRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Builder
@Controller
public class CRMController {

    @Autowired
    private CRMRepository crmRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("requests", crmRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/add")
    public String addRequest() {
        return "add";
    }

    @PostMapping("/addRequest")
    public String addRequest(@RequestParam(name = "fio") String userName,
                             @RequestParam(name = "kursy") String courseName,
                             @RequestParam(name = "comment") String commentary,
                             @RequestParam(name = "phone") String phone) {

        ApplicationRequest appReq = ApplicationRequest.builder()
                .userName(userName)
                .courseName(courseName)
                .commentary(commentary)
                .phone(phone)
                .handled(false)
                .build();
        crmRepository.save(appReq);
        return "redirect:/";
    }

    @GetMapping(value = "/details/{id}")
    public String detailsRequest(@PathVariable("id") Long id, Model model) {

        ApplicationRequest appReq = crmRepository.findById(id).orElse(null);
        if(appReq.isHandled() == false) {
            model.addAttribute("newRequest", "*** Новая необработанная заявка ***");
        } else {
            model.addAttribute("processedRequest", "Обработанная заявка");
        }
        model.addAttribute("request", appReq);
        return "details";
    }

    @PostMapping("/saveRequest")
    public String saveRequest(@RequestParam(name = "id") Long id) {
        ApplicationRequest appReq = crmRepository.findById(id).orElse(null);
        if(appReq != null) {
            appReq.setHandled(true);
            crmRepository.save(appReq);
        }
        return "redirect:/";
    }
    @PostMapping("/deleteRequest")
    public String deleteRequest(@RequestParam("id") Long id) {
        crmRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam boolean handled, Model model) {
        model.addAttribute("requests", crmRepository.findByHandled(handled));
        return "index";
    }

}
