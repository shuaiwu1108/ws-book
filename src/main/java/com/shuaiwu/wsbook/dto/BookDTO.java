package com.shuaiwu.wsbook.dto;

import lombok.Data;

/**
 * <p>
 * 书籍
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Data
public class BookDTO {

    private Long id;

    private String name;

    private String url;

    private String icon;

    private String iconFileUrl;

    private String authorId;

    private String authorName;

    private String description;

    private String bookType;

    private String workStatus;

    private String bookSource;

    private String bookSourceId;
}
