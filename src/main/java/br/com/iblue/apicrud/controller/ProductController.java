package br.com.iblue.apicrud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.iblue.apicrud.entity.Product;
import br.com.iblue.apicrud.repository.IProductRepository;

@ResponseBody
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private IProductRepository dao;

	@PostMapping("/gravar")
	public ResponseEntity<?> create(@RequestBody Product product) {
		try {
			Product p = dao.save(product);

			Map<String, Object> map = new HashMap<String, Object>() {
				{
					put("product-saved", p);
					put("status", "saved successfully");
				}
			};
			return ResponseEntity.status(200).body(map);
		} catch (Exception ex) {
			Map<String, Object> error = new HashMap<String, Object>() {
				{
					put("saving-error", ex.getMessage());
				}
			};
			return ResponseEntity.status(500).body(error);
		}
	}
}