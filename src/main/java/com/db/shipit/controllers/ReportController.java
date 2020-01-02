package com.db.shipit.controllers;
import com.db.shipit.models.Message;
import com.db.shipit.models.Report;
import com.db.shipit.models.User;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.PackageRepository;
import com.db.shipit.repositories.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.db.shipit.ShipitApplication.currentUser;

class MessageInfo {
    public String textt;
    public String mine;
    public String date;

    public MessageInfo(String textt, String mine, String date) {
        this.textt = textt;
        this.mine = mine;
        this.date = date;
    }
    @Override
    public String toString() {
        return "MessageInfo{" +
                "textt='" + textt + '\'' +
                ", mine='" + mine + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
};
class MessageInfoPair {
    public MessageInfo m1;
    public MessageInfo m2;

    public MessageInfoPair(MessageInfo m1, MessageInfo m2) {
        this.m1 = m1;
        this.m2 = m2;
    }
};


class SortbyMessageNum implements Comparator<Message>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Message a, Message b)
    {
        return Integer.parseInt(a.getMessage_number() )- Integer.parseInt(b.getMessage_number());
    }
}
@Controller
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/file_report")
    public String getReportInfo (
            @RequestParam(value = "package_id", required = true) String package_id,
            @RequestParam(value = "issuer_id", required = true) String issuer_id,
            Model model){

        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
            Report report = new Report(null, null, issuer_id, package_id, null, null, null, null, null);
            model.addAttribute("report", report);

            return "file_report";
        }
        else
            return "redirect:/my_account";
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
       if(currentUser == null) {
           model.addAttribute("user", new User());
           return "login";
       }
       else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
           Report report = reportRepository.getReportByID(id);
           String courier = "-";
           courier = packageRepository.getAPackageCourier(package_id);
           model.addAttribute("newMessage", new Message());
//System.out.println(id);
           model.addAttribute("report", report);
           model.addAttribute("package_id", package_id);
           model.addAttribute("report_id", id);
           model.addAttribute("courier", courier);

           List<Message>[] messages = new ArrayList[2];
           messages = reportRepository.getAllMessages(id);
           messages[0].sort(new SortbyMessageNum());
           int counter1 = 0;
           int counter2 = 0;
           messages[1].sort(new SortbyMessageNum());
           ArrayList<MessageInfoPair> mylist = new ArrayList<>();
           //  ArrayList<MessageInfo> otherList = new ArrayList<>();
           for (int i = 0; i < messages[1].size() + messages[0].size(); i++) {
               if (counter1 < messages[0].size() && counter2 < messages[1].size()) {
                   if ((Integer.parseInt(messages[0].get(counter1).getMessage_number())) < (Integer.parseInt(messages[1].get(counter2).getMessage_number()))) {
                       mylist.add(new MessageInfoPair(new MessageInfo(messages[0].get(counter1).getText(), "true", messages[0].get(counter1).getDate()), new MessageInfo("", "false", messages[0].get(counter1).getDate())));
                       counter1++;
                   } else {
                       mylist.add(new MessageInfoPair(new MessageInfo("", "true", messages[1].get(counter2).getDate()), (new MessageInfo(messages[1].get(counter2).getText(), "false", messages[1].get(counter2).getDate()))));

                       counter2++;
                   }
               } else if (counter1 < messages[0].size()) {
                   mylist.add(new MessageInfoPair(new MessageInfo(messages[0].get(counter1).getText(), "true", messages[0].get(counter1).getDate()), new MessageInfo("", "false", messages[0].get(counter1).getDate())));
                   counter1++;
               } else if (counter2 < messages[1].size()) {
                   mylist.add(new MessageInfoPair(new MessageInfo("", "true", messages[1].get(counter2).getDate()), new MessageInfo(messages[1].get(counter2).getText(), "false", messages[1].get(counter2).getDate())));
                   counter2++;
               }

           }
           //   for(int k=0;k<commonList.size();k++)
           //    System.out.println(commonList.get(k).toString());
           model.addAttribute("myList", mylist);

           return "report";
       }
       else
           return "redirect:/my_account";
   }

    @GetMapping("/chat.sendMessage")
    public String sendMessage(@RequestParam(value = "package_id", required = true) String package_id,
                              @RequestParam(value = "id", required = true) String report_id,
                              @ModelAttribute("newMessage") Message newMessage,Model model){

        Report report = reportRepository.getReportByID(report_id);
        String courier="-";
        courier= packageRepository.getAPackageCourier( package_id);
        reportRepository.commitMessage(newMessage,report_id);
        return "redirect:report?id=" + report_id+"&package_id="+package_id;
   }



   @GetMapping("/reports")
    public String getAllPackages (Model model) {
       if(currentUser == null) {
           model.addAttribute("user", new User());
           return "login";
       }
       else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
           List<Report> reports = reportRepository.getAllReports();
           model.addAttribute("reports", reports);
           return "reports";
       }
       else
           return "redirect:/my_account";
    }
}
