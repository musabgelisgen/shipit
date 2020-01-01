package com.db.shipit.controllers;
import com.db.shipit.models.Message;
import com.db.shipit.models.Report;
import com.db.shipit.repositories.PackageRepository;
import com.db.shipit.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping("/file_report")
    public String getReportInfo (
            @RequestParam(value = "package_id", required = true) String package_id,
            @RequestParam(value = "issuer_id", required = true) String issuer_id,
            Model model){

        Report report = new Report(null, null, issuer_id,package_id,null, null,null,null, null);
        model.addAttribute("report", report);

        return "file_report";
    }

    @PostMapping("/save_report")
    public String saveReport(Model model, @ModelAttribute("report") Report report){
        Report newReport = reportRepository.saveReport(report);
        String redirect = "redirect:report?id=" + newReport.getReport_id();
        return redirect;
    }

   @GetMapping("/report")
   public String getReport (
           @RequestParam(value = "package_id", required = true) String package_id,
           @RequestParam(value = "id", required = true) String id,
           Model model){
       Report report = reportRepository.getReportByID(id);
       String courier="-";
       courier= packageRepository.getAPackageCourier( package_id);
       model.addAttribute("newMessage", "");
//System.out.println(id);
       model.addAttribute("report", report);
       model.addAttribute("package_id", package_id);
       model.addAttribute("report_id", id);
       model.addAttribute("courier", courier);

       List<String> messages= reportRepository.getAllMessages(id);
       model.addAttribute("messages", messages);
       return "report";
   }

    @GetMapping("/chat.sendMessage")
    public String sendMessage(@RequestParam(value = "package_id", required = true) String package_id,
                              @RequestParam(value = "id", required = true) String report_id,
                              Model model,@RequestParam("newMessage") String newMessage){

        Report report = reportRepository.getReportByID(report_id);
        Message mm = new Message();
        System.out.println("New Message :"+newMessage);
        mm.setText(newMessage);
        String courier="-";
        courier= packageRepository.getAPackageCourier( package_id);
       // Message newMessagee= new Message();
      //  newMessagee.setText(newMessage);
        reportRepository.commitMessage(mm,report_id);
        String redirect = "redirect:report?id=" + report_id+"&package_id="+package_id;
        return redirect;
   }



        @GetMapping("/reports")
    public String getAllPackages (Model model) {
        List<Report> reports = reportRepository.getAllReports();
        model.addAttribute("reports", reports);
        return "reports";
    }
}
