package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 查询所有数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
       return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 根据 id 查询数据
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    /**
     * 保存数据
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    /**
     * 修改数据
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 根据id 删除数据
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 分页查询
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){

        Page<Spit> pageDate = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageDate.getTotalElements(),pageDate.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //判断当前用户是否已经点赞， 但是现在我们没有做认证，暂时先把userid 写死
        String userid = "111";
        //判断当前用户是否已经点赞
        if(redisTemplate.opsForValue().get("thumbup_"+userid) !=null){
            return new Result(false,StatusCode.REMOTEERROR,"不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }


}
