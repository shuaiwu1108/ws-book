package com.shuaiwu.wsbook.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.shuaiwu.wsbook.dto.BookDTO;
import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.mapper.BookMapper;
import com.shuaiwu.wsbook.service.IAuthorService;
import com.shuaiwu.wsbook.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaiwu.wsbook.utils.*;
import io.netty.util.internal.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private IAuthorService iAuthorService;

    @Override
    public void saveBook() {
        // 开始拉取并解析存储小说数据
        String res = HttpUtil.get("https://www.xbiquge.bz", "/", null, "GBK");
        List<String> types = JsoupUtil.bqg_index(res);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("", e);
        }

        for (String type : types) {
            log.info("type===>{}", type);
            String typeRes = HttpUtil.get("https://www.xbiquge.bz", type, null, "GBK");
            if (StringUtil.isNullOrEmpty(typeRes)) {
                log.error("书籍类型请求失败，type:{}", type);
                continue;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("", e);
            }

            List<String> books = JsoupUtil.bqg_type(typeRes);

            List<Book> bookList = new ArrayList<>();
            for (String book : books) {
                Book b;
                String bookId = book.split("/")[4];
                //redis检查书籍Id是否重复
                if (RedisUtil.sHasKey(RedisKeys.BQG_BOOK.name(), bookId)) {
                    log.info("书籍{}已存在", bookId);
                    b = this.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookSource, "bqg").eq(Book::getBookSourceId, bookId));
                    continue;
                }else{
                    b = new Book();
                }

                String catalogue = HttpUtil.get(book, "", null, "GBK");
                if (StringUtil.isNullOrEmpty(catalogue)) {
                    log.error("书籍数据请求失败，book:{}", book);
                    RedisUtil.sSet(RedisKeys.BQG_BOOK_ERR.name(), bookId);
                    continue;
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    log.error("", e);
                }

                Map<String, String> stringMap = JsoupUtil.bqg_catalogue(catalogue);
                String bookNameTmp = stringMap.get("name");
                b.setName(bookNameTmp);
                b.setUrl(book);

                String authorNameTmp = stringMap.get("author");
                Author a;
                a = iAuthorService.getOne(new LambdaQueryWrapper<Author>().eq(Author::getAuthorName, authorNameTmp));
                if (a == null){
                    a = new Author();
                    a.setAuthorName(authorNameTmp);
                    a.setCreateTime(new Date());
                    a.setUpdateTime(new Date());
                    iAuthorService.save(a);
                }
                b.setAuthorId(a.getId());
                b.setDescription(stringMap.get("description"));
                b.setCreateTime(new Date());
                b.setUpdateTime(new Date());
                b.setBookType(CommonUtil.getCodeByName(type));

                if(b.getBookType().equals("100007")){
                    b.setWorkStatus("300002");
                }else{
                    b.setWorkStatus("300001");
                }

                String icon = stringMap.get("icon");
                b.setIcon(icon);
                try (InputStream inputStream = HttpUtil.getInputStream(icon)){
                    String objName = "book/".concat(b.getId()+"").concat(".jpg");
                    ByteArrayOutputStream data = new ByteArrayOutputStream();
                    byte[] fileBytes = new byte[1024 * 1024];
                    int len = 0;
                    while ((len = inputStream.read(fileBytes)) != -1){
                        data.write(fileBytes, 0, len);
                    }
                    try (ByteArrayInputStream inputStream1 = new ByteArrayInputStream(data.toByteArray())){
                        boolean flag = MinioUtil.putObject("image", objName, inputStream1);
                        if (flag){
                            b.setIconFileUrl("/image/".concat(objName));
                        }
                    }
                }catch (Exception e){
                    log.error("", e);
                    RedisUtil.sSet(RedisKeys.BQG_BOOK_ERR.name(), bookId);
                    continue;
                }
                b.setBookSource("bqg");
                b.setBookSourceId(bookId);

                //redis保存书籍id
                RedisUtil.sSet(RedisKeys.BQG_BOOK.name(), bookId);
                bookList.add(b);
                log.info("type===>{} save bookName===>{}, bookId==>{}", type, b.getName(), bookId);
            }

            this.saveOrUpdateBatch(bookList);
        }
    }

    @Override
    public IPage<BookDTO> getBookListAndAuthorName(JSONObject jsonObject) {
        String source = jsonObject.getStr("source");
        String bookType = jsonObject.getStr("bookType");
        int pageIndex = jsonObject.getInt("pageIndex");
        int pageSize = jsonObject.getInt("pageSize");

        MPJLambdaWrapper<Book> leftJoin = new MPJLambdaWrapper<Book>()
                .selectAll(Book.class)
                .select(Author::getAuthorName)
                .leftJoin(Author.class, Author::getId, Book::getAuthorId)
                .eq(Book::getBookSource, source)
                .eq(Book::getBookType, CommonUtil.getCodeByName(bookType));

        IPage<BookDTO> pageDto = this.baseMapper.selectJoinPage(new Page<>(pageIndex, pageSize), BookDTO.class, leftJoin);

        return pageDto;
    }
}
