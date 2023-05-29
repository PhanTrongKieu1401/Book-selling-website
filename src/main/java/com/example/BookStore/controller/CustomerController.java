package com.example.BookStore.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BookStore.dao.CategoryDAO;
import com.example.BookStore.dao.CommentDAO;
import com.example.BookStore.dao.CustomerDAO;
import com.example.BookStore.dao.EvaluationDAO;
import com.example.BookStore.dao.ProductDAO;
import com.example.BookStore.global.GlobalData;
import com.example.BookStore.model.Admin;
import com.example.BookStore.model.Category;
import com.example.BookStore.model.Comment;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.DetailedBill;
import com.example.BookStore.model.Evaluation;
import com.example.BookStore.model.Product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes({"customer","notify"})

public class CustomerController {
	private CustomerDAO customerDAO = new CustomerDAO();
	private ProductDAO productDAO = new ProductDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();
	private CommentDAO commentDAO = new CommentDAO();
	private EvaluationDAO evaluationDAO = new EvaluationDAO();
	
	private final int PRODUCTS_PER_PAGE = 20;
	
	//information
	@GetMapping("/bookstore/user")
	public String user(Model model) {
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
		return "information_customer";
	}
	
	//update information
	@PutMapping("/bookstore/user/update")
	public String updateUser(Model model,Customer customer, HttpSession session) {
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
        if(customerDAO.update(customer))
        	session.setAttribute("customer", customer);
		return "redirect:/bookstore/user";
	}
	
	//comment
	@PostMapping("/bookstore/user/comment/{productCode}")
	public String addComment(Model model, HttpSession session, @PathVariable("productCode") int productCode, Comment comment, Evaluation evaluation) throws ClassNotFoundException, IOException {
		Date date = new Date();
		Customer savedCustomer = (Customer) session.getAttribute("customer");
		Product product = new Product();
		product.setIdPro(productCode);
		comment.setCreateDate(new java.sql.Date(date.getTime()));
		comment.setCustomer(savedCustomer);
		comment.setProduct(product);
		evaluation.setCustomer(savedCustomer);
		evaluation.setProduct(product);
		evaluationDAO.insertEvaluation(evaluation);
		commentDAO.insertComment(comment);
		
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
		return "redirect:/bookstore/viewproduct/" + productCode;
	}
	
	//bookstore
	@GetMapping("/bookstore")
	public String bookstore(Model model,HttpServletRequest request) throws ClassNotFoundException{
		request.getSession().setAttribute("productList", null);
		return "redirect:/bookstore/page/1";
	}
	
	//home
	@GetMapping("/bookstore/page/{pageNumber}")
	public String home(HttpServletRequest request, @PathVariable int pageNumber, Model model) throws ClassNotFoundException{
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productList");
		List<Product> list = productDAO.selectAllProducts();
		List<Category> categories = categoryDAO.selectAllCategory();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(PRODUCTS_PER_PAGE);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("productList", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 20, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/bookstore/page/";
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);
		model.addAttribute("categories", categories);
		return "shop";
	}
	
	//view product
	@GetMapping("/bookstore/viewproduct/{ProductCode}")
	public String viewProduct(Model model, @PathVariable("ProductCode") String productCode, HttpSession session) throws ClassNotFoundException {
		model.addAttribute("ProductCode",productCode);
		Product product = productDAO.selectProduct(productCode);
		List<Category> categories = categoryDAO.selectAllCategory();
		List<Evaluation> evaluations = evaluationDAO.selectAllEvaluationsOfBook(productCode);
		List<Comment> comments = commentDAO.selectAllCommentsOfBook(productCode);
		double totalStarFill = (double) Math.round((evaluations.stream().mapToDouble(Evaluation::getStar).sum() / evaluations.size()) * 10) / 10;
		int totalStarUnfill =(int) Math.floor(5 - totalStarFill);
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
		model.addAttribute("comments",comments);
		model.addAttribute("evaluations",evaluations);
		model.addAttribute("totalStarFill", (float)totalStarFill);
		model.addAttribute("totalStarUnfill", (float)totalStarUnfill);
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		return "book_detail";
	}
	
	//view products by category
	@GetMapping("/bookstore/category/{Key}")
	public String searchCategory(Model model, @PathVariable("Key") String key, RedirectAttributes redirectAttributes) throws ClassNotFoundException {
		String valueString = key;
		redirectAttributes.addFlashAttribute("categoryKey",key);
		return "redirect:/bookstore/category/page/1";
	}
	
	@GetMapping("/bookstore/category/page/{pageNumber}")
	public String viewProductByCategory(Model model, HttpServletRequest request, @PathVariable int pageNumber) throws ClassNotFoundException {
		String categoryKey = (String) model.asMap().get("categoryKey");
		List<Product> list = productDAO.searchProductByCategory(categoryKey);
		List<Category> categories = categoryDAO.selectAllCategory();
		if (list == null) {
			return "redirect:/bookstore";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productList");
		pages = new PagedListHolder<>(list);
		pages.setPageSize(PRODUCTS_PER_PAGE);
		final int goToPage = pageNumber - 1;
		if(goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("productList", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/bookstore/page/";
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);
		model.addAttribute("categories", categories);
		return "shop";
	}
	
	//search products by title or author
	@GetMapping("/bookstore/search/{pageNumber}")
	public String searchProductByTitleOrCategory(Model model, HttpServletRequest request, @PathVariable int pageNumber, @RequestParam("key") String key) throws ClassNotFoundException {
		if (key.equals("")) {
			return "redirect:/bookstore";
		}
		List<Product> list = productDAO.searchProductByTitleOrAuthor(key);
		List<Category> categories = categoryDAO.selectAllCategory();
		if (list == null) {
			return "redirect:/bookstore";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productList");
		pages = new PagedListHolder<>(list);
		pages.setPageSize(PRODUCTS_PER_PAGE);
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("productList", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/bookstore/page/";
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);
		model.addAttribute("categories", categories);
		return "shop";
	}
}
