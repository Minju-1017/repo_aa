package com.aa.module.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	ProductDao productDao;
	
		public int selectCount(ProductVo vo) {
			return productDao.selectCount(vo);
		}
		public List<ProductDto> productList(ProductVo vo){
			return productDao.productList(vo);
		}
		public ProductDto productOne(ProductDto productDto) {
			return productDao.productOne(productDto);
		}
		public List<ProductDto> venderList(ProductDto productDto){
			return productDao.venderList(productDto);
		}
		public int productUpdate(ProductDto productDto) {
			return productDao.productUpdate(productDto);
		}
		public int productInsert(ProductDto productDto) {
			return productDao.productInsert(productDto);
		}
		public int productUelete(ProductDto productDto) {
			return productDao.productUelete(productDto);
		}
		public List<ProductDto> codeList(ProductDto productDto){
			return productDao.codeList(productDto);
		}
		public int totalPrice() {
			return productDao.totalPrice();
		}
		public int totalOrder() {
			return productDao.totalOrder();
		}
}
