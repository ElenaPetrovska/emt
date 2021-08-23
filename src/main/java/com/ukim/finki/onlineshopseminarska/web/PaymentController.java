package com.ukim.finki.onlineshopseminarska.web;

import com.ukim.finki.onlineshopseminarska.model.Drink;
import com.ukim.finki.onlineshopseminarska.model.ShoppingCart;
import com.ukim.finki.onlineshopseminarska.model.dto.ChargeRequest;
import com.ukim.finki.onlineshopseminarska.model.exception.TransactionFailedException;
import com.ukim.finki.onlineshopseminarska.service.AuthService;
import com.ukim.finki.onlineshopseminarska.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final AuthService authService;
    private final ShoppingCartService shoppingCartService;

    public PaymentController(AuthService authService, ShoppingCartService shoppingCartService) {
        this.authService = authService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/charge")
    public String getCheckoutPage(Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency","eur");
            model.addAttribute("amount",(int)(shoppingCart.getDrinks().stream().mapToDouble(Drink::getPrice).sum()*100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        }catch (RuntimeException ex){
            return "redirect:/drinks?error=" + ex.getLocalizedMessage();
        }

    }

    @PostMapping("charge")
    public String checkout(ChargeRequest chargeRequest,Model model){
        try{
            this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(),chargeRequest);
            return "redirect:/drinks?message=Succesful payment";
        }catch (RuntimeException | TransactionFailedException ex) {
            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
        }
    }


}
