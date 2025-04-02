package org.example.blue_springboot.controller;

import org.example.blue_springboot.model.Article;
import org.example.blue_springboot.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@CrossOrigin(origins = "http://localhost:5173") // 跨域

@RestController
@RequestMapping("/api/articles")
public class ArticleController implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleRepository articleRepository;

   /* @Bean
    public void initUploadDir() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (Exception e) {
            logger.error("无法创建上传目录: {}", e.getMessage());
        }
    }*/

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestBody Article article) {
        Map<String, Object> response = new HashMap<>();
        try {
            articleRepository.save(article);
            logger.info("文章已保存，ID: " + article.getId());
            response.put("message", "文章上传成功！");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("上传失败: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "上传失败: " + e.getMessage()));
        }
    }


    private String saveFile(MultipartFile file) throws Exception {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, file.getBytes());
        return "/uploads/" + fileName;
    }

    @GetMapping("/list")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticle(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        return articleRepository.findById(id).map(article -> {
            try {
                article.setTitle(title);
                article.setContent(content);
                if (image != null && !image.isEmpty()) {
                    article.setImageUrl(saveFile(image));
                }
                articleRepository.save(article);
                return ResponseEntity.ok("文章更新成功！");
            } catch (Exception e) {
                logger.error("更新失败: {}", e.getMessage());
                return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
            }
        }).orElse(ResponseEntity.status(404).body("文章未找到"));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return ResponseEntity.ok("文章删除成功！");
        } else {
            return ResponseEntity.status(404).body("文章未找到");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + UPLOAD_DIR);
    }
}
