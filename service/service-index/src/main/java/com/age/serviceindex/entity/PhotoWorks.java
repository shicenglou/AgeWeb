package com.age.serviceindex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dudubired
 * @since 2022-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PhotoWorks对象", description="")
public class PhotoWorks implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者名称")
    private String userName;

    @ApiModelProperty(value = "图片地址")
    private String photoUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "点赞数")
    private Double likely;

    @ApiModelProperty(value = "收藏数")
    private Double collection;

    @ApiModelProperty(value = "权重")
    private Double weight;

    @ApiModelProperty(value = "标签id")
    private String labelId;


}
