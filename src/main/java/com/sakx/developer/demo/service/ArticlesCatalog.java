package com.sakx.developer.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakx.developer.demo.model.Article;
import com.sakx.developer.demo.repository.ArticleRepository;


/**
 * This classes contains the service catalog for articles
 */

@Service
public class ArticlesCatalog {

	@Autowired
	private ArticleRepository articleRepository;

	public String getInfo() {
		return "Article service";
	}
	
	public Article getArticleById(int articleId) {
		Article obj = articleRepository.getArticleById(articleId);
		return obj;
	}

	public List<Article> getAllArticles() {
		return articleRepository.getAllArticles();
	}

	// public synchronized boolean addArticle(Article article) {
	public synchronized boolean addArticle(Article article) {
	    boolean isAdded = false;
		if (! articleRepository.isExists(article.getTitle(), article.getCategory()) ) {
			if (articleRepository.addArticle(article) > 0 ) isAdded = true;
		}
		return isAdded;
	}

	public int updateArticle(Article article) {
		return articleRepository.updateArticle(article);
	}

	public int deleteArticle(int articleId) {
		return articleRepository.deleteArticle(articleId);
	}
}
