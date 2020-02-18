package com.epam.bankproject.bankproject.contoller.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ModelAndViewBuilder {
    private static final Map<String, Object> namesToObjects = new HashMap();

    public ModelAndViewBuilder withObjectBySupplier(String name, Supplier<?> supplier) {
        namesToObjects.put(name, supplier.get());
        return this;
    }

    public ModelAndViewBuilder withPageableByFunction(String name,
                                                      BiFunction<Integer, PageRequest, Page<?>> function,
                                                      Integer id,
                                                      PageRequest pageRequest) {
        Page<?> pageable = function.apply(id, pageRequest);
        namesToObjects.put(name, pageable);

        if (pageable.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pageable.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            namesToObjects.put("pageNumbers", pageNumbers);
        }
        return this;
    }

    public ModelAndViewBuilder withObjectByFunction(String name, Function<Integer, ?> function, Integer id) {
        namesToObjects.put(name, function.apply(id));
        return this;
    }

    public ModelAndView buildWithName(String name) {
        ModelAndView modelAndView = new ModelAndView();
        namesToObjects.forEach(modelAndView::addObject);
        modelAndView.setViewName(name);
        return modelAndView;
    }


}
