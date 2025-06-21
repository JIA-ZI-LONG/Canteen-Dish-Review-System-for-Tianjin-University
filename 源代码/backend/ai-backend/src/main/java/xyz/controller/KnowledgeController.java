package xyz.controller;

import dev.langchain4j.data.segment.TextSegment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.service.KnowledgeBaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
@Tag(name = "知识库管理", description = "管理 RAG 知识库的接口")
public class KnowledgeController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @PostMapping("/add")
    @Operation(summary = "添加知识", description = "向知识库添加新的知识内容")
    public Map<String, Object> addKnowledge(
            @RequestParam String title,
            @RequestParam String content) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            knowledgeBaseService.addKnowledge(title, content);
            result.put("success", true);
            result.put("message", "知识添加成功");
            result.put("title", title);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "知识添加失败: " + e.getMessage());
        }
        
        return result;
    }

    @GetMapping("/search")
    @Operation(summary = "搜索知识", description = "在知识库中搜索相关内容")
    public Map<String, Object> searchKnowledge(
            @RequestParam String query,
            @RequestParam(defaultValue = "3") int maxResults) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<TextSegment> segments = knowledgeBaseService.searchKnowledge(query, maxResults);
            result.put("success", true);
            result.put("query", query);
            result.put("results", segments.stream().map(TextSegment::text).toList());
            result.put("count", segments.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "搜索失败: " + e.getMessage());
        }
        
        return result;
    }

    @GetMapping("/status")
    @Operation(summary = "知识库状态", description = "获取知识库的状态信息")
    public Map<String, Object> getStatus() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int count = knowledgeBaseService.getKnowledgeCount();
            result.put("success", true);
            result.put("knowledgeCount", count);
            result.put("status", count > 0 ? "已初始化" : "未初始化");
            result.put("message", "知识库包含 " + count + " 条知识");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取状态失败: " + e.getMessage());
        }
        
        return result;
    }
}
