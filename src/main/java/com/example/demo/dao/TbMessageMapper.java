package com.example.demo.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.TbMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-02-26 14:48:04
 */
@Mapper
@Repository
public interface TbMessageMapper extends BaseMapper<TbMessage> {

    /**
     * 新增
     * @author 吴啸
     * @date 2020/02/26
     **/
    int insert(TbMessage tbMessage);

    /**
     * 刪除
     * @author 吴啸
     * @date 2020/02/26
     **/
    int delete(int id);

    /**
     * 更新
     * @author 吴啸
     * @date 2020/02/26
     **/
    int update(TbMessage tbMessage);

    /**
     * 查询 根据主键 id 查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    TbMessage load(int id);

    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    List<TbMessage> pageList(int start,int pagesize,String username);

    /**
     * 查询 分页查询 count
     * @author 吴啸
     * @date 2020/02/26
     **/
    int pageListCount(int start,int pagesize,String username);

}
