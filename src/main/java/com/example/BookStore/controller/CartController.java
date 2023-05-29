package com.example.BookStore.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BookStore.dao.BillDAO;
import com.example.BookStore.dao.ProductDAO;
import com.example.BookStore.global.GlobalData;
import com.example.BookStore.model.Bill;
import com.example.BookStore.model.Category;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.DetailedBill;
import com.example.BookStore.model.Product;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class CartController {
	private ProductDAO productDAO = new ProductDAO();
	private BillDAO billDAO = new BillDAO();
	private final int PRODUCTS_PER_PAGE = 20;
	
	//page cart
	@GetMapping("/cart")
    public String cartGet(Model model){
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }
	
	//click add from page viewProduct
	@GetMapping("/addToCart/{ProductCode}")
    public String addToCart(Model model,@PathVariable("ProductCode") String productCode,@RequestParam("quantity") int quantity) throws ClassNotFoundException{
		model.addAttribute("ProductCode",productCode);
		Product product = productDAO.selectProduct(productCode);
		//kiểm tra sản phẩm đã tồn tại trong cart chưa
		int i=0;
		for(DetailedBill dtBill : GlobalData.cart) {
			if(i<GlobalData.cart.size()) {
				if(dtBill.getProduct().equals(product)){
					dtBill.setQuantity(dtBill.getQuantity() + quantity);
					dtBill.setAmount(dtBill.getPrice() * dtBill.getQuantity());
					break;
				}
				else {
					i++;
				}
			}			
		}
		//nếu chưa tồn tại thì set giá trị cho detailbill
		if(i >= GlobalData.cart.size()) {
			DetailedBill detailedBill = new DetailedBill();
			detailedBill.setPrice(product.getPricePro());
			detailedBill.setQuantity(quantity);
			detailedBill.setAmount(detailedBill.getPrice() * detailedBill.getQuantity());
			detailedBill.setProduct(product);
	        GlobalData.cart.add(detailedBill);
		}
        return "redirect:/bookstore";
    }
	
	//update quantity
	@PostMapping("/updateQuantity/{index}")
	@ResponseBody
	public Map<String, Object> updateQuantity(Model model, @PathVariable int index, @RequestParam("quantity") int quantity) {
		DetailedBill detailedBill = GlobalData.cart.get(index);
		detailedBill.setQuantity(quantity);
		detailedBill.setAmount(detailedBill.getPrice() * detailedBill.getQuantity());
		GlobalData.cart.set(index, detailedBill);
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
		Map<String, Object> responseData = new HashMap<>();
	    responseData.put("cartCount", GlobalData.cart.size());
	    responseData.put("total", (float) totalDouble);
	    responseData.put("amount", detailedBill.getAmount());
		return responseData;
	}
	
	// delete 1 product
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    } 

    // checkout totalPrice
    @GetMapping("/checkout")
    public String checkout(Model model){
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
		double totalDoubleVAT = totalDouble * 1.08;
		totalDoubleVAT = Math.floor(totalDoubleVAT);
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("totalVAT", (float)totalDoubleVAT);
        model.addAttribute("cart", GlobalData.cart);
        return "check_out";
    } 
    
    @PostMapping("/confirm")
    @ResponseBody
    public Map<String, String> confirm(Model model, @RequestBody() Bill bill, HttpSession session) throws ClassNotFoundException, SQLException {
    	Customer savedCustomer = (Customer) session.getAttribute("customer");
    	double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum() * 1.08;
    	Date date = new Date();

    	//set Bill
    	bill.setCreateDate(new java.sql.Date(date.getTime()));
    	bill.setTotalAmount((float)totalDouble);
    	bill.setCustomer(savedCustomer);
    	bill.setDetailedBills(GlobalData.cart);
    	Map<String, String> result = new HashMap<>();
		if(billDAO.addBill(bill)) {
			GlobalData.cart.clear();
			result.put("status", "success");
			result.put("url", "/bookstore/list-bill");
		} else {
			result.put("status", "failure");
		}
    	return result;
    }
    
    @GetMapping("/bookstore/list-bill")
    public String listBill(Model model,HttpServletRequest request, RedirectAttributes redirect) {
    	request.getSession().setAttribute("billList", null);
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
    	return "redirect:/bookstore/list-bill/page/1";
    }
    
    @GetMapping("/bookstore/list-bill/page/{pageNumber}")
    public String listBillPage(HttpServletRequest request, @PathVariable int pageNumber, Model model, HttpSession session) throws ClassNotFoundException {
    	Customer customer = (Customer) session.getAttribute("customer");
    	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("billList");
    	List<Bill> list = billDAO.selectBills(customer);
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(PRODUCTS_PER_PAGE);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("billList", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 20, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/bookstore/list-bill/page/";
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("bills", pages);
    	return "list_bill";
    }
    
    @GetMapping("/bookstore/bill/{BillID}")
	public String getProduct(Model model, @PathVariable("BillID") String billID) throws Exception {
		model.addAttribute("BillID", billID);
		Bill bill = billDAO.selectBill(Integer.parseInt(billID));
		ArrayList<DetailedBill> detailedBills = bill.getDetailedBills();
		float subTotal = (float) detailedBills.stream().mapToDouble(DetailedBill::getAmount).sum();
		double totalDouble = GlobalData.cart.stream().mapToDouble(DetailedBill::getAmount).sum();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", (float)totalDouble);
        model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("bill", bill);
		model.addAttribute("detailedBills",detailedBills);
		model.addAttribute("subTotal", subTotal);
		return "order_bill";
	}
    @DeleteMapping("/bookstore/delete/{BillID}")
	public String deleteProduct(@PathVariable("BillID") String billID, RedirectAttributes redirectAttributes) {
		if(billDAO.deleteBill(billID)) {
			redirectAttributes.addFlashAttribute("success","Xóa hóa đơn thành công!");
		};
		return "redirect:/bookstore/list-bill";
	}
}
