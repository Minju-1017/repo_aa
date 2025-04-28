package com.aa.module.product;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {
	public int selectCount(ProductVo vo);
	public List<ProductDto> productList(ProductVo vo);
	public ProductDto productOne(ProductDto productDto);
	public List<ProductDto> venderList(ProductDto productDto);
	public int productUpdate(ProductDto productDto);
	public int productInsert(ProductDto productDto);
	public int productUelete(ProductDto productDto);
	public List<ProductDto> codeList(ProductDto productDto);
	public int totalPrice();
	public int totalOrder();
}
