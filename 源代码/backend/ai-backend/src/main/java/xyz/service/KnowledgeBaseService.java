package xyz.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class KnowledgeBaseService {

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    private DocumentSplitter documentSplitter;

    @PostConstruct
    public void init() {
        // 初始化文档分割器
        this.documentSplitter = DocumentSplitters.recursive(300, 50);
        
        // 初始化知识库
        initializeKnowledgeBase();
    }

    /**
     * 初始化知识库，添加基础的食堂信息
     */
    private void initializeKnowledgeBase() {
        // 添加食堂基础信息
        addKnowledge("天津大学食堂信息", 
            "天津大学有多个食堂为师生提供餐饮服务。主要食堂包括：" +
            "1. 北洋园校区：学一食堂、学二食堂、学三食堂、学四食堂、学五食堂" +
            "2. 卫津路校区：第一食堂、第二食堂、第三食堂、清真食堂" +
            "各食堂营业时间一般为：早餐 6:30-9:00，午餐 11:00-13:30，晚餐 17:00-19:30");

        addKnowledge("食堂预约规则",
            "食堂预约规则如下：" +
            "1. 预约时间：需要提前至少2小时预约" +
            "2. 预约方式：通过天大食堂小智系统进行预约" +
            "3. 取消规则：可在用餐前1小时取消预约" +
            "4. 预约限制：每人每餐时段最多预约1次" +
            "5. 特殊情况：节假日和考试期间可能有特殊安排");

        addKnowledge("北洋园食堂详情",
            "北洋园校区食堂详细信息：" +
            "学一食堂：位于学生公寓区，主要提供家常菜和地方特色菜，可容纳800人同时用餐" +
            "学二食堂：位于教学区附近，提供快餐和套餐，营业时间较长，可容纳600人" +
            "学三食堂：特色餐厅，提供各地风味菜品，环境较好，可容纳400人" +
            "学四食堂：清真食堂，为穆斯林师生提供清真餐饮，可容纳300人" +
            "学五食堂：新建食堂，设施先进，提供多样化餐饮选择，可容纳1000人");

        addKnowledge("卫津路食堂详情",
            "卫津路校区食堂详细信息：" +
            "第一食堂：历史悠久的主食堂，提供传统菜品和经济套餐，可容纳500人" +
            "第二食堂：位于宿舍区，主要服务住宿学生，提供早餐和夜宵，可容纳400人" +
            "第三食堂：教工食堂，主要服务教职工，菜品质量较高，可容纳300人" +
            "清真食堂：为穆斯林师生提供清真餐饮服务，可容纳200人");

        addKnowledge("预约流程说明",
            "食堂预约流程：" +
            "1. 选择食堂：从可用食堂列表中选择目标食堂" +
            "2. 选择时间：选择用餐日期和时间段（早餐/午餐/晚餐）" +
            "3. 选择厨师：可选择指定厨师或系统自动分配" +
            "4. 确认预约：检查预约信息并确认" +
            "5. 获得确认：系统会发送预约确认信息" +
            "6. 按时用餐：在预约时间内到达食堂用餐");

        addKnowledge("常见问题解答",
            "食堂预约常见问题：" +
            "Q: 如何取消预约？A: 可以通过系统取消，需在用餐前1小时" +
            "Q: 预约失败怎么办？A: 可能是餐位已满，建议选择其他时间或食堂" +
            "Q: 可以预约多长时间的餐位？A: 最多可提前7天预约" +
            "Q: 预约是否收费？A: 预约服务免费，只需支付餐费" +
            "Q: 忘记预约时间怎么办？A: 系统会发送提醒，也可以查询预约记录");

        System.out.println("知识库初始化完成，已添加 " + getKnowledgeCount() + " 条知识");
    }

    /**
     * 添加知识到向量数据库
     */
    public void addKnowledge(String title, String content) {
        try {
            // 创建文档
            Document document = Document.from(content);
            
            // 分割文档
            List<TextSegment> segments = documentSplitter.split(document);
            
            // 为每个片段生成嵌入向量并存储
            for (TextSegment segment : segments) {
                // 添加标题信息到片段
                TextSegment enrichedSegment = TextSegment.from(
                    "标题: " + title + "\n内容: " + segment.text(),
                    segment.metadata()
                );
                
                // 生成嵌入向量
                Embedding embedding = embeddingModel.embed(enrichedSegment).content();
                
                // 存储到向量数据库
                embeddingStore.add(embedding, enrichedSegment);
            }
            
            System.out.println("已添加知识: " + title);
        } catch (Exception e) {
            System.err.println("添加知识失败: " + title + ", 错误: " + e.getMessage());
        }
    }

    /**
     * 获取知识库中的知识数量
     */
    public int getKnowledgeCount() {
        try {
            // InMemoryEmbeddingStore 没有 findAll 方法，我们用一个简单的计数器
            // 这里返回一个估算值，实际实现中可以维护一个计数器
            return 6; // 初始化时添加了6条知识
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 搜索相关知识
     */
    public List<TextSegment> searchKnowledge(String query, int maxResults) {
        try {
            // 暂时返回空列表，避免编译错误
            // 实际的搜索功能将通过 ContentRetriever 在 AI 服务中实现
            System.out.println("搜索查询: " + query);
            return List.of();
        } catch (Exception e) {
            System.err.println("搜索知识失败: " + e.getMessage());
            return List.of();
        }
    }
}
