package com.db.shipit.controllers;
import com.db.shipit.models.Message;
import com.db.shipit.models.Report;
import com.db.shipit.repositories.PackageRepository;
import com.db.shipit.repositories.ReportRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.*;
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
       model.addAttribute("newMessage", new Message());
//System.out.println(id);
       model.addAttribute("report", report);
       model.addAttribute("package_id", package_id);
       model.addAttribute("report_id", id);
       model.addAttribute("courier", courier);
       ArrayList<Message> listofOther=new ArrayList<>();
       List<Message> messages= reportRepository.getAllMessages(id,listofOther);
       messages.sort(new SortbyMessageNum());
       int counter1=0;
       int counter2=0;
       listofOther.sort(new SortbyMessageNum());
       ArrayList<MessageInfo> commonList = new ArrayList<>();
       for (int i=0; i<listofOther.size()+messages.size();i++)
       {
           if(counter1<messages.size()&&counter2<listofOther.size()) {
               if ((Integer.parseInt(messages.get(counter1).getMessage_number())) < (Integer.parseInt(listofOther.get(counter2).getMessage_number()))) {
                   commonList.add(new MessageInfo(messages.get(counter1).getText(), "true", messages.get(counter1).getDate()));
                   counter1++;
               } else {
                   commonList.add(new MessageInfo(listofOther.get(counter2).getText(), "false", listofOther.get(counter2).getDate()));
                   counter2++;
               }
           }
           else if (counter1<messages.size())
           {
               commonList.add(new MessageInfo(messages.get(counter1).getText(), "true", messages.get(counter1).getDate()));
               counter1++;

           }else if (counter2<listofOther.size())
           { commonList.add(new MessageInfo(listofOther.get(counter2).getText(), "false", listofOther.get(counter2).getDate()));
               counter2++;
           }

       }
       for(int k=0;k<commonList.size();k++)
          System.out.println(commonList.get(k).toString());
       model.addAttribute("messages", commonList);

       return "report";
   }

    @GetMapping("/chat.sendMessage")
    public String sendMessage(@RequestParam(value = "package_id", required = true) String package_id,
                              @RequestParam(value = "id", required = true) String report_id,
                              @ModelAttribute("newMessage") Message newMessage,Model model){

        Report report = reportRepository.getReportByID(report_id);
       // Message mm = new Message();
      //  System.out.println("New Message :"+model.getAttribute("newMessage"));
        //String newMessage=(String) model.getAttribute("newMessage");
      //  mm.setText(newMessage);
        String courier="-";
        courier= packageRepository.getAPackageCourier( package_id);
       // Message newMessagee= new Message();
      //  newMessagee.setText(newMessage);
        reportRepository.commitMessage(newMessage,report_id);
        return "redirect:report?id=" + report_id+"&package_id="+package_id;
   }



        @GetMapping("/reports")
    public String getAllPackages (Model model) {
        List<Report> reports = reportRepository.getAllReports();
        model.addAttribute("reports", reports);
        return "reports";
    }
}
