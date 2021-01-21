package br.com.iblue.apicrud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/post")
	public ResponseEntity<?> insert(@RequestBody Product product) {
		try {
			Product p = dao.save(product);

			Map<String, Object> map = new HashMap<String, Object>() {
				{
					if (p == null) {
						throw new Exception("Falha ao Inserir os Dadoas");
					}else {
						put("product-saved", p);
						put("status", "saved successfully");
					}
				}
			};
			return ResponseEntity.status(200).body(p);
		} catch (Exception ex) {
			Map<String, Object> error = new HashMap<String, Object>() {
				{
					put("saving-error", ex.getMessage());
				}
			};
			return ResponseEntity.status(500).body(error);
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.status(200).body(dao.findAll());
	}
	
	@GetMapping("/products/{idProduct}")
	public ResponseEntity<?> findById(@PathVariable("idProduct") Long idProduct){
		try{ 
		Product product = dao.findById(idProduct).get();
		 if (product == null) {
			  throw new IllegalAccessException("Produto não Encontrado");
		 }
		 return ResponseEntity.status(200).body(product);
		}catch(Exception ex) {
			 Map<String,Object> mapa = new HashMap<String,Object>();
			 mapa.put("Produto não Encontrado", ex.getMessage());
			 return ResponseEntity.status(404).body(mapa);
			}
	}
	
	@DeleteMapping("/delete/{idProduct}")
	public ResponseEntity<?> delete(@PathVariable("idProduct") Long idProduct) {
		try {
			dao.deleteById(idProduct);
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("Excluido", "Excluido com Sucesso");
			return ResponseEntity.status(200).body(mapa);
		} catch (Exception ex) {
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("Error", ex.getMessage());
			return ResponseEntity.status(400).body(mapa);
		}
	}
	
	@PutMapping("/update/{idProduct}")
	public ResponseEntity<?> update(@PathVariable("idProduct") Long idProduct, @RequestBody Product product) {
		try {
			Product p = dao.findById(idProduct).get();
			if (p == null) {
				throw new IllegalAccessException("Produto Não Encontrado");
			}
			p.setNameProduct(product.getNameProduct());
			p.setPrice(product.getPrice());
			p.setQuantity(product.getQuantity());
			Product update = dao.save(p);
			return ResponseEntity.status(200).body(update);
		} catch (Exception ex) {
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("Erro de Gravação", ex.getMessage());
			return ResponseEntity.status(500).body(mapa);
		}
	}
}