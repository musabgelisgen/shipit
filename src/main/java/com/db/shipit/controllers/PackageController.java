package com.db.shipit.controllers;

import com.db.shipit.models.Branch;
import com.db.shipit.models.Customer;
import com.db.shipit.models.Package;
import com.db.shipit.models.User;
import com.db.shipit.repositories.BranchRepository;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.PackageRepository;
import com.db.shipit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class PackageController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/send_package")
    public String sendPackage (Model model){
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
            List<String> receivers = customerRepository.getAllCustomers().stream().map(Customer::getIdAndFullName).collect(Collectors.toList());
            model.addAttribute("receivers", receivers);

            List<String> branches = branchRepository.getAllBranches().stream().map(Branch::getBranchAndCityName).collect(Collectors.toList());
            model.addAttribute("branches", branches);

            model.addAttribute("package", new Package());
            return "send_package";
        }
        return "redirect:/my_account";
    }

    @PostMapping("/send_package")
    public String commitSendPackage (Model model, @ModelAttribute("package") Package packet){
        System.out.println(packet);
        packageRepository.commitPackage(packet);
        return "redirect:/packages";
    }

    @GetMapping("/packages")
    public String getAllPackages (
            @RequestParam(value = "receiver", required = false, defaultValue = "false") boolean receiver,
            @RequestParam(value = "sender", required = false, defaultValue = "false") boolean sender,
            @RequestParam(value = "preparing", required = false, defaultValue = "false") boolean preparing,
            @RequestParam(value = "onTransfer", required = false, defaultValue = "false") boolean onTransfer,
            @RequestParam(value = "onBranch", required = false, defaultValue = "false") boolean onBranch,
            @RequestParam(value = "delivered", required = false, defaultValue = "false") boolean delivered,
            @RequestParam(value = "declined", required = false, defaultValue = "false") boolean declined,
            @RequestParam(value = "id", required = false, defaultValue = "0") String id,
            @RequestParam(value = "accept", required = false, defaultValue = "0") int accept,
            @RequestParam(value = "decline", required = false, defaultValue = "0") int decline,
            Model model){

        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
            Map<String, Boolean> modifications = new HashMap<>();
            modifications.put("receiver", receiver);
            modifications.put("sender", sender);
            modifications.put("preparing", preparing);
            modifications.put("onTransfer", onTransfer);
            modifications.put("onBranch", onBranch);
            modifications.put("delivered", delivered);
            modifications.put("declined", declined);

        if (accept == 1 || decline == 1)
            packageRepository.updatePackageStatus(id, accept, decline);

        List<Package> packages = packageRepository.getAllPackages(modifications);
        model.addAttribute("packages", packages);

            return "packages";
        }
        else
            return "redirect:/my_account";
    }

    @GetMapping("/package")
    public String getPackageInfo (@RequestParam(value = "id") String id, Model model){
        Package packet = packageRepository.findPackageById(id);

        if (packet == null)
            return "redirect:packages";

        model.addAttribute("package", packet);

        String receiver = userRepository.searchUserFromId(packet.getReceiver_id()).getFullName();
        String sender = userRepository.searchUserFromId(packet.getSender_id()).getFullName();

        if (currentUser.getID().equals(packet.getReceiver_id())){
            receiver = "(You) " + receiver;
        }
        else if (currentUser.getID().equals(packet.getSender_id())){
            sender = "(You) " + sender;
        }

        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);

        String cost = "" + packet.getCost();
        if (packet.getCost() == -1){
            cost = "(-1 quota)";
        }
        model.addAttribute("cost", cost);

        return "package";
    }

    @GetMapping("/top_senders")
    public String getTopSenders (Model model) {
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) == null) {
            Map<String, String> top_senders = packageRepository.getTopSenders();
            model.addAttribute("top_senders", top_senders);
            return "top_senders";
        }
        else
            return "redirect:/my_account";
    }

    @GetMapping("/branch_statistics")
    public String getBranchStatistics (Model model) {
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) == null) {
            Map<String, String> branchStatistics = packageRepository.getBranchStatistics();
            model.addAttribute("branchStatistics", branchStatistics);
            return "branchStatistics";
        }
        else
            return "redirect:/my_account";
    }
}
