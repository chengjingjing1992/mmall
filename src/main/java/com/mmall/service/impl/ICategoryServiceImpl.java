package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired//通过这个注解把mapper 注解进来
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
//        if(categoryName!=null&&parentId!=null){
//
//        }
        if(StringUtils.isBlank(categoryName)||parentId==null){
            return ServerResponse.createByErrorMeg("类型参数错误");
        }

        Category category=new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);//这个分类是可用的

        int rowCount=categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMeg("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId,String  categoryName ) {
        if(StringUtils.isBlank(categoryName)||categoryId==null){
            return ServerResponse.createByErrorMeg("类型参数错误");
        }
        int result=categoryMapper.updateCategoryName(categoryId,categoryName);
        if(result==0){
            return ServerResponse.createByErrorMeg("更改失败");
        }

        return ServerResponse.createBySuccess();
    }
    @Override
    public ServerResponse getChildrenCategoryParallel(Integer categoryId){
        if(categoryId==null){
            return ServerResponse.createByErrorMeg("类型参数错误");
        }
        List<Category> categories= categoryMapper.getChildrenCategoryParallel(categoryId);
        if(CollectionUtils.isEmpty(categories)){
            return ServerResponse.createByErrorMeg("该类型的子类为空");
        }



        for(int i=0;i<categories.size();i++ ){
            System.out.println(categories.get(i).toString());
        }
        return ServerResponse.createBySuccess(categories);
    }

    public ServerResponse getCategoryAndDeepChildrenCategory(Integer categoryId){
        if(categoryId==null){
            return ServerResponse.createByErrorMeg("类型参数错误");
        }
        Set<Category> set=new HashSet<Category>();
        Set<Category> categories=getCategoryAndDeepChildrenCategory(set,categoryId);
        return ServerResponse.createBySuccess(categories);
    }

    public  Set<Category> getCategoryAndDeepChildrenCategory(Set<Category> categories,Integer categoryId){
        categories.add(categoryMapper.selectByPrimaryKey(categoryId));
        List<Category> categoryList=categoryMapper.getChildrenCategoryParallel(categoryId);
        if(categoryList!=null){
            for (Category category:
                    categoryList) {
                categories.add(category);
                getCategoryAndDeepChildrenCategory(categories,category.getId());
            }
        }
        return categories;

    }

    //    List list=new ArrayList();
    int index=0;
    @Transactional
    public  List sort(List list,Integer id ){
        Category category= categoryMapper.selectByPrimaryKey(id);
        System.out.println("category.toString()="+category.toString());
        Integer level=category.getLevel();
        if(id==0){
            list.add(id);

        }
        index=list.indexOf(id);
        list=list.subList(0,index+1);
        List<Category> idList=categoryMapper.getChildrenCategoryParallel(id);
        if(idList!=null){
            level=level+1;
            for (Category c:idList
                 ) {
                c.setLevel(level);
            }

            for (Category c:idList
            ) {
                list.add(c.getId());
                c.setLevel(level);
                categoryMapper.updateByPrimaryKeySelective(c);
                System.out.println("c.getLevel()=="+c.getLevel());
                sort(list,c.getId());
            }
        }
        return list;
    }

}
