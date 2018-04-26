package com.sakx.developer.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.sakx.developer.demo.model.Article;
import com.sakx.developer.demo.service.ArticleService;

@Controller
@RequestMapping("/")
public class ArticleRestController {

	public static final Logger logger = LoggerFactory.getLogger(ArticleRestController.class);
	
	@Autowired
	private ArticleService articleService;

    @RequestMapping("/")
    public ResponseEntity<String> greeting() {
		return new ResponseEntity<String>(articleService.getInfo(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/articles/list", method= RequestMethod.GET)
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> list = articleService.getAllArticles();
		logger.debug(" getting list of all articles - {} ", (list != null ? list.size() : null));
		return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/articles/show/{id}", method= RequestMethod.GET)
	public ResponseEntity<Article> getArticleById(@PathVariable("id") Integer id) {
		HttpStatus status = HttpStatus.OK;

		logger.debug(" searching for article - {} ", id);
		Article article = articleService.getArticleById(id);
		if (article == null) {
			status = HttpStatus.NOT_FOUND;
		}
		logger.debug(" article is {} ", article);
		return new ResponseEntity<Article>(article, status);
	}

	@RequestMapping(value = "/articles/add", method = RequestMethod.POST)
	public ResponseEntity<String> addArticle(@RequestBody Article article, UriComponentsBuilder builder) {

		ResponseEntity<String> response = null;

		boolean isAdded = articleService.addArticle(article);
		if (isAdded == false) {
			response = new ResponseEntity<String>("Article already existed!", HttpStatus.CONFLICT);
		} else {
			response = new ResponseEntity<String>("Article added successfully", HttpStatus.OK);
//			if (forward) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.setLocation(builder.path("/show/{id}").buildAndExpand(article.getArticleId()).toUri());
//				response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//			}
		}
		return response;
	}

	@RequestMapping(value = "/articles/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateArticle(@PathVariable Integer id, @RequestBody Article article) {
		article.setArticleId(id);
		articleService.updateArticle(article);
		return new ResponseEntity<String>("Article updated successfully", HttpStatus.OK);
	}


	@RequestMapping(value="/articles/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteArticle(@PathVariable("id") Integer id) {
		articleService.deleteArticle(id);
		return new ResponseEntity<String>("Article deleted successfully", HttpStatus.OK);
	}

} 