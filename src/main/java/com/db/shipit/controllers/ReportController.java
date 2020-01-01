package com.db.shipit.controllers;

import com.db.shipit.models.Report;
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
    public String getPackage (
            @RequestParam(value = "id", required = true) String report_id,
            Model model){

        Report report = reportRepository.getReport(report_id);
        model.addAttribute("report", report);
        return "report";
    }

    @GetMapping("/reports")
    public String getAllPackages (Model model) {
        List<Report> reports = reportRepository.getAllReports();
        model.addAttribute("reports", reports);
        return "reports";
    }
}
