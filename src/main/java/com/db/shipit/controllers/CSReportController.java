package com.db.shipit.controllers;
import com.db.shipit.models.Message;
import com.db.shipit.models.Report;
import com.db.shipit.repositories.PackageRepository;
import com.db.shipit.repositories.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CSReportController {

    public static class PairOfReportAndUserID{
        public PairOfReportAndUserID(Report repo, String userId) {
            this.repo = repo;
            this.userId = userId;
        }

        private Report repo;
        private String userId;

        public Report getRepo() {
            return repo;
        }

        public void setRepo(Report repo) {
            this.repo = repo;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping("/cs_file_report")
    public String getReportInfo (
            @RequestParam(value = "package_id", required = true) String package_id,
            @RequestParam(value = "issuer_id", required = true) String issuer_id,
            Model model){

        Report report = new Report(null, null, issuer_id,package_id,null, null,null,null, null);
        model.addAttribute("report", report);

        return "cs_file_report";
    }

    @PostMapping("/cs_save_report")
    public String saveReport(Model model, @ModelAttribute("report") Report report){
        Report newReport = reportRepository.saveReport(report);
        String redirect = "redirect:cs_report?id=" + newReport.getReport_id();
        return redirect;
    }

    @GetMapping("/cs_report")
    public String getReport (
            @RequestParam(value = "package_id", required = true) String package_id,
            @RequestParam(value = "id", required = true) String id,
            Model model){
        PairOfReportAndUserID pair = reportRepository.getReportByID(id);
        Report report= pair.getRepo();
        String userId =pair.getUserId();
        String courier="-";
        courier= packageRepository.getAPackageCourier( package_id);
        model.addAttribute("newMessage", new Message());
//System.out.println(id);
        model.addAttribute("report", report);
        model.addAttribute("package_id", package_id);
        model.addAttribute("report_id", id);
        model.addAttribute("userId",userId);
        model.addAttribute("courier", courier);
        model.addAttribute("result","");
        List<Message>[] messages =new ArrayList[2];
        messages= reportRepository.getAllMessages(id);
        messages[0].sort(new SortbyMessageNum());
        int counter1=0;
        int counter2=0;
        messages[1].sort(new SortbyMessageNum());
        ArrayList<MessageInfoPair> mylist = new ArrayList<>();
        //  ArrayList<MessageInfo> otherList = new ArrayList<>();
        for (int i=0; i<messages[1].size()+messages[0].size();i++)
        {
            if(counter1<messages[0].size()&&counter2< messages[1].size()) {
                if ((Integer.parseInt(messages[0].get(counter1).getMessage_number())) < (Integer.parseInt( messages[1].get(counter2).getMessage_number()))) {
                    mylist.add(new MessageInfoPair(new MessageInfo(messages[0].get(counter1).getText(), "true", messages[0].get(counter1).getDate()),new MessageInfo("", "false", messages[0].get(counter1).getDate())));
                    counter1++;
                } else {
                    mylist.add(new MessageInfoPair(new MessageInfo("", "true",  messages[1].get(counter2).getDate()),(new MessageInfo( messages[1].get(counter2).getText(), "false",  messages[1].get(counter2).getDate()))));

                    counter2++;
                }
            }
            else if (counter1<messages[0].size())
            {
                mylist.add(new MessageInfoPair(new MessageInfo(messages[0].get(counter1).getText(), "true", messages[0].get(counter1).getDate()),new MessageInfo("", "false", messages[0].get(counter1).getDate())));
                counter1++;
            }else if (counter2< messages[1].size())
            {    mylist.add(new MessageInfoPair(new MessageInfo("", "true",  messages[1].get(counter2).getDate()),new MessageInfo( messages[1].get(counter2).getText(), "false",  messages[1].get(counter2).getDate())));
                counter2++;
            }

        }
        //   for(int k=0;k<commonList.size();k++)
        //    System.out.println(commonList.get(k).toString());
        model.addAttribute("myList", mylist);

        return "cs_report";
    }

    @GetMapping("/cs_chat.sendMessage")
    public String sendMessage(@RequestParam(value = "package_id", required = true) String package_id,
                              @RequestParam(value = "id", required = true) String report_id,
                              @ModelAttribute("newMessage") Message newMessage,Model model){
PairOfReportAndUserID pair      =reportRepository.getReportByID(report_id);
        Report report = pair.getRepo();
                String courier="-";
        courier= packageRepository.getAPackageCourier( package_id);
        reportRepository.commitMessage(newMessage,report_id);
        return "redirect:cs_report?id=" + report_id+"&package_id="+package_id;
    }

    @GetMapping("/cs_assign_to_me")
    public String assignToMe( @RequestParam(value = "id", required = true)  String report_id         ){
        PairOfReportAndUserID pair    =reportRepository.getReportByID(report_id);
        Report report = pair.getRepo();
        reportRepository.assignReport(report_id);
        return "redirect:cs_reports";
    }
    @PostMapping("/cs_update_result")
    public String updateResult( @RequestParam(value = "package_id2", required = true) String package_id,@RequestParam(value = "id2", required = true)  String report_id  ,@RequestParam(value = "result", required = true)  String result       ){
        reportRepository.updateResult(report_id,result);
        return "redirect:cs_report?id=" + report_id+"&package_id="+package_id;
    }

    @GetMapping("/cs_reports")
    public String getAllPackages (Model model) {
        List<Report> reports = reportRepository.getAllReportByCustomerS();
        model.addAttribute("reports", reports);
        return "cs_reports";
    }
}
