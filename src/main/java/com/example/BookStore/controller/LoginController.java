package com.example.BookStore.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.BookStore.dao.AdminDAO;
import com.example.BookStore.dao.CustomerDAO;
import com.example.BookStore.global.GlobalData;
import com.example.BookStore.model.Admin;
import com.example.BookStore.model.Customer;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	private AdminDAO adminDAO = new AdminDAO();
	private CustomerDAO customerDAO = new CustomerDAO();
	
	//admin
	//login admin
	@GetMapping("/admin/login")
	public String showLoginAdmin() {
		return "login_admin";
	}
	
	//logout admin
	@GetMapping("/admin/logout")
	public String logoutAdmin(HttpSession session) {
		session.removeAttribute("admin");
		return "redirect:/admin";
	}
	
	//check login admin
	@PostMapping("/admin/login")
    @ResponseBody
    public Map<String, String> loginAdmin(Model model, @RequestBody Admin admin, RedirectAttributes redirectAttributes, HttpSession session) throws ClassNotFoundException {
		Map<String, String> result = new HashMap<>();
	    if (adminDAO.checkLogin(admin)) {
	        result.put("status", "success");
	        result.put("url", "/admin");
	        session.setAttribute("admin", admin);
	    } else {
	        result.put("status", "failure");
	    }
	    return result;
	}
	
	//customer
	//login customer
	@GetMapping("/bookstore/login")
	public String showLoginCustomer() {
		GlobalData.cart.clear();
		return "login_customer";
	}
	
	//register customer
	@GetMapping("/bookstore/register")
	public String showRegisterCustomer() {
		return "register_customer";
	}
	
	//check exist customer
	@PostMapping("/bookstore/customer/register")
	@ResponseBody
	public Map<String, String> registerCustomer(Model model, @RequestBody Customer customer) {
		Map<String, String> result = new HashMap<>();
		if(customerDAO.checkExist(customer)) {
			if(customerDAO.register(customer)) {
				result.put("status", "success");
				result.put("url", "/bookstore/login");
			} else {
				result.put("status", "failure");
			}
		}
		else {
			result.put("status", "exist");
		}
	    return result;
	}
	
	//check login customer
	@PostMapping("/customer/login")
    @ResponseBody
    public Map<String, String> loginCustomer(Model model, @RequestBody Customer customer, RedirectAttributes redirectAttributes, HttpSession session) throws ClassNotFoundException {
		Map<String, String> result = new HashMap<>();
	    if (customerDAO.checkLogin(customer)) {
	        result.put("status", "success");
	        result.put("url", "/bookstore");
	        Customer newCustomer = customerDAO.takeCustomer(customer);
	        session.setAttribute("customer", newCustomer);
	    } else {
	        result.put("status", "failure");
	    }
	    return result;
	}
}
