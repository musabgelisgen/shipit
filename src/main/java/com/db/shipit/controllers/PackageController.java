package com.db.shipit.controllers;

import com.db.shipit.models.*;
import com.db.shipit.models.Package;
import com.db.shipit.repositories.*;
import com.db.shipit.utils.CourierPicker;
import com.db.shipit.utils.DatePicker;
import com.db.shipit.utils.RandomID;
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

    @Autowired
    SubscriptionRepository subscriptionRepository;

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
        String id = RandomID.generateUUID();
        String receiverId = packet.getReceiver_id().substring(0, packet.getReceiver_id().indexOf('-'));
        String paymentStatus = packet.getPayment_side().equalsIgnoreCase("sender") ? "paid" : "not paid";
        String from = packet.getFrom_city().substring(packet.getFrom_city().indexOf('-') + 1);

        Customer c = customerRepository.searchCustomerFromId(receiverId);
        String to_city = c.getCity_name();

        packet.setPackage_id(id)
                .setReceiver_id(receiverId)
                .setSender_id(currentUser.getID())
                .setSend_date(DatePicker.getDate())
                .setPayment_status(paymentStatus)
                .setStatus("preparing")
                .setCourier(CourierPicker.getRandomCourierName())
                .setFrom_city(from)
                .setCurr_city(from)
                .setTo_city(to_city);

        /*
        String packageType = packet.getPackage_type();
        String deliveryType = packet.getDelivery_type();
         */
        if(packet.getPayment_side().equals("sender")){
            List<Subscription> subscriptions = subscriptionRepository.getSubscriptionByID(currentUser.getID());
            Subscription latestSubscription = subscriptions.get(0);
            if (latestSubscription.isIs_active()) {
                Subscription currSubscription = latestSubscription;

            }
            customerRepository.changeCustomerBalance(- ((int) packet.getCost()));
        }

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
}
