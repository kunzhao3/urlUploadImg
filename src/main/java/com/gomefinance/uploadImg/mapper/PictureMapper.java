package com.gomefinance.uploadImg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.gomefinance.uploadImg.domain.Picture;

@Repository
public interface PictureMapper {

	@Select("SELECT MAX(t.UPTIME) upTime,t.IMGNAME name,t.ID_NO idNo,t.APP_NO appNo,SYS_NAME sysName,K_ID kId,t.SUBCLASS_SORT subClassSort" +
    		" FROM PICTURE t WHERE t.APP_NO = #{appNo} AND SUBCLASS_SORT IN ('B1','B2','FACE1','FACE2','FACE3','FACE4') GROUP BY t.SUBCLASS_SORT")
	@Results(value = {  @Result(column = "app_no", property = "appNo") })
	List<Picture> getPicture(@Param("appNo")String appNo);
	
	@Select("SELECT t.IMGNAME name,t.ID_NO idNo,t.APP_NO appNo,SYS_NAME sysName,K_ID kId,t.SUBCLASS_SORT subClassSort" +
    		" FROM PICTURE t WHERE t.APP_NO = #{appNo} AND SUBCLASS_SORT =#{subClassSort} GROUP BY t.SUBCLASS_SORT")
	@Results(value = {  @Result(column = "app_no", property = "appNo") })
	Picture getPictureByAppNoAndSubClassSort(@Param("appNo")String appNo,@Param("subClassSort")String subClassSort);
}
