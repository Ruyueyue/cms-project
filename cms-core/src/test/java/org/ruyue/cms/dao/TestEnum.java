package org.ruyue.cms.dao;

import org.junit.Test;
import org.ruyue.basic.test.util.EntitiesHelper;
import org.ruyue.basic.util.EnumUtils;
import org.ruyue.cms.model.ChannelType;
import org.ruyue.cms.model.RoleType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 测试:将枚举类型转换为列表
 * @author: Ruyue Bian
 * @create: 2019-05-27 17:17
 */
public class TestEnum {
    @Test
    public void testEnumList(){
        RoleType[] rts=RoleType.values();
        for (RoleType rt:rts){
            System.out.println(rt.ordinal());
        }
    }

    @Test
    public void testEnumList2(){
        Class<? extends Enum> clz=RoleType.class;
        System.out.println(clz.isEnum());
        for (Enum en:clz.getEnumConstants()){
            System.out.println(en.name());
        }
    }

   /* @Test
    public void testEnumList3(){
        List<String> actuals= Arrays.asList("ROLE_ADMIN","ROLE_PUBLISH","ROLE_AUDIT");
        List<String> expectes= EnumUtils.enum2Name(RoleType.class);
        EntitiesHelper.assertObjects(expectes,actuals);
    }*/

    @Test
    public void testEnumProp(){
        try {
            String prop="getName";
            Method m=ChannelType.class.getMethod(prop,null);
            System.out.println(m.invoke(ChannelType.NAV_CHANNEL,null));
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEnumPropUtil(){
        //System.out.println(EnumUtils.enumProp2List(ChannelType.class,"name"));
        //System.out.println(EnumUtils.enumPropByOrdinalMap(ChannelType.class,"name"));
        //System.out.println(EnumUtils.enumProp2NameMap(ChannelType.class,"name"));
    }
}
