package com.example.BookStore.global;

import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.DetailedBill;
import com.example.BookStore.model.Product;

public class GlobalData {
	//tao bien toan cuc
    public static ArrayList<DetailedBill> cart;

    static {
        cart = new ArrayList<>();
    }
}
