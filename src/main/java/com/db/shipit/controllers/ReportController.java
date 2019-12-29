package com.db.shipit.controllers;

import com.db.shipit.models.Package;
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

        Report report = new Report(null, null, issuer_id,package_id,null, null,null,null);
        model.addAttribute("report", report);

        return "file_report";
    }


    @PostMapping("/save_report")
    public String saveReport(Model model, @ModelAttribute("report") Report report){
        System.out.println("HERE");
        reportRepository.saveReport(report);
        return "reports";
    }


    @GetMapping("/reports")
    public void getAllPackages (
            @RequestParam(value = "issuer_id", required = true) String issuer_id,
            Model model){

        List<Report> reports = reportRepository.getReport(issuer_id);
        model.addAttribute("reports", reports);
    }



}
