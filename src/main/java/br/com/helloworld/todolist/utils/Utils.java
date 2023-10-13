package br.com.helloworld.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  public static void mergeObjects(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertyKeys(source));
  }

  private static String[] getNullPropertyKeys(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> nullishProperties = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      String propKey = pd.getName();
      Object propVal = src.getPropertyValue(propKey);
      if (propVal == null)
        nullishProperties.add(propKey);
    }
    String[] result = new String[nullishProperties.size()];
    return nullishProperties.toArray(result);
  }
}
