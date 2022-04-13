package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲座类型表
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_type")
public class LectureType implements Serializable {

    private static final long serialVersionUID = 423535467456341L;

    /**
     * 讲座类型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 讲座类型名称
     */
    private String name;

    /**
     * 逻辑删除 1（true）已删除  0（false）未删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
