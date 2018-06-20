package com.gomefinance.uploadImg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.gomefinance.uploadImg.domain.Picupload;
import com.gomefinance.uploadImg.domain.TmAppMain;

@Repository
public interface TmAppMainMapper {
    @Select("SELECT APP_NO as appNo, CUSTOMER_SOURCE as customerSource, UNIQUE_ID as uniqueId, PRODUCT_CD as productCd FROM tm_app_main  where APP_NO= #{appNo}")
    @Results(value = {  @Result(column = "app_no", property = "appNo") })
    TmAppMain getAppMainByAppNo(@Param("appNo") String appNo);
    /**
     * 按时间查询总条数
     * @param createTime  时间
     * @return
     */
    @Select("SELECT COUNT(APP_NO) FROM tm_app_main WHERE PRODUCT_CD='5000' AND APP_NO NOT IN(SELECT APP_NO FROM tm_pic_upload)  AND CREATE_TIME<=#{createTime} ")
    @Results()
    int getAppMainCount(@Param("createTime") String createTime);
    /**
     * 分页查询
     * @param createTime 时间
     * @param begin 开始页数
     * @param end 结束页数
     * @return
     */
    @Select("SELECT APP_NO as appNo,UNIQUE_ID as uniqueId,CREATE_TIME as createTime,PRODUCT_CD  as productCd FROM tm_app_main  WHERE PRODUCT_CD='5000' AND APP_NO NOT IN(SELECT APP_NO FROM tm_pic_upload) AND  CREATE_TIME<=#{createTime} LIMIT #{begin},#{end}")
    @Results(value = {  @Result(column = "create_time", property = "createTime") })
    List<TmAppMain> getTmAppMain(@Param("createTime") String createTime,@Param("begin") int begin,@Param("end") int end );
    
    @Insert("INSERT INTO tm_pic_upload (`app_no`,`create_time`,`update_time`,`status`,`imginfo`) " +
    		"VALUES(#{appNo},#{createTime},#{updateTime},#{status},#{imginfo})")
    int saveTmPicUpload(Picupload picupload);
    
    @Select("select * from tm_pic_upload where app_no=#{appNo}")
    @Results(value = {  @Result(column = "app_no", property = "appNo") })
    Picupload getPicuploadByAppNo(@Param("appNo") String appNo);
    
    @Update("UPDATE  tm_pic_upload SET imginfo=#{imginfo},update_time=#{updateTime} where app_no=#{appNo}")
    int upTmPicUpload(Picupload picupload);
    
}
