package com.example.BookStore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BookStore.dao.AdminDAO;
import com.example.BookStore.dao.CategoryDAO;
import com.example.BookStore.dao.ProductDAO;
import com.example.BookStore.edu.FileUploadUtil;
import com.example.BookStore.model.Category;
import com.example.BookStore.model.Product;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"admin","notify"}) //lưu admin trong session

public class AdminController {
	private AdminDAO adminDAO = new AdminDAO();
	private ProductDAO productDAO = new ProductDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();

	private final int PRODUCTS_PER_PAGE = 20;
	
	//save image
	public String pathImage(MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String uploadDir ="upload/";
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return fileName;
	}
	
	@GetMapping("/admin")	
	public String home(Model model,HttpServletRequest request, RedirectAttributes redirect) throws Exception {
		request.getSession().setAttribute("productList", null);
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/admin/products/page/1";
	}
	
	//get all
	@GetMapping("/admin/products/page/{pageNumber}")	
	public String getProducts(HttpServletRequest request, @PathVariable int pageNumber, Model model) throws Exception {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productList");
		List<Product> list =(List<Product>) productDAO.selectAllProducts();
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
		String baseUrl = "/admin/products/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);
		model.addAttribute("categories", categories);
		return "home_admin";
	}
	
	//find by title
	@GetMapping("/admin/search/{pageNumber}")
	public String search(@RequestParam("searchText") String searchText, Model model, HttpServletRequest request, @PathVariable int pageNumber) throws ClassNotFoundException {
		if (searchText.equals("")) {
			return "redirect:/admin";
		}
		List<Product> list = productDAO.searchProductByTitle(searchText);
		List<Category> categories = categoryDAO.selectAllCategory();
		if (list == null) {
			return "redirect:/admin";
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
		String baseUrl = "/admin/products/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);
		model.addAttribute("categories", categories);
		return "home_admin";
	}
	
	//view detail
	@GetMapping("/admin/product/{ProductCode}")
	public String getProduct(Model model, @PathVariable("ProductCode") String productCode) throws Exception {
		model.addAttribute("ProductCode", productCode);
		Product product = productDAO.selectProduct(productCode);
		List<Category> categories = categoryDAO.selectAllCategory();
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		return "product_detail";
	}
	
	//insert
	@PostMapping("/admin/product/save/{ProductCode}")
	public String insertProduct(Model model,Product product, @PathVariable("ProductCode") String productCode, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws ClassNotFoundException, IOException {
		model.addAttribute("productCode",productCode);
		product.setPricePro(0);
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());	
		if(fileName == null || fileName.equals("")) {
			
		}
		else {
			product.setImagepPro(pathImage(multipartFile));
		}
		if(productDAO.checkExist(product)) {
			if(productDAO.insertProduct(product)) {
				redirectAttributes.addFlashAttribute("success","Thêm sách thành công!");
				return "redirect:/admin";
			}
			else {
				return "redirect:/admin/product/-1";
			}
		}
		else {
			return "redirect:/admin/product/-1";
		}
	}

	//update
	@PutMapping("/admin/product/save/{ProductCode}")
	public String updateProduct(Model model, Product product, @PathVariable("ProductCode") String productCode, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws ClassNotFoundException, IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());	
		if(fileName == null || fileName.equals("")) {
			
		}
		else {		
			product.setImagepPro(pathImage(multipartFile));
			product.setImagepPro(fileName);
		}
		if(productDAO.updateProduct(product)) {
			redirectAttributes.addFlashAttribute("success","Cập nhật sách thành công!");
			return "redirect:/admin";	
		}
		else {
			return "redirect:/admin/product/" + productCode;
		}
	}
	
	//delete
	@DeleteMapping("/admin/delete/{ProductCode}")
	public String deleteProduct(@PathVariable("ProductCode") String productCode, RedirectAttributes redirectAttributes) {
		if(productDAO.deleteProduct(productCode)) {
			redirectAttributes.addFlashAttribute("success","Xóa sách thành công!");
		};
		return "redirect:/admin";
	}
}
